package editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import view.GUIController;


/**
 * 
 * @author Jonathan Tseng
 *
 */
public class ProjectExplorerController implements GUIController {

    @FXML
    private TreeView<String> myTreeView;
    private ProjectExplorerTreeItem<String> myGameNode;
    private ObservableList<TreeItem<String>> myCampaigns;
    private Map<String, ObservableList<TreeItem<String>>> myLevelMap;
    private Consumer<String> mySelectionChangedConsumer = (String s) -> {
    };

    public ObjectProperty<String> selectedProjectItemProperty () {
        return myTreeView.getSelectionModel().selectedItemProperty().get().valueProperty();
    }

    public void bindGameName (SimpleStringProperty gameName) {
        myGameNode.valueProperty().bind(gameName);
    }

    public void addCampaigns (String ... campaignNames) {
        Arrays.stream(campaignNames)
                .forEach( (campaignName) ->
                {
                    ProjectExplorerTreeItem<String> campaignNode =
                            new ProjectExplorerTreeItem<>(campaignName);
                    myCampaigns.add(campaignNode);
                    myLevelMap.put(campaignName, campaignNode.getChildren());
                });
    }

    public void addLevels (String campaignName, String ... levelNames) {
        Arrays.stream(levelNames).forEach( (levelName) ->
        {
            myLevelMap.get(campaignName).add(new ProjectExplorerTreeItem<>(levelName));
        });
    }

    public void setOnSelectionChanged (Consumer<String> selectionChangedConsumer) {
        mySelectionChangedConsumer = selectionChangedConsumer;
    }

    public void setOnLevelClicked (Consumer<String> levelClicked) {
        myLevelMap.values()
                .forEach( (list) ->
                {
                    list.forEach( (treeItem) ->
                    {
                        ProjectExplorerTreeItem<String> explorerItem =
                                (ProjectExplorerTreeItem<String>) treeItem;
                        explorerItem.setAction( (ProjectExplorerTreeItem<String> item) ->
                        {
                            levelClicked.accept(item.getValue());
                        });
                    });
                });
    }

    private void initListeners () {
        myTreeView.setOnMouseClicked(e -> itemClicked(e));
        myTreeView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(e -> mySelectionChangedConsumer.accept(myTreeView.getSelectionModel()
                        .getSelectedItem().getValue()));
    }

    private void itemClicked (MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            ProjectExplorerTreeItem<String> selected =
                    (ProjectExplorerTreeItem<String>) myTreeView.getSelectionModel()
                            .getSelectedItem();
            selected.onClicked(selected);
        }
    }

    @Override
    public Node getRoot () {
        return myTreeView;
    }

    @Override
    public void initialize () {
        myGameNode = new ProjectExplorerTreeItem<>();
        myCampaigns = myGameNode.getChildren();
        myLevelMap = new HashMap<>();
        myTreeView.setRoot(myGameNode);
        myTreeView.getSelectionModel().select(myGameNode);
        initListeners();
    }

}
