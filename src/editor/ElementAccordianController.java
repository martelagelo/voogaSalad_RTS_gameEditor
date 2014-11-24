package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.shape.Rectangle;
import view.GUIContainer;
import view.GUILoadStyleUtility;


public class ElementAccordianController extends GUIContainer {

    @FXML
    private Accordion elementAccordian;

    @Override
    public Node getRoot () {
        return null;
    }

    @Override
    public void initialize () {
        String filePath = "/editor/guipanes/GameElementDropDown.fxml";
        ElementDropDownControl dropDownController = (ElementDropDownControl) GUILoadStyleUtility
                .generateGUIPane(filePath);
        dropDownController.setGameElement("Unit");
        dropDownController.addElement("item1", null);
        dropDownController.addElement("item2", new Rectangle(50, 50));

        elementAccordian.getPanes().add((TitledPane) dropDownController.getRoot());
    }

    @Override
    public void update () {

    }

}
