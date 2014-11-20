package editor.wizards;

import gamemodel.GameElementInfoBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.GUILoadStyleUtility;

/**
 * 
 * @author joshua
 *
 */
public class GameElementWizard  extends Wizard{
	
	@FXML private TextField name;
	@FXML private Button trigger;
	@FXML private Button stringAttribute;
	@FXML private Button numberAttribute;
	protected GameElementInfoBundle myGameElementInfoBundle;

	/**
	 * Sets the submit action for the game element wizard
	 * 
	 * @param Consumer c takes in a GameElementInfoBundle
	 * and uses it while protecting the internals of what
	 * is done with the GameElementInfoBundle
	 */
	public void setSubmit(Consumer<GameElementInfoBundle> c){
		save.setOnAction(e -> c.accept(myGameElementInfoBundle));
	}
	
	/**
	 * Launches a TriggerEditorWizard
	 * 
	 * TODO: Jonathan is cleaning up what needs to be
	 * done to launch a wizard. There is repeated
	 * code here that must be cleaned up
	 */
	private void launchTriggerEditor(){
    	GUILoadStyleUtility glsu = new GUILoadStyleUtility();
    	TriggerWizard TW = (TriggerWizard) glsu.generateGUIPane("/editor/wizards/guipanes/TriggerWizard.fxml");
        Scene myScene = new Scene((Parent) TW.getRoot(), 600, 300);  
        Stage s = new Stage();
        s.setScene(myScene);
        s.show();
		BiConsumer<String, String> bc = (condition, action) -> {
			myGameElementInfoBundle.addTrigger(condition, action);
			s.close();
		};
		TW.setSubmit(bc);
	}
	
	/**
	 * Launches a String Attribute Wizard
	 * 
	 * TODO: Jonathan is cleaning up what needs to be
	 * done to launch a wizard. There is repeated
	 * code here that must be cleaned up
	 */
	private void launchStringAttributeEditor(){
    	GUILoadStyleUtility glsu = new GUILoadStyleUtility();
    	StringAttributeWizard SAW = (StringAttributeWizard) glsu.generateGUIPane("/editor/wizards/guipanes/StringAttributeWizard.fxml");
        Scene myScene = new Scene((Parent) SAW.getRoot(), 600, 300);  
        Stage s = new Stage();
        s.setScene(myScene);
        s.show();
		BiConsumer<String, String> bc = (key, stringValue) -> {
			myGameElementInfoBundle.addStringAttribute(key, stringValue);
			s.close();
		};
		SAW.setSubmit(bc);
	}
	
	/**
	 * Launches a Nuumber Attribute Wizard
	 * 
	 * TODO: Jonathan is cleaning up what needs to be
	 * done to launch a wizard. There is repeated
	 * code here that must be cleaned up
	 */
	private void launchNumberAttributeEditor(){
    	GUILoadStyleUtility glsu = new GUILoadStyleUtility();
    	NumberAttributeWizard NAW = (NumberAttributeWizard) glsu.generateGUIPane("/editor/wizards/guipanes/NumberAttributeWizard.fxml");
        Scene myScene = new Scene((Parent) NAW.getRoot(), 600, 300);     
        Stage s = new Stage();
        s.setScene(myScene);
        s.show();
		BiConsumer<String, String> bc = (key, numberValue) -> {
			try{
				myGameElementInfoBundle.addNumberAttribute(key, Double.parseDouble(numberValue));
			}
			catch(Exception e){
				throw e;
			}
			s.close();
		};
		NAW.setSubmit(bc);
	}

	/**
	 * Binds all the listeners to the scene
	 * 
	 */
	@Override
	public void initialize() {
		myGameElementInfoBundle = new GameElementInfoBundle();
		trigger.setOnAction(e -> launchTriggerEditor());
		stringAttribute.setOnAction(e -> launchStringAttributeEditor());
		numberAttribute.setOnAction(e -> launchNumberAttributeEditor());
	}
}
