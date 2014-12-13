package view.editor.wizards;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import model.data.WizardData;
import model.data.WizardDataType;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.gui.GUIController;
import view.gui.GUIPanePath;


public class CRUDContainer implements GUIController {

    private static final String DELETE_TEXT = "X";
    private static final Dimension DEFAULT_WIZARD_SIZE = new Dimension(300, 300);

    @FXML
    private VBox root;
    @FXML
    private Button add;
    @FXML
    private VBox existing;

    List<WizardData> myWizardDatas;
    BooleanSupplier valid;

    private GUIPanePath myPath;
    private List<String> myGlobals;
    private BiConsumer<Button, WizardData> myTextConsumer;
    private Dimension myDim;

    @Override
    public Node getRoot () {
        return root;
    }

    @Override
    public void initialize () {
        myWizardDatas = new ArrayList<>();
        valid = () -> true;
        add.setOnAction(e -> {
            if (valid.getAsBoolean()) {
                launchNestedWizard();
            }
        });
    }

    public void setValidToLaunch (BooleanSupplier supplier) {
        valid = supplier;
    }

    public List<WizardData> getWizardDatas () {
        return Collections.unmodifiableList(myWizardDatas);
    }

    public void addAndRenderWizardDatas (Collection<WizardData> datas) {
        myWizardDatas.addAll(datas);
        myWizardDatas.forEach(data -> createVisualRepresentation(data));
    }

    public void init (GUIPanePath path, List<String> globalAttrs,
                      BiConsumer<Button, WizardData> setTextConsumer) {
        init(path, globalAttrs, setTextConsumer, DEFAULT_WIZARD_SIZE);
    }

    public void init (GUIPanePath path, List<String> globalAttrs,
                      BiConsumer<Button, WizardData> setTextConsumer,
                      Dimension dim) {
        myPath = path;
        myGlobals = globalAttrs;
        myTextConsumer = setTextConsumer;
        myDim = dim;
    }

    public void bindButtonText (ObjectProperty<String> property) {
        add.textProperty().bind(property);
    }

    private void launchNestedWizard () {
        Wizard wiz = WizardUtility.loadWizard(myPath, myDim);
        wiz.loadGlobalValues(myGlobals);
        Consumer<WizardData> bc = (data) -> {
            myWizardDatas.add(data);
            createVisualRepresentation(data);
            wiz.closeStage();
        };
        wiz.setSubmit(bc);
    }

    private void createVisualRepresentation (WizardData data) {
        HBox newElement = new HBox();
        Button edit = new Button();
        myTextConsumer.accept(edit, data);
        edit.setOnAction(e -> launchEditWizard(data, edit));
        edit.setMaxWidth(Double.MAX_VALUE);
        newElement.getChildren().add(edit);

        Button delete = new Button();
        delete.setText(DELETE_TEXT);
        delete.setOnAction(e -> {
            myWizardDatas.remove(data);
            existing.getChildren().remove(newElement);
        });
        newElement.getChildren().add(delete);
        existing.getChildren().add(newElement);
    }

    private void launchEditWizard (WizardData oldData, Button button) {
        Wizard wiz = WizardUtility.loadWizard(myPath, myDim);
        wiz.loadGlobalValues(myGlobals);
        wiz.launchForEdit(oldData);
        Consumer<WizardData> bc = (data) -> {
            myWizardDatas.remove(oldData);
            myWizardDatas.add(data);
            oldData.clear();
            for (WizardDataType type : data.getData().keySet()) {
                oldData.addDataPair(type, data.getValueByKey(type));
            }
            myTextConsumer.accept(button, data);
            wiz.closeStage();
        };
        wiz.setSubmit(bc);
    }

}
