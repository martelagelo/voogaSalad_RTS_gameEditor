package editor;

import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
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
import view.GUIController;


/**
 * 
 * @author Nishad Agrawal
 * @author Jonathan Tseng
 * 
 */
public class ElementDropDownControl implements GUIController {

    private final static String CREATE_NEW_STRING = "Create new ";
    
    @FXML private Button newElementButton;
    @FXML private ListView<String> elementListView;
    @FXML private TitledPane elementDropDown;
    
    private ObservableList<String> myElementsList;
    private HashMap<String, Node> myElementsMap;
    private String myGameElement;
    
    private SimpleStringProperty myButtonText;
    
    public void addElement(String element, Node image) {
        myElementsList.add(element);
        myElementsMap.put(element, image);
    }
    
    public void setGameElement(String gameElement) {
        myGameElement = gameElement;
        elementDropDown.setText(myGameElement);
        myButtonText.setValue(CREATE_NEW_STRING + myGameElement);
    }
    
    @Override
    @FXML
    public void initialize () {
        myButtonText = new SimpleStringProperty();
        initListView();
        initNewElementButton();
    }

    private void initListView() {
        myElementsList = FXCollections.observableArrayList();
        myElementsMap = new HashMap<>();
        elementListView.setItems(myElementsList);
        elementListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {            
            @Override
            public ListCell<String> call (ListView<String> arg0) {
                return new GameElementListCell();
            }
        });
        elementListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed (ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                System.out.println(myElementsMap.get(myElementsList.get(newValue.intValue())));
            }
        });
    }
    
    private void initNewElementButton() {
        newElementButton.textProperty().bind(myButtonText);
        newElementButton.setOnAction(event->addElement());
    }
    
    private void addElement() {
        System.out.println("clicked");
    }
    
    private class GameElementListCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(item);
            if (item != null) {
                setGraphic(myElementsMap.get(item));
            }
        }
    }
    
    @Override
    public String[] getCSS () {
        return new String[0];
    }

    @Override
    public Node getRoot () {
        return elementDropDown;
    }

}