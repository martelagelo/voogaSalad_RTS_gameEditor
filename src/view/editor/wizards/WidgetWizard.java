package view.editor.wizards;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerTags.AttributeDisplayerType;


/**
 * This represents the Wizard responsible for storing a string attribute along with its attribute
 * name as a key. It only requires both fields to not be null.
 * 
 * @author Joshua, Nishad
 *
 */
public class WidgetWizard extends Wizard {
	
    @FXML
    protected ComboBox<AttributeDisplayerType> displayerTag;
    @FXML
    protected ComboBox<String> attribute;
    @FXML
    protected VBox arguments;
    

    @Override
    public boolean checkCanSave () {
        return areFieldsNotNull();
    }

    protected boolean areFieldsNotNull () {
        return false;
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.STRING_ATTRIBUTE);
    }

    
    @Override
    public void initialize () {
        super.initialize();
        displayerTag.setItems(FXCollections.observableArrayList(AttributeDisplayerType.values()));
        attribute.valueProperty()
        .addListener( (o, oldVal, newVal) -> newAttribute(newVal));
    }
    
    
    private void newAttribute(String newVal) {
    	arguments.getChildren().clear();
		if(!newVal.isEmpty()){
			arguments.getChildren()
			.add(new TextField());
		}
		arguments.getChildren()
		.add(new TextField());
	}

	@Override
    protected void attachTextProperties () {
        super.attachTextProperties();
//        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
//        try {
//            .promptTextProperty().bind(util.getStringProperty(ATTRIBUTE_KEY_KEY));
//            value.promptTextProperty().bind(util.getStringProperty(ATTRIBUTE_VALUE_KEY));
//        }
//        catch (LanguageException e) {
//            displayErrorMessage(e.getMessage());
//        }
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        
    }

    @Override
    public void loadGlobalValues (List<String> values) {
    }
}
