package view;

import java.util.Observer;
import javafx.fxml.FXML;
import javafx.scene.Node;

/**
 * 
 * @author Nishad Agrawal
 *
 */
public interface GUIController extends Observer {
    
    public Node getRoot();

    public String[] getCSS ();

    @FXML
    public void initialize();
}
