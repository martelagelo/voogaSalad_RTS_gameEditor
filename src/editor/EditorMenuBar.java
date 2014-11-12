package editor;

import javafx.scene.control.MenuBar;

public class EditorMenuBar extends MenuBar {

    private static final String FXML_STRING = "/editor/guipanes/EditorMenuBar.fxml";
    
    public EditorMenuBar (GUIPaneGenerator guiPaneGenerator) {
        guiPaneGenerator.generateGUIPane(FXML_STRING, this);    
    }

}
