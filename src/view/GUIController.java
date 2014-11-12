package view;

import javafx.fxml.FXML;
import javafx.scene.Node;

/**
 * 
 * @author Nishad Agrawal
 *
 */
public interface GUIController {
    
    public Node getRoot();

    public String[] getCSS ();

    @FXML
    public void initialize();
}
