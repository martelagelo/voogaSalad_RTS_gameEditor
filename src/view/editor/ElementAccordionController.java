package view.editor;

import java.awt.Dimension;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.data.WizardData;
import model.data.WizardDataFactory;
import model.data.WizardDataType;
import model.data.WizardType;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.SelectableGameElementState;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.editor.wizards.GameElementWizard;
import view.editor.wizards.WizardUtility;
import view.gui.GUIContainer;
import view.gui.GUIPanePath;


/**
 * Element Accordion Controller to handle communication between model and
 * element accordion as well as to the titled panes in the accordion
 * 
 * @author Jonathan Tseng
 *
 */
public class ElementAccordionController extends GUIContainer {

    private static final String DRAWABLE_KEY = "Drawable";
    private static final String SELECTABLE_KEY = "Selectable";

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
        elementAccordion.expandedPaneProperty().addListener( (property, oldPane, newPane) -> {
            if (oldPane != null)
                oldPane.setCollapsible(true);
            if (newPane != null)
                Platform.runLater(new Runnable() {
                    @Override
                    public void run () {
                        newPane.setCollapsible(false);
                    }
                });
        });
        attachStringProperties();
        terrainTitledPaneController.setButtonAction(openDrawableGameElementWizard(new WizardData()));
        unitTitledPaneController.setButtonAction(openSelectableGameElementWizard(new WizardData()));
        terrainTitledPaneController.setDeleteConsumer( (String elementName) -> {
            try {
                myMainModel.removeDrawableGameElement(elementName);
            }
            catch (Exception e) {
                DialogBoxUtility.createMessageDialog(e.toString());
            }
        });
        unitTitledPaneController.setDeleteConsumer( (String elementName) -> {
            try {
                myMainModel.removeSelectableGameElement(elementName);
            }
            catch (Exception e) {
                DialogBoxUtility.createMessageDialog(e.toString());
            }
        });
        elementAccordion.setExpandedPane(elementAccordion.getPanes()
                .get(elementAccordion.getPanes().size() - 1));
        terrainTitledPaneController
                .setOnSelectionChanged(s -> editorChooseDrawableElement(s));
        unitTitledPaneController
                .setOnSelectionChanged(s -> editorChooseSelectableElement(s));
        terrainTitledPaneController.setEditConsumer((value) -> {});
        unitTitledPaneController.setEditConsumer((value) -> {
            SelectableGameElementState sges = myMainModel.getGameUniverse().getSelectableGameElementState(value);            
            openSelectableGameElementWizard(createWizardData(sges)).accept("");
        });
        terrainTitledPaneController.setEditConsumer((value) -> {
            DrawableGameElementState sges = myMainModel.getGameUniverse().getDrawableGameElementState(value);
            openDrawableGameElementWizard(createWizardData(sges)).accept("");
        });
    }

    private WizardData createWizardData(DrawableGameElementState state) {
        return WizardDataFactory.createWizardData(state);
    }
    
    private void editorChooseDrawableElement (String selection) {
        if (selection != null && !selection.isEmpty()) {
            myMainModel.setEditorChosenDrawable(selection);
        }
    }

    private void editorChooseSelectableElement (String selection) {
        if (selection != null && !selection.isEmpty()) {
            myMainModel.setEditorChosenSelectable(selection);
        }
    }

    private void attachStringProperties () {
        try {
            terrainTitledPaneController.bindGameElement(MultiLanguageUtility.getInstance()
                    .getStringProperty(DRAWABLE_KEY));
            unitTitledPaneController.bindGameElement(MultiLanguageUtility.getInstance()
                    .getStringProperty(SELECTABLE_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            // Should never happen
            DialogBoxUtility.createMessageDialog(e.toString());
        }
    }    
    
    private void updateList (ElementDropDownController dropDownController,
                             List<ImageElementPair> units) {
        dropDownController.clearItems();
        units.forEach( (item) -> {
            dropDownController.addElement(item.myElementName, new ImageView(item.myImage));
        });
    }

    private Consumer<String> openSelectableGameElementWizard (WizardData oldData) {
        Consumer<String> consumer = (c) -> {
                    GameElementWizard wiz =
                            (GameElementWizard) WizardUtility
                                    .loadWizard(GUIPanePath.GAME_ELEMENT_WIZARD,
                                                new Dimension(800, 600));
                    addStringAttributes(wiz);
                    addNumberAttributes(wiz);
                    if (!oldData.getType().equals(WizardType.UNSPECIFIED)) wiz.launchForEdit(oldData);
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
                                    myMainModel.getGameUniverse().removeSelectableGameElementState(data.getValueByKey(WizardDataType.NAME));
                                }
                                else {
                                    myMainModel.createSelectableGameElementState(data);
                                    wiz.closeStage();
                                }
                            };
                    wiz.setSubmit(cons);
                };
        return consumer;
    }

    private Consumer<String> openDrawableGameElementWizard (WizardData oldData) {
        Consumer<String> consumer = (c) -> {
                    GameElementWizard wiz =
                            (GameElementWizard) WizardUtility
                                    .loadWizard(
                                                GUIPanePath.GAME_ELEMENT_WIZARD,
                                                new Dimension(800, 600));
                    addStringAttributes(wiz);
                    addNumberAttributes(wiz);
                    if (!oldData.getType().equals(WizardType.UNSPECIFIED)) wiz.launchForEdit(oldData);
                    
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
                                    myMainModel.getGameUniverse().removeDrawableGameElementState(data.getValueByKey(WizardDataType.NAME));
                                }
                                else {
                                    myMainModel.createDrawableGameElementState(data);
                                    wiz.closeStage();
                                }
                            };
                    wiz.setSubmit(cons);
                };
        return consumer;
    }

    private void addNumberAttributes (GameElementWizard wiz) {
        List<String> numberAttrs = myMainModel.getGameUniverse().getNumericalAttributes().stream()
                .map(atr -> atr.getName()).collect(Collectors.toList());
        wiz.attachNumberAttributes(numberAttrs);
    }

    private void addStringAttributes (GameElementWizard wiz) {
        List<String> stringAttrs = myMainModel.getGameUniverse().getStringAttributes().stream()
                .map(atr -> atr.getName()).collect(Collectors.toList());
        wiz.attachStringAttributes(stringAttrs);
    }

    @Override
    public void modelUpdate () {
        List<ImageElementPair> selectableStates = myMainModel.getGameUniverse()
                .getSelectableGameElementStates().stream().map( (element) -> {
                    try {
                        return new ImageElementPair(null, element.getName());
                    }
                    catch (Exception e) {
                        return new ImageElementPair(null, "failure");
                    }
                }).collect(Collectors.toList());
        List<ImageElementPair> drawableStates =
                myMainModel.getGameUniverse().getDrawableGameElementStates()
                        .stream().map( (element) -> {
                            try {
                                // TODO remove ImageElementPair
                                return new ImageElementPair(null, element.getName());
                            }
                            catch (Exception e) {
                                return new ImageElementPair(null, "failure");
                            }
                        }).collect(Collectors.toList());
        updateList(terrainTitledPaneController, drawableStates);
        updateList(unitTitledPaneController, selectableStates);
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