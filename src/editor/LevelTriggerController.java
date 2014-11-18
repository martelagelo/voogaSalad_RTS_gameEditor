package editor;

import java.util.Observable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import view.GUIController;

public class LevelTriggerController implements GUIController {

    @FXML private ScrollPane triggerRoot;
    
    @Override
    public Node getRoot () {
        return triggerRoot;
    }

    @Override
    public String[] getCSS () {
        return new String[0];
    }

    @Override
    public void initialize () {
        System.out.println("initializing level trigger editor...");
    }

    @Override
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
        
    }
    

}
