package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import view.GUIController;


/**
 * 
 * @author Nishad Agrawal
 * @author Jonathan Tseng
 */
public class ElementDropDownControl implements GUIController {

    @FXML
    private TitledPane elementDropDown;

    @FXML
    public void initialize () {

    }

    @Override
    public String[] getCSS () {
        return new String[] { "/editor/stylesheets/editorRoot.css" };
    }

    @Override
    public Node getRoot () {
        return elementDropDown;
    }

}
