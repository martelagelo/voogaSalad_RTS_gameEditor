package editor;

import java.util.List;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import view.GUIController;
import editor.TabViewController.TriggerPair;
import editor.wizards.WizardData;


/**
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

    public void setButtonAction (Consumer<Consumer<WizardData>> consumer) {
        newLevelTrigger.setOnAction(e -> consumer.accept(null));
    }

    @Override
    public Node getRoot () {
        return levelTriggersView;
    }

    @Override
    public void initialize () {
        initListView();
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

    public void updateTriggerList (List<TriggerPair> triggers) {
        myTriggerList.clear();
        triggers.forEach((trigger) -> myTriggerList.add(trigger.myActionType + "\n" + trigger.myAction));
    }
    
    private class TriggerListCell extends ListCell<String> {
        @Override
        public void updateItem (String item, boolean empty) {
            super.updateItem(item, empty);
            setText(item);
        }
    }

}
