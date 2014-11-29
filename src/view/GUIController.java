package view;

import javafx.fxml.FXML;
import javafx.scene.Node;


/**
 * 
 * @author Jonathan Tseng, Nishad Agrawal
 *
 */
public interface GUIController {

    public abstract Node getRoot ();

    @FXML
    public abstract void initialize ();

}
