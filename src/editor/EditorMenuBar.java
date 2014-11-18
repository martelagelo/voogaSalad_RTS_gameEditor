package editor;

import java.util.Observable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import view.GUIController;

/**
 * 
 * @author Nishad Agrawal
 * @author Jonathan Tseng
 *
 */
public class EditorMenuBar implements GUIController {

    @FXML
    private MenuBar menuBar;

    @Override
    @FXML
    public void initialize () {
        System.out.println("(TODO) initializing menu bar...");
    }

    @Override
    public String[] getCSS () {
        return new String[] { "/editor/stylesheets/editorMenuBar.css" };
    }

    @Override
    public Node getRoot () {
        return menuBar;
    }

    @Override
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
        
    }

}
