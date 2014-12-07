package view.editor.wizards;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.multilanguage.LanguageException;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.gui.GUIController;


/**
 * This class represents the core functionality of any wizard within the Editor.
 * It requires a concrete implementation of a Controller class which extends it along
 * with an associated FXML file which includes specified fx:id's root, save, and errorMessage.
 * This class gives the class which launches the wizard the ability to modify the error message,
 * set a Consumer lambda function for when the save button is clicked, along with getting the data
 * submitted by the wizard, and the stage held by the wizard such that it can be closed.
 * 
 * @author Joshua, Nishad
 *
 */
public abstract class Wizard implements GUIController {

    private static final int ERROR_DISPLAY_DURATION = 3000;
    private final static String SAVE_KEY = "Save";
    private static final String ERROR_KEY = "SaveError";
    public static final String NUM_REGEX = "-?[0-9]+\\.?[0-9]*";

    @FXML
    protected SplitPane root;
    @FXML
    protected Button save;

    @FXML
    protected Text errorMessage;

    /**
     * Default error message
     */

    private Consumer<WizardData> mySaveConsumer;
    private WizardData myUserInput;
    private Stage myStage;

    /**
     * Returns the root of the wizard's scene
     * 
     * @return Node root - the root of the scene
     */
    @Override
    public Node getRoot () {
        return root;
    }

    /**
     * The default initialization of a wizard which creates a wizard data
     * and puts a null consumer into the save button action.
     */
    @Override
    public void initialize () {
        attachTextProperties();
        myUserInput = new WizardData();
        mySaveConsumer = (myUserInput) -> {
        };
        save.setOnAction(e -> save());
    }

    /**
     * This is the default workflow for when the user hits the save button
     */
    private void save () {
        if (checkCanSave()) {
            updateData();
            mySaveConsumer.accept(myUserInput);
        }
        else {
            displayWarning();
        }
    }

    /**
     * 
     * @param c The Consumer of a WizardData to execute when save is hit if the wizard allows it to
     *        be saved
     */
    public void setSubmit (Consumer<WizardData> c) {
        mySaveConsumer = c;
    }

    /**
     * Enables implementations of this abstract class to modify the WizardData with
     * data inputted by the user, ideally this should only be called within updateData()
     * 
     * @param key the type of data being saved
     * @param value the value of the data being saved, as a string
     */
    protected void addToData (WizardDataType key, String value) {
        myUserInput.addDataPair(key, value);
    }

    /**
     * Enables implementations of this abstract class to modify the WizardData with
     * nested wizards
     * 
     * @param wizData the wizard data contanied by a nested wizard
     */
    protected void addWizardData (WizardData wizData) {
        myUserInput.addWizardData(wizData);
    }

    /**
     * 
     * @param type defines what type of wizard this is for a factory within the model
     */
    protected void setWizardType (WizardType type) {
        myUserInput.setType(type);
    }

    /**
     * Note: this always happens before data is actually added to the WizardData
     * 
     * @return whether or not the data is acceptable to send to the model for the creation of
     *         whatever this wizard is intended to produce
     */
    public abstract boolean checkCanSave ();

    /**
     * This method is the essential method within the wizard where the concrete implementation
     * of a wizard, must convert the user inputted JavaFX data fields into data in a WizardData.
     * Please be sure to use the given methods addToData(String, String) and
     * addWizardData(WizardData).
     */
    public abstract void updateData ();

    public abstract void launchForEdit (WizardData oldValues);

    public abstract void loadGlobalValues (List<String> values);

    /**
     * used internally to display the default error message if none has been provided.
     */
    private void displayWarning () {
        try {
            displayErrorMessage(MultiLanguageUtility.getInstance().getStringProperty(ERROR_KEY)
                    .getValue());
        }
        catch (LanguagePropertyNotFoundException e) {
            DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * 
     * @return The WizardData object associated with this Wizard
     */
    public WizardData getWizardData () {
        return myUserInput;
    }

    /**
     * 
     * @param error The new message to display when called.
     */
    public void displayErrorMessage (String error) {
        errorMessage.setText(error);
        FadeTransition ft =
                new FadeTransition(Duration.millis(ERROR_DISPLAY_DURATION), errorMessage);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.play();
    }

    public void removeWizardData (WizardData data) {
        myUserInput.removeWizardData(data);
    }

    /**
     * This should only be done by the WizardUtility which actually spawns a new wizard.
     * 
     * @param s The stage which holds the Wizard
     */
    public void setStage (Stage s) {
        myStage = s;
    }

    /**
     * 
     * @return The stage which holds this Wizard.
     */
    public void closeStage () {
        myStage.close();
    }

    /**
     * Binds the multilanguage util to the text in the
     * wizard
     */
    protected void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            save.textProperty().bind(util.getStringProperty(SAVE_KEY));
        }
        catch (LanguageException e) {
            displayErrorMessage(e.getMessage());
        }
    }

}
