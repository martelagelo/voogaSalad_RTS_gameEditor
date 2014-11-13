package editor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import com.sun.corba.se.pept.transport.EventHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import view.GUIController;

public class ProjectExplorerController implements GUIController {

    @FXML private TreeView<String> myTreeView;
    private ProjectExplorerTreeItem<String> myGameNode;
    private ObservableList<TreeItem<String>> myCampaigns;
    private Map<String, ObservableList<TreeItem<String>>> myLevelMap;

    @Override
    @FXML
    public void initialize () {
        myGameNode = new GameTreeItem<>();
        myCampaigns = myGameNode.getChildren();
        myLevelMap = new HashMap<>();
        myTreeView.setRoot(myGameNode);
        initListeners();
    }

    private void initListeners() {
        myTreeView.setOnMouseClicked(e->itemClicked(e));
    }

    private void itemClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            ProjectExplorerTreeItem<String> selected = (ProjectExplorerTreeItem<String>) myTreeView.getSelectionModel().getSelectedItem();
            selected.onClicked(selected);
        }
    }

    public void bindGameName(SimpleStringProperty gameName) {
        myGameNode.valueProperty().bind(gameName);
    }

    public void addCampaign(String campaignName) {
        ProjectExplorerTreeItem<String> campaignNode = new GameTreeItem<>(campaignName);
        myCampaigns.add(campaignNode);
        myLevelMap.put(campaignName, campaignNode.getChildren());
    }

    public void addLevel(String campaignName, String levelName) {
        myLevelMap.get(campaignName).add(new GameTreeItem<String>(levelName));
    }

    public void setOnGameClicked(Consumer<String> gameClicked) {
        myGameNode.setAction((ProjectExplorerTreeItem<String> item)->{gameClicked.accept(item.getValue());});
    }

    public void setOnCampaignClicked(Consumer<String> campaignClicked) {
        myCampaigns.forEach((treeItem)->
        {
            ProjectExplorerTreeItem<String> explorerItem = (ProjectExplorerTreeItem<String>) treeItem;
            explorerItem.setAction((ProjectExplorerTreeItem<String> item)->
            {
                campaignClicked.accept(item.getValue());
            });
        });
    }

    public void setOnLevelClicked(Consumer<String> levelClicked) {
        myLevelMap.values().forEach((list)->
        {            
            list.forEach((treeItem)->
            {
                ProjectExplorerTreeItem<String> explorerItem = (ProjectExplorerTreeItem<String>) treeItem;
                explorerItem.setAction((ProjectExplorerTreeItem<String> item)->
                {
                    levelClicked.accept(item.getValue());
                });
            });
        });
    }

    @Override
    public String[] getCSS () {
        return new String[0];
    }

    @Override
    public Node getRoot () {
        return myTreeView;
    }

}
