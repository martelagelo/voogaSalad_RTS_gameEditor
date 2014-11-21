package editor;

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
public class EditorMenuBarController implements GUIController {

    @FXML
    private MenuBar menuBar;

    @Override
    public Node getRoot () {
        return menuBar;
    }

    @Override
    public void initialize () {
        // TODO Auto-generated method stub
        
    }

}
