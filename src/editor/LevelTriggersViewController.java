package editor;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import view.GUIController;
import editor.TabViewController.TriggerPair;
import editor.wizards.WizardData;


/**
 * This class represents the pane held within a TabPane representing all of the Triggers/Goals
 * associated with a level. It holds a button to add a new Trigger/Goal along with a clickable list
 * of the existing Goals. Both of these elements will spawn a TriggerWizard upon double-click. The
 * publically accessible methods of this class include setting the onButtonClick and the
 * onSelectedClick within the list view. Furthermore, one has the ability to update the list of
 * triggers with a list of TriggerPair elements.
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class LevelTriggersViewController implements GUIController {

    @FXML
    private VBox levelTriggersView;
    @FXML
    private Button newLevelTrigger;
    @FXML
    private ListView<String> levelTriggers;

    private ObservableList<String> myTriggerList;
    
    private BiConsumer<Integer, String> deleteConsumer; 

    /**
     * 
     * @param consumer The consumer to execute when the new button is clicked
     */
    public void setButtonAction (Consumer<Consumer<WizardData>> consumer) {
        newLevelTrigger.setOnAction(e -> consumer.accept(null));
    }

    /**
     * 
     * @param consumer The consumer to execute when an item in the list is selected
     */
    public void setSelectedAction (BiConsumer<Integer, String> consumer) {
        levelTriggers.setOnMouseClicked(e -> itemClicked(2, e, consumer));
    }
    
    public void setDeleteAction(BiConsumer<Integer, String> consumer) {
        deleteConsumer = consumer;
    }

    @Override
    public Node getRoot () {
        return levelTriggersView;
    }

    @Override
    public void initialize () {
        initListView();
        deleteConsumer = (Integer i, String s) -> {
            System.out.println("delete something here");
        };
    }

    private void initListView () {
        myTriggerList = FXCollections.observableArrayList();
        levelTriggers.setItems(myTriggerList);
        levelTriggers.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call (ListView<String> arg0) {
                return new TriggerListCell();
            }
        });
        levelTriggers.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed (ObservableValue<? extends Number> value, Number oldValue,
                                         Number newValue) {
                    }
                });
    }

    /**
     * This method sends the currently selected text and position within the list of the selected
     * item into the consumer when an element in the list of triggers has been double clicked.
     * 
     * @param mouseEvent
     * @param consumer
     */
    private void itemClicked (int clicks, MouseEvent mouseEvent, BiConsumer<Integer, String> consumer) {
        if (mouseEvent.getClickCount() == clicks) {
            String action = levelTriggers.getSelectionModel()
                    .getSelectedItem();
            System.out.println(action);
            System.out.println(levelTriggers.getSelectionModel().getSelectedIndex());

            consumer.accept(levelTriggers.getSelectionModel().getSelectedIndex(), action);
        }
    }
    
    private void buttonClicked (BiConsumer<Integer, String> consumer) {
        //TODO: position is always -1 because of nothing is selected when button is clicked
        String action = levelTriggers.getSelectionModel().getSelectedItem();
        consumer.accept(levelTriggers.getSelectionModel().getSelectedIndex(), action);
    }

    /**
     * 
     * @param triggers The list of values to pass into the ListView
     */
    public void updateTriggerList (List<TriggerPair> triggers) {
        myTriggerList.clear();
        triggers.forEach( (trigger) -> myTriggerList.add(trigger.myActionType + "\n" +
                                                         trigger.myAction));
    }
    
    /**
     * 
     * An internal class to populate the cells within our ListView.
     *
     */
    private class TriggerListCell extends ListCell<String> {
        HBox trigger;
        Label label;
        Pane pane;
        Button button;
        
        public TriggerListCell () {
            super();
            trigger = new HBox();
            label = new Label();
            pane = new Pane();
            button = new Button("X");
            button.setOnAction(e -> buttonClicked(deleteConsumer));
            trigger.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);       
        }
        
        @Override
        public void updateItem (String item, boolean empty) {
            super.updateItem(item, empty);                         
            setText(null);  // No text in label of super class
            if (empty || item == null) {
                setGraphic(null);
            } else {
                label.setText(item);
                setGraphic(trigger);
            }
        }
        
    }
   
}
