package view.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import view.gui.GUIController;


/**
 * 
 * Controller for the project explorer
 * 
 * @author Jonathan Tseng
 *
 */
public class ProjectExplorerController implements GUIController {

    @FXML
    private TreeView<String> myTreeView;
    private ProjectExplorerTreeItem<String> myGameNode;
    private ObservableList<TreeItem<String>> myCampaigns;
    private List<ProjectExplorerTreeItem<String>> myLevels;
    private Consumer<String[]> mySelectionChangedConsumer = (String[] s) -> {
    };
    private Consumer<String> myLevelClickedConsumer = (String s) -> {
    };

    public void update (String game,
                        List<String> campaignOrder,
                        Map<String, List<String>> campaignLevelMap) {
        TreeItem<String> selectedItem = myTreeView.getSelectionModel().selectedItemProperty().get();
        int selectedIndex = myTreeView.getSelectionModel().selectedIndexProperty().get();
        myGameNode.setValue(game);
        myCampaigns.clear();
        myLevels.clear();
        campaignOrder.forEach( (campaign) -> {
            ProjectExplorerTreeItem<String> campaignNode = new ProjectExplorerTreeItem<>(campaign);
            campaignNode.getChildren()
                    .addAll(campaignLevelMap
                            .get(campaign)
                            .stream()
                            .map( (level) -> {
                                ProjectExplorerTreeItem<String> levelNode =
                                        new ProjectExplorerTreeItem<>(level);
                                levelNode.setAction(myLevelClickedConsumer);
                                myLevels.add(levelNode);
                                return levelNode;
                            }).collect(Collectors.toList()));
            myCampaigns.add(campaignNode);
        });

        if (myTreeView.getChildrenUnmodifiable().contains(selectedItem)) {
            myTreeView.getSelectionModel().select(selectedItem);
        }
        else if (myTreeView.getChildrenUnmodifiable().size() > selectedIndex) {
            myTreeView.getSelectionModel().select(selectedIndex);
        }
        else {
            myTreeView.getSelectionModel().select(myGameNode);
        }
    }

    public void setOnSelectionChanged (Consumer<String[]> selectionChangedConsumer) {
        mySelectionChangedConsumer = selectionChangedConsumer;
    }

    public void setOnLevelClicked (Consumer<String> levelClicked) {
        myLevelClickedConsumer = levelClicked;
        myLevels.forEach( (levelNode) -> {
            levelNode.setAction(myLevelClickedConsumer);
        });
    }

    private void initListeners () {
        myTreeView.setOnMouseClicked(e -> itemClicked(e));
        myTreeView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(e -> {
                    mySelectionChangedConsumer.accept(getSelectedHierarchy());
                });
    }

    /**
     * ugly code to get the hierarchy for the selected index
     * 
     * @return
     */
    public String[] getSelectedHierarchy () {
        TreeItem<String> selected = myTreeView.getSelectionModel().getSelectedItem();
        if (selected == null || selected.getParent() == null) {
            return new String[] { myGameNode.getValue(), "", "" };
        }
        else if (selected.getParent().getParent() == null) {
            return new String[] { selected.getParent().getValue(), selected.getValue(), "" };
        }
        else {
            return new String[] { selected.getParent().getParent().getValue(),
                                 selected.getParent().getValue(), selected.getValue() };
        }
    }

    private void itemClicked (MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            ProjectExplorerTreeItem<String> selected =
                    (ProjectExplorerTreeItem<String>) myTreeView.getSelectionModel()
                            .getSelectedItem();
            selected.onClicked(selected.getValue());
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
        myLevels = new ArrayList<>();
        myTreeView.setRoot(myGameNode);
        initListeners();
        myTreeView.getSelectionModel().select(myGameNode);
        myTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

}
