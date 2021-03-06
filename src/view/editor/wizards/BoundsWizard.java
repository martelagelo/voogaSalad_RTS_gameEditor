package view.editor.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.data.WizardData;
import model.data.WizardDataType;
import model.data.WizardType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;


/**
 * This represents the Wizard used to accept a numerical value with an attribute name. It checks
 * whether the inputted value is numeric, and then stores this value as a string, to conform with
 * the WizardData data structure.
 * 
 * @author Joshua, Nishad
 *
 */
public class BoundsWizard extends Wizard {

    private static final String ADD_POINT_KEY = "AddPoint";
    private static final String X_COR_KEY = "XCoordinate";
    private static final String Y_COR_KEY = "YCoordinate";

    @FXML
    private VBox allPoints;
    @FXML
    private Button addPoint;

    private List<TextField> xCoordinates;
    private List<TextField> yCoordinates;

    @Override
    public void initialize () {
        super.initialize();
        try {
            addPoint.textProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(ADD_POINT_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
        }

        addPoint.setOnAction(e -> addXYPair("", ""));
        xCoordinates = new ArrayList<>();
        yCoordinates = new ArrayList<>();
    }

    private void addXYPair (String x, String y) {
        HBox nextPoint = new HBox();
        TextField xField = new TextField();
        xField.setText(x);
        xCoordinates.add(xField);
        TextField yField = new TextField();

        try {
            xField.promptTextProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(X_COR_KEY));
            yField.promptTextProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(Y_COR_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
        }
        yField.setText(y);
        yCoordinates.add(yField);
        Button delete = new Button("X");
        nextPoint.getChildren().addAll(xField, yField, delete);
        allPoints.getChildren().add(nextPoint);
        delete.setOnAction(e -> {
            allPoints.getChildren().remove(nextPoint);
        });
    }

    @Override
    public boolean checkCanSave () {
        return checkCoordinatesValid(xCoordinates) && checkCoordinatesValid(yCoordinates) &&
               allPoints.getChildren().size() >= 4;
    }

    private boolean checkCoordinatesValid (List<TextField> coordinates) {
        for (TextField field : coordinates) {
            if (field.getText().isEmpty() || !isNumber(field.getText())) { return false; }
        }
        return true;
    }

    private String getCoordinates () {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < xCoordinates.size(); i++) {
            sb.append(xCoordinates.get(i).getText() + ",");
            sb.append(yCoordinates.get(i).getText() + ",");
        }
        return sb.toString();
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.BOUNDS);
        addToData(WizardDataType.BOUND_VALUES, getCoordinates());
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        String bounds = oldValues.getValueByKey(WizardDataType.BOUND_VALUES);
        String[] points = bounds.split(",");       
        for (int i = 0; i < points.length; i += 2) {
            addXYPair(points[i], points[i + 1]);
        }
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing
    }

}
