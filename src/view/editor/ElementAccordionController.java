package view.editor;

import java.awt.Dimension;
import java.util.List;
import java.util.Optional;
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
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.SelectableGameElementState;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.editor.wizards.SelectableGameElementWizard;
import view.editor.wizards.Wizard;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
import view.editor.wizards.WizardUtility;
import view.gui.GUIContainer;
import view.gui.GUIPanePath;


/**
 * Element Accordion Controller to handle communication between model and element accordion
 * as well as to the titled panes in the accordion
 * 
 * @author Jonathan Tseng
 *
 */
public class ElementAccordionController extends GUIContainer {

    private static final String UNIT_KEY = "Unit";
    private static final String TERRAIN_KEY = "Terrain";

    @FXML
    private Accordion elementAccordion;
    @FXML
    private TitledPane terrainTitledPane;
    @FXML
    private TitledPane unitTitledPane;
    @FXML
    private ElementDropDownController terrainTitledPaneController;
    @FXML
    private ElementDropDownController unitTitledPaneController;

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
        try {
            terrainTitledPaneController.bindGameElement(MultiLanguageUtility.getInstance()
                    .getStringProperty(TERRAIN_KEY));
            unitTitledPaneController.bindGameElement(MultiLanguageUtility.getInstance()
                    .getStringProperty(UNIT_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            // Should never happen
            DialogBoxUtility.createMessageDialog(e.toString());
        }
        terrainTitledPaneController.setButtonAction(openDrawableGameElementWizard());
        unitTitledPaneController.setButtonAction(openSelectableGameElementWizard());
        terrainTitledPaneController.setDeleteConsumer( (String elementName) -> {
            DialogBoxUtility.createMessageDialog("TODO remove shit");
            // myMainModel.removeDrawableGameElement(elementName);
            });
        unitTitledPaneController.setDeleteConsumer( (String elementName) -> {
            DialogBoxUtility.createMessageDialog("TODO remove shit");
            // myMainModel.removeSelectableGameElement(elementName);
            });
        terrainTitledPaneController.setAddToLevelConsumer( (String elementName) -> {
            if (myMainModel.getCurrentLevel() != null) {
                myMainModel.setTerrain(elementName);
            }
        });
        unitTitledPaneController.setAddToLevelConsumer(addUnitToLevel());
    }

    private Consumer<String> addUnitToLevel () {
        return (String elementName) -> {
            if (myMainModel.getCurrentLevel() != null) {
                Wizard wiz =
                        WizardUtility.loadWizard(GUIPanePath.POSITION_WIZARD, new Dimension(300,
                                                                                            300));
                Consumer<WizardData> cons =
                        (data) -> {
                            myMainModel
                                    .addUnitToLevel(elementName,
                                                          Double.parseDouble(data
                                                                  .getValueByKey(WizardDataType.X_POSITION)),
                                                          Double.parseDouble(data
                                                                  .getValueByKey(WizardDataType.Y_POSITION)));
                            wiz.getStage().close();
                        };
                wiz.setSubmit(cons);
            }
            System.out.println("Add to level!");
        };
    }

    private void updateList (ElementDropDownController dropDownController,
                             List<ImageElementPair> units) {
        units.forEach( (item) -> {
            dropDownController.addElement(item.myElementName, new ImageView(item.myImage));
        });
    }

    private Consumer<Consumer<WizardData>> openSelectableGameElementWizard () {        
        Consumer<Consumer<WizardData>> consumer =
                (c) -> {
                    SelectableGameElementWizard wiz = (SelectableGameElementWizard)
                            WizardUtility.loadWizard(GUIPanePath.SELECTABLE_GAME_ELEMENT_WIZARD, new Dimension(800, 600));
                    List<String> stringAttrs = myMainModel.getGameUniverse().
                            getStringAttributes().stream().map(atr -> atr.getName())
                            .collect(Collectors.toList());
                    wiz.attachStringAttributes(stringAttrs);
                    List<String> numberAttrs = myMainModel.getGameUniverse().
                            getNumericalAttributes().stream().map(atr -> atr.getName())
                            .collect(Collectors.toList());
                    wiz.attachNumberAttributes(numberAttrs);

                    Consumer<WizardData> cons =
                            (data) -> {
                                Optional<SelectableGameElementState> sameElementExistsOption =
                                        myMainModel
                                                .getGameUniverse()
                                                .getSelectableGameElementStates()
                                                .stream()
                                                .filter(element -> element
                                                                .getName()
                                                                .equals(data.getValueByKey(WizardDataType.NAME)))
                                                .findFirst();
                                if (sameElementExistsOption.isPresent()) {
                                    wiz.setErrorMesssage("A Selectable Game Element with this name already exists");
                                }
                                else {
                                    myMainModel.createSelectableGameElementState(data);
                                    wiz.getStage().close();
                                }
                            };
                    wiz.setSubmit(cons);
                };
        return consumer;
    }
    
    private Consumer<Consumer<WizardData>> openDrawableGameElementWizard () {
        Consumer<Consumer<WizardData>> consumer =
                (c) -> {
                    SelectableGameElementWizard wiz = (SelectableGameElementWizard)
                            WizardUtility.loadWizard(GUIPanePath.SELECTABLE_GAME_ELEMENT_WIZARD, new Dimension(800, 600));
                    List<String> stringAttrs = myMainModel.getGameUniverse().
                            getStringAttributes().stream().map(atr -> atr.getName())
                            .collect(Collectors.toList());
                    wiz.attachStringAttributes(stringAttrs);
                    List<String> numberAttrs = myMainModel.getGameUniverse().
                            getNumericalAttributes().stream().map(atr -> atr.getName())
                            .collect(Collectors.toList());
                    wiz.attachNumberAttributes(numberAttrs);

                    Consumer<WizardData> cons =
                            (data) -> {
                                Optional<DrawableGameElementState> sameElementExistsOption =
                                        myMainModel
                                                .getGameUniverse()
                                                .getDrawableGameElementStates()
                                                .stream()
                                                .filter(element -> element
                                                                .getName()
                                                                .equals(data.getValueByKey(WizardDataType.NAME)))
                                                .findFirst();
                                if (sameElementExistsOption.isPresent()) {
                                    wiz.setErrorMesssage("A Selectable Game Element with this name already exists");
                                }
                                else {
                                    myMainModel.createDrawableGameElementState(data);
                                    wiz.getStage().close();
                                }
                            };
                    wiz.setSubmit(cons);
                };
        return consumer;
    }

    @Override
    public void update () {
        List<ImageElementPair> selectableStates =
                myMainModel.getGameUniverse().getSelectableGameElementStates().stream()
                        .map( (element) -> {
                            try {
                                // TODO GET IMAGES
                                return new ImageElementPair(null, element.getName());
                            }
                            catch (Exception e) {
                                return new ImageElementPair(null, "failure");
                            }
                        }).collect(Collectors.toList());
        List<ImageElementPair> drawableStates =
                myMainModel.getGameUniverse().getDrawableGameElementStates().stream()
                        .map( (element) -> {
                            try {
                                // TODO GET IMAGES
                                return new ImageElementPair(null, element.getName());
                            }
                            catch (Exception e) {
                                return new ImageElementPair(null, "failure");
                            }
                        }).collect(Collectors.toList());
        updateList(terrainTitledPaneController, drawableStates);
        updateList(unitTitledPaneController, selectableStates);
        elementAccordion.setExpandedPane(terrainTitledPane);
    }

    /**
     * data structure for holding accordion tile view data
     * 
     * @author Jonathan Tseng
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

    @Override
    public Node getRoot () {
        return elementAccordion;
    }

}
