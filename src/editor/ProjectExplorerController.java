package editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
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
    private List<ProjectExplorerTreeItem<String>> myLevels;
    private Consumer<String> mySelectionChangedConsumer = (String s) -> {
    };
    private Consumer<String> myLevelClickedConsumer = (String s) -> {
    };

    public void update (String game, Map<String, List<String>> campaignLevelMap) {
        System.out.println(game);
        TreeItem<String> selectedItem = myTreeView.getSelectionModel().selectedItemProperty().get();
        int selectedIndex = myTreeView.getSelectionModel().selectedIndexProperty().get();
        myGameNode.setValue(game);
        myCampaigns.clear();
        myLevels.clear();
        campaignLevelMap.forEach( (campaign, levels) -> {
            ProjectExplorerTreeItem<String> campaignNode = new ProjectExplorerTreeItem<>(campaign);
            campaignNode.getChildren().addAll(levels.stream().map( (level) -> {
                ProjectExplorerTreeItem<String> levelNode = new ProjectExplorerTreeItem<>(level);
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

    public void setOnSelectionChanged (Consumer<String> selectionChangedConsumer) {
        mySelectionChangedConsumer = selectionChangedConsumer;
    }

    public void setOnLevelClicked (Consumer<String> levelClicked) {
        myLevelClickedConsumer = levelClicked;
        myLevels.forEach((levelNode)->{
            levelNode.setAction(myLevelClickedConsumer);
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
        myTreeView.getSelectionModel().select(myGameNode);
        initListeners();
    }

}
