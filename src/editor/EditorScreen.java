package editor;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EditorScreen extends BorderPane {

    //@FXML private MenuBar rootViewMenuBar;
//    @FXML private TabPane rootViewTabPane;
//    @FXML private TreeView<String> rootViewProjectExplorer;
//    @FXML private VBox rootViewGameInfoVBox;
//    
    private static final String FXML_STRING = "/editor/guipanes/EditorRoot.fxml";
    
    public EditorScreen (GUIPaneGenerator guiPaneGenerator) {
        guiPaneGenerator.generateGUIPane(FXML_STRING, this);    
    }
    
}
