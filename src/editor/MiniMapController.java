package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import view.GUIController;

public class MiniMapController implements GUIController {

    @FXML private ScrollPane miniMap;
    
    @Override
    public Node getRoot () {
        return miniMap;
    }

    @Override
    public String[] getCSS () {
        return new String[0];
    }

    @Override
    public void initialize () {
        System.out.println("initializing mini map...");
    }
    

}
