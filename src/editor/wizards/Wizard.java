package editor.wizards;

import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.GUIController;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public abstract class Wizard implements GUIController {

    @FXML
    protected SplitPane root;
    @FXML
    protected Button save;
    
    @FXML
    protected Text errorMessage;
    
    private static final String ERROR = "CANNOT SAVE!"; 
    
    private Consumer<WizardData> mySaveConsumer;
    private WizardData userInput;
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

    @Override
    public void initialize () {        
        userInput = new WizardData();
        mySaveConsumer = (userInput) -> {}; 
        save.setOnAction(e -> save());
    }
    
    private void save() {       
        if (checkCanSave()) {
            updateData();
            
            mySaveConsumer.accept(userInput);
        }
        else {
            displayWarning();
        }
    }

    public void setSubmit (Consumer<WizardData> c) {
        mySaveConsumer = c;        
    }
    
    protected void addToData(String key, String value) {
        userInput.addDataPair(key, value);
    }
    
    protected void addWizardData(WizardData wizData) {
        userInput.addWizardData(wizData);
    }
    
    protected void setDataName(String name) {
        userInput.setName(name);
    }
    
    public abstract boolean checkCanSave();
    
    public abstract void updateData();
    
    private void displayWarning() {
        errorMessage.setText(ERROR);
    }
    
    public WizardData getWizardData() {
        return userInput;
    }
    
    public void setErrorMesssage(String error) {
        errorMessage.setText(error);
    }
    
    public void setStage(Stage s) {
        myStage = s;
    }
    
    public Stage getStage() {
        return myStage;
    }

}
