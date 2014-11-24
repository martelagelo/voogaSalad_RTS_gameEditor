package editor;

import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.GUIContainer;
import view.GUILoadStyleUtility;
import view.WizardUtility;
import editor.EditorScreen.ImageElementPair;
import editor.wizards.Wizard;
import editor.wizards.WizardData;


public class ElementAccordionController extends GUIContainer {

    private static final String TERRAIN_WIZARD = "/editor/wizards/guipanes/TerrainWizard.fxml";
    private static final String GAME_ELEMENT_WIZARD =
            "/editor/wizards/guipanes/DrawableGameElementWizard.fxml";
    private static final String ACCORDION_SUBPANE_PATH =
            "/editor/guipanes/GameElementDropDown.fxml";

    private static final String ELEMENT_KEY = "GameElement";

    @FXML
    private Accordion elementAccordion;

    @Override
    public Node getRoot () {
        return null;
    }

    @Override
    public void init () {
    }

    public void updateItems (List<ImageElementPair> items) {
        elementAccordion.getPanes().clear();
        ElementDropDownController dropDownController =
                (ElementDropDownController) GUILoadStyleUtility
                        .generateGUIPane(ACCORDION_SUBPANE_PATH);
        try {
            dropDownController.bindGameElement(MultiLanguageUtility.getInstance()
                    .getStringProperty(ELEMENT_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            System.out.println(e.toString());
        }
        items.forEach( (item) -> {
            dropDownController.addElement(item.myElementName, new ImageView(item.myImage));
        });
        dropDownController.setButtonAction(openGameElementWizard());
        elementAccordion.getPanes().add((TitledPane) dropDownController.getRoot());
    }

    private Consumer<Consumer<WizardData>> openGameElementWizard () {
        Consumer<Consumer<WizardData>> consumer = (c) -> {
            Wizard wiz = WizardUtility.loadWizard(GAME_ELEMENT_WIZARD, new Dimension(800, 600));
            Consumer<WizardData> cons = (data) -> {
                myMainModel.createDrawableGameElement(data);
                wiz.getStage().close();
            };
            wiz.setSubmit(cons);
        };
        return consumer;
    }

    @Override
    public void update () {

    }

}
