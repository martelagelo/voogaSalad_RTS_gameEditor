package editor;

import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class GameElementAccordianPane extends TitledPane {

    private final static String CREATE_NEW_STRING = "Create new ";
    private String myGameElement;
    private Button myAddElementButton;
    private ListView<String> myListView;
    private ObservableList<String> myElementsList;
    private HashMap<String, Node> myElementsMap;
    
    public GameElementAccordianPane (String gameElement) {
        myGameElement = gameElement;
        initAddElementButton();
        initListView();
        ScrollPane scrollPane = new ScrollPane(myListView);
        VBox box = new VBox();
        myAddElementButton.setMaxWidth(Double.MAX_VALUE);
        scrollPane.setFitToHeight(true);
        box.setPadding(new Insets(0));
        box.getChildren().addAll(myAddElementButton, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        setText(gameElement);
        setContent(box);
    }
    
    private void initListView() {
        myElementsList = FXCollections.observableArrayList();
        myElementsMap = new HashMap<>();
        myListView = new ListView<>();
        myListView.setItems(myElementsList);
        myListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {            
            @Override
            public ListCell<String> call (ListView<String> arg0) {
                return new GameElementListCell();
            }
        });
        myListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed (ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                System.out.println(myElementsMap.get(myElementsList.get(newValue.intValue())));
            }
        });
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
    
    public void addElement(String element, Node image) {
        myElementsList.add(element);
        myElementsMap.put(element, image);
    }
    
    private void initAddElementButton() {
        myAddElementButton = new Button();
        myAddElementButton.setText(CREATE_NEW_STRING + myGameElement);
        myAddElementButton.setOnAction(event->addElement());
    }
    
    private void addElement() {
        System.out.println("clicked");
    }

}
