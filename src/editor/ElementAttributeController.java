package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import view.GUIController;

public class ElementAttributeController implements GUIController {

    @FXML private ScrollPane attributeRoot;
    
    @Override
    public Node getRoot () {
        return attributeRoot;
    }

    @Override
    public String[] getCSS () {
        return new String[0];
    }

    @Override
    public void initialize () {
        System.out.println("initializing element attribute editor...");
    }
    

}
