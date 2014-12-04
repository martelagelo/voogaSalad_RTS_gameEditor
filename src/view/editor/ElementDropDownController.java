package view.editor;

import java.util.HashMap;
import java.util.function.Consumer;
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
import view.editor.wizards.WizardData;
import view.gui.GUIController;


/**
 * 
 * @author Nishad Agrawal
 * @author Jonathan Tseng
 * 
 */
public class ElementDropDownController implements GUIController {

    private final static String DELETE_SELECTED_KEY = "DeleteSelected";
    private final static String CREATE_NEW_KEY = "CreateNew";

    @FXML
    private Button newElementButton;
    @FXML
    private Button deleteElementButton;
    @FXML
    private Button addToLevelButton;
    @FXML
    private ListView<String> elementListView;
    @FXML
    private TitledPane elementDropDown;

    private HashMap<String, Node> myElementsMap;
    private Consumer<String> myDeletionConsumer = (String element) -> {
    };
    private Consumer<String> myAddToLevelConsumer = (String element) -> {
    };

    public void addElement (String element, Node image) {
        if (!elementListView.getItems().contains(element)) {
            elementListView.getItems().add(element);
            myElementsMap.put(element, image);
        }
    }

    public void setButtonAction (Consumer<Consumer<WizardData>> consumer) {
        newElementButton.setOnAction(e -> consumer.accept(null));
    }

    public void setDeleteConsumer (Consumer<String> deleteConsumer) {
        myDeletionConsumer = deleteConsumer;
    }
    
    public void setAddToLevelConsumer (Consumer<String> levelConsumer) {
        myAddToLevelConsumer = levelConsumer;
    }

    public void bindGameElement (ObjectProperty<String> elementName) {
        elementDropDown.textProperty().bind(elementName);
    }

    private void initListView () {
        ObservableList<String> myElementsList = FXCollections.observableArrayList();
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
                        //TODO: drag and drop goes here
                    }
                });
    }

    private void initDeleteElementButton () {
        deleteElementButton.setOnAction(event -> {
            String selected = elementListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                 elementListView.getItems().remove(selected);
                 myElementsMap.remove(selected);
                myDeletionConsumer.accept(selected);
            }
        });
    }
    
    private void initAddToLevelButton () {
        addToLevelButton.setOnAction(event -> {
            String selected = elementListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                myAddToLevelConsumer.accept(selected);
            }
        });
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
        initDeleteElementButton();
        initAddToLevelButton();

        try {
            newElementButton.textProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(CREATE_NEW_KEY));
            deleteElementButton.textProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(DELETE_SELECTED_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            e.printStackTrace();
        }
    }    

}
