package view.editor;

import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.LevelState;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.editor.wizards.Wizard;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
import view.editor.wizards.WizardUtility;
import view.gui.GUIPanePath;
import view.gui.MenuBarController;
import view.gui.ViewScreenPath;


/**
 * 
 * @author Nishad Agrawal
 * @author Jonathan Tseng
 *
 */
public class EditorMenuBarController extends MenuBarController {

    private final static String NEW_CAMPAIGN_KEY = "NewCampaign";
    private final static String NEW_LEVEL_KEY = "NewLevel";

    @FXML
    private MenuItem newCampaignMenuItem;
    @FXML
    private MenuItem newLevelMenuItem;

    @Override
    public void initMenuBar () {
        initFileMenu();
    }

    @Override
    protected void bindTextProperties () throws LanguageException {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        newLevelMenuItem.textProperty().bind(util.getStringProperty(NEW_LEVEL_KEY));
        newCampaignMenuItem.textProperty().bind(util.getStringProperty(NEW_CAMPAIGN_KEY));
    }

    private void initFileMenu () {
        newCampaignMenuItem.setOnAction(e -> onNewCampaignClick());
        newLevelMenuItem.setOnAction(e -> onNewLevelClick());
        saveMenuItem.setOnAction(e -> saveGame());
        quitMenuItem.setOnAction(e -> myScreen.switchScreen(ViewScreenPath.SPLASH));
    }
    
    private void saveGame() {
        ((EditorScreen) myScreen).updateModelToSave();
        try {
			LevelState level = myMainModel.getCampaign("shit").getLevel("shit");
	        System.out.println("Model version of level state to save: " + level.toJSON());
		} catch (LevelNotFoundException | CampaignNotFoundException e) {
			e.printStackTrace();
		}
        myMainModel.saveGame();
    }

    private void onNewLevelClick () {
        Wizard wiz = WizardUtility.loadWizard(GUIPanePath.LEVEL_WIZARD, new Dimension(300, 300));
        List<String> existingCampaigns = myMainModel.getCurrentGame().getCampaigns().stream()
                .map(camp -> camp.getName()).collect(Collectors.toList());
        wiz.loadGlobalValues(existingCampaigns);
        Consumer<WizardData> bc = (data) -> {
            try {
                myMainModel.createLevel(data.getValueByKey(WizardDataType.NAME),
                                        data.getValueByKey(WizardDataType.CAMPAIGN_NAME),
                                        Double.parseDouble(data.getValueByKey(WizardDataType.WIDTH)),
                                        Double.parseDouble(data.getValueByKey(WizardDataType.HEIGHT)));
                wiz.closeStage();
            }
            catch (Exception e1) {
                e1.printStackTrace();
                wiz.displayErrorMessage(e1.getMessage());
            }
        };
        wiz.setSubmit(bc);
    }

    private void onNewCampaignClick () {
        Wizard wiz = WizardUtility.loadWizard(GUIPanePath.CAMPAIGN_WIZARD, new Dimension(300, 300));
        Consumer<WizardData> bc = (data) -> {
            try {
                myMainModel.createCampaign(data.getValueByKey(WizardDataType.NAME));
                wiz.closeStage();
            }
            catch (Exception e1) {
                wiz.displayErrorMessage("Campaign Already Exists!");
            }
        };
        wiz.setSubmit(bc);
    }

}
