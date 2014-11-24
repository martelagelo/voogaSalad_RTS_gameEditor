package editor;

import java.util.HashMap;
import java.util.function.Consumer;
import editor.wizards.WizardData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.GUIController;

/**
 * 
 * @author Nishad Agrawal
 * @author Jonathan Tseng
 * 
 */
public class ElementDropDownController implements GUIController {

    private final static String CREATE_NEW_KEY= "CreateNew";

    @FXML
    private Button newElementButton;
    @FXML
    private ListView<String> elementListView;
    @FXML
    private TitledPane elementDropDown;

    private ObservableList<String> myElementsList;
    private HashMap<String, Node> myElementsMap;

    public void addElement (String element, Node image) {
        myElementsList.add(element);
        myElementsMap.put(element, image);
    }
    
    public void setButtonAction (Consumer<Consumer<WizardData>> consumer) {
        newElementButton.setOnAction(e -> consumer.accept(null));
    }

    public void bindGameElement (ObjectProperty<String> elementName) {
        elementDropDown.textProperty().bind(elementName);
        try {
            newElementButton.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty(CREATE_NEW_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            System.out.println(e.toString());
        }
    }
    
    private void initListView () {
        myElementsList = FXCollections.observableArrayList();
        myElementsMap = new HashMap<>();
        elementListView.setItems(myElementsList);
        elementListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call (ListView<String> arg0) {
                return new GameElementListCell();
            }
        });
        elementListView.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed (ObservableValue<? extends Number> value, Number oldValue,
                            Number newValue) {
                        System.out.println(myElementsMap.get(myElementsList.get(newValue.intValue())));
                    }
                });
    }

    private void initNewElementButton () {
        newElementButton.setOnAction(event -> addElement());
    }

    private void addElement () {
        System.out.println("clicked");
    }

    private class GameElementListCell extends ListCell<String> {
        @Override
        public void updateItem (String item, boolean empty) {
            super.updateItem(item, empty);
            setText(item);
            if (item != null) {
                setGraphic(myElementsMap.get(item));
            }
        }
    }

    @Override
    public Node getRoot () {
        return elementDropDown;
    }

    @Override
    public void initialize () {
        initListView();
        initNewElementButton();        
    }
    
}