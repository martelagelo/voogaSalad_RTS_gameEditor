package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import view.GUIController;

/**
 * 
 * @author Nishad Agrawal
 *
 */
public class EditorMenuBar implements GUIController {

    @FXML
    private MenuBar menuBar;

    @Override
    @FXML
    public void initialize () {
        System.out.println("initializing menu bar...");
    }

    @Override
    public String[] getCSS () {
        return new String[] { "/editor/stylesheets/editorMenuBar.css" };
    }

    @Override
    public Node getRoot () {
        return menuBar;
    }

}
