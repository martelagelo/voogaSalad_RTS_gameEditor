package editor;

import java.awt.Dimension;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.GUIContainer;
import view.GUILoadStyleUtility;
import view.WizardUtility;
import editor.wizards.DrawableGameElementWizard;
import editor.wizards.Wizard;
import editor.wizards.WizardData;
import game_engine.gameRepresentation.stateRepresentation.gameElement.Attribute;


public class ElementAccordionController extends GUIContainer {

    // private static final String TERRAIN_WIZARD = "/editor/wizards/guipanes/TerrainWizard.fxml";
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
        elementAccordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
            @Override
            public void changed (ObservableValue<? extends TitledPane> property,
                                 final TitledPane oldPane,
                                 final TitledPane newPane) {
                if (oldPane != null) oldPane.setCollapsible(true);
                if (newPane != null) Platform.runLater(new Runnable() {
                    @Override
                    public void run () {
                        newPane.setCollapsible(false);
                    }
                });
            }
        });
    }

    private void updateItems (List<ImageElementPair> items) {
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
        elementAccordion.setExpandedPane(elementAccordion.getPanes().get(0));
    }

    private Consumer<Consumer<WizardData>> openGameElementWizard () {
        Consumer<Consumer<WizardData>> consumer = (c) -> {
            DrawableGameElementWizard wiz = (DrawableGameElementWizard)
                    WizardUtility.loadWizard(GAME_ELEMENT_WIZARD, new Dimension(800, 600));
            List<String> stringAttrs = myMainModel.getGameUniverse().
                    getStringAttributes().stream().map(atr -> atr.getName())
                    .collect(Collectors.toList());            
            wiz.attachStringAttributes(stringAttrs);
            List<String> numberAttrs = myMainModel.getGameUniverse().
                    getNumericalAttributes().stream().map(atr -> atr.getName())
                    .collect(Collectors.toList());
            wiz.attachNumberAttributes(numberAttrs);
            
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
        List<ImageElementPair> states =
                myMainModel.getGameUniverse().getDrawableGameElementStates().stream()
                        .map( (element) -> {
                            try {
                                return new ImageElementPair(null, element.getName());
                                // return new
                                // ImageElementPair(SaveLoadUtility.loadImage(element.getSpritesheet().imageTag),
                                // element.getName());
                            }
                            catch (Exception e) {
                                System.out.println(e.toString());
                                return new ImageElementPair(null, "failure");
                            }
                        }).collect(Collectors.toList());
        updateItems(states);
    }

    /**
     * data structure for holding accordion tile view data
     * 
     * @author Jonathan Tseng, Nishad Agrawal
     *
     */
    private class ImageElementPair {
        Image myImage;
        String myElementName;

        public ImageElementPair (Image image, String elementName) {
            myImage = image;
            myElementName = elementName;
        }
    }

}
