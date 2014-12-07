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
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.LevelState;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.SelectableGameElementState;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.editor.wizards.DrawableGameElementWizard;
import view.editor.wizards.Wizard;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
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

    private LevelState myLevel;

    public void setLevel (String campaign, String level) throws LevelNotFoundException,
            CampaignNotFoundException {
        myLevel = myMainModel.getLevel(campaign, level);
    }

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
        terrainTitledPaneController.setButtonAction(openDrawableGameElementWizard());
        unitTitledPaneController.setButtonAction(openSelectableGameElementWizard());
        terrainTitledPaneController.setDeleteConsumer( (String elementName) -> {
            try {
                myMainModel.removeDrawableGameElement(elementName);
            } catch (Exception e) {
                DialogBoxUtility.createMessageDialog(e.toString());
            }
        });
        unitTitledPaneController.setDeleteConsumer( (String elementName) -> {
            try {
                myMainModel.removeSelectableGameElement(elementName);
            } catch (Exception e) {
                DialogBoxUtility.createMessageDialog(e.toString());
            }
        });
        terrainTitledPaneController.setAddToLevelConsumer(addTerrainToLevel());
        unitTitledPaneController.setAddToLevelConsumer(addUnitToLevel());
        elementAccordion.setExpandedPane(elementAccordion.getPanes().get(
                elementAccordion.getPanes().size() - 1));
        terrainTitledPaneController
                .setOnSelectionChanged( (String s) -> editorChooseDrawableElement(s));
        unitTitledPaneController
                .setOnSelectionChanged( (String s) -> editorChooseSelectableElement(s));
    }

    private void editorChooseDrawableElement (String selection) {
        if (selection != null && !selection.isEmpty()) {
            myMainModel.setEditorDrawableChosen(selection);
        }
    }

    private void editorChooseSelectableElement (String selection) {
        if (selection != null && !selection.isEmpty()) {
            myMainModel.setEditorSelectableChosen(selection);
        }
    }

    private void attachStringProperties () {
        try {
            terrainTitledPaneController.bindGameElement(MultiLanguageUtility.getInstance()
                    .getStringProperty(DRAWABLE_KEY));
            unitTitledPaneController.bindGameElement(MultiLanguageUtility.getInstance()
                    .getStringProperty(SELECTABLE_KEY));
        } catch (LanguagePropertyNotFoundException e) {
            // Should never happen
            DialogBoxUtility.createMessageDialog(e.toString());
        }
    }

    private Consumer<String> addTerrainToLevel () {
        return (String elementName) -> {
            if (myLevel != null) {
                Wizard wiz = WizardUtility.loadWizard(GUIPanePath.POSITION_WIZARD, new Dimension(
                        300, 300));
                Consumer<WizardData> cons = (data) -> {
                    try {
                        myMainModel.addTerrainToLevel(myLevel, elementName,
                                Double.parseDouble(data.getValueByKey(WizardDataType.X_POSITION)),
                                Double.parseDouble(data.getValueByKey(WizardDataType.Y_POSITION)));
                        wiz.closeStage();
                    } catch (Exception e) {
                        wiz.displayErrorMessage(e.getMessage());
                    }
                };
                wiz.setSubmit(cons);
                System.out.println("adding terrain to level");
            }
        };
    }

    private Consumer<String> addUnitToLevel () {
        return (String elementName) -> {
            if (myLevel != null) {
                Wizard wiz = WizardUtility.loadWizard(GUIPanePath.POSITION_WIZARD, new Dimension(
                        300, 300));
                Consumer<WizardData> cons = (data) -> {
                    try {
                        myMainModel.addUnitToLevel(myLevel, elementName,
                                Double.parseDouble(data.getValueByKey(WizardDataType.X_POSITION)),
                                Double.parseDouble(data.getValueByKey(WizardDataType.Y_POSITION)));
                        wiz.closeStage();
                    } catch (Exception e) {
                        wiz.displayErrorMessage(e.getMessage());
                    }
                };
                System.out.println("adding unit to level");
                wiz.setSubmit(cons);
            }
        };
    }

    private void updateList (ElementDropDownController dropDownController,
            List<ImageElementPair> units) {
        units.forEach( (item) -> {
            dropDownController.addElement(item.myElementName, new ImageView(item.myImage));
        });
    }

    private Consumer<Consumer<WizardData>> openSelectableGameElementWizard () {
        Consumer<Consumer<WizardData>> consumer = (c) -> {
            DrawableGameElementWizard wiz = (DrawableGameElementWizard) WizardUtility.loadWizard(
                    GUIPanePath.DRAWABLE_GAME_ELEMENT_WIZARD, new Dimension(800, 600));
            addStringAttributes(wiz);
            addNumberAttributes(wiz);

            Consumer<WizardData> cons = (data) -> {
                Optional<SelectableGameElementState> sameElementExistsOption = myMainModel
                        .getGameUniverse()
                        .getSelectableGameElementStates()
                        .stream()
                        .filter(element -> element.getName().equals(
                                data.getValueByKey(WizardDataType.NAME))).findFirst();
                if (sameElementExistsOption.isPresent()) {
                    wiz.displayErrorMessage("A Selectable Game Element with this name already exists");
                } else {
                    myMainModel.createSelectableGameElementState(data);
                    wiz.closeStage();
                }
            };
            wiz.setSubmit(cons);
        };
        return consumer;
    }

    private Consumer<Consumer<WizardData>> openDrawableGameElementWizard () {
        Consumer<Consumer<WizardData>> consumer = (c) -> {
            // TODO: make a drawable ges wizard
            DrawableGameElementWizard wiz = (DrawableGameElementWizard) WizardUtility.loadWizard(
                    GUIPanePath.DRAWABLE_GAME_ELEMENT_WIZARD, new Dimension(800, 600));
            addStringAttributes(wiz);
            addNumberAttributes(wiz);

            Consumer<WizardData> cons = (data) -> {
                Optional<DrawableGameElementState> sameElementExistsOption = myMainModel
                        .getGameUniverse()
                        .getDrawableGameElementStates()
                        .stream()
                        .filter(element -> element.getName().equals(
                                data.getValueByKey(WizardDataType.NAME))).findFirst();
                if (sameElementExistsOption.isPresent()) {
                    wiz.displayErrorMessage("A Drawable Game Element with this name already exists");
                } else {
                    myMainModel.createDrawableGameElementState(data);
                    wiz.closeStage();
                }
            };
            wiz.setSubmit(cons);
        };
        return consumer;
    }

    private void addNumberAttributes (DrawableGameElementWizard wiz) {
        List<String> numberAttrs = myMainModel.getGameUniverse().getNumericalAttributes().stream()
                .map(atr -> atr.getName()).collect(Collectors.toList());
        wiz.attachNumberAttributes(numberAttrs);
    }

    private void addStringAttributes (DrawableGameElementWizard wiz) {
        List<String> stringAttrs = myMainModel.getGameUniverse().getStringAttributes().stream()
                .map(atr -> atr.getName()).collect(Collectors.toList());
        wiz.attachStringAttributes(stringAttrs);
    }

    @Override
    public void modelUpdate () {
        List<ImageElementPair> selectableStates = myMainModel.getGameUniverse()
                .getSelectableGameElementStates().stream().map( (element) -> {
                    try {
                        // TODO GET IMAGES
                        return new ImageElementPair(null, element.getName());
                    } catch (Exception e) {
                        return new ImageElementPair(null, "failure");
                    }
                }).collect(Collectors.toList());
        List<ImageElementPair> drawableStates = myMainModel.getGameUniverse()
                .getDrawableGameElementStates().stream().map( (element) -> {
                    try {
                        // TODO GET IMAGES
                        return new ImageElementPair(null, element.getName());
                    } catch (Exception e) {
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
