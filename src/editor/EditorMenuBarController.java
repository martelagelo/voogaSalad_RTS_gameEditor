package editor;

import java.awt.Dimension;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.GUIContainer;
import view.GUIScreen;
import view.ViewScreen;
import view.WizardUtility;
import editor.wizards.Wizard;
import editor.wizards.WizardData;
import editor.wizards.WizardDataType;


/**
 * 
 * @author Nishad Agrawal
 * @author Jonathan Tseng
 *
 */
public class EditorMenuBarController extends GUIContainer {

    private final static String FILE_KEY = "File";
    private final static String NEW_CAMPAIGN_KEY = "NewCampaign";
    private final static String NEW_LEVEL_KEY = "NewLevel";
    private final static String SAVE_KEY = "Save";
    private final static String QUIT_KEY = "Quit";

    private final static String LANGUAGE_KEY = "Languages";

    private static final String CAMPAIGN_WIZARD = "/editor/wizards/guipanes/CampaignWizard.fxml";
    private static final String LEVEL_WIZARD = "/editor/wizards/guipanes/LevelWizard.fxml";

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem newCampaignMenuItem;
    @FXML
    private MenuItem newLevelMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem quitMenuItem;

    @FXML
    private Menu languageMenu;

    private GUIScreen myScreen;

    @Override
    public Node getRoot () {
        return menuBar;
    }

    @Override
    public void init () {
        attachTextProperties();
        initLanguageMenu();
        initFileMenu();
    }

    private void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            fileMenu.textProperty().bind(util.getStringProperty(FILE_KEY));
            newLevelMenuItem.textProperty().bind(util.getStringProperty(NEW_LEVEL_KEY));
            newCampaignMenuItem.textProperty().bind(util.getStringProperty(NEW_CAMPAIGN_KEY));
            saveMenuItem.textProperty().bind(util.getStringProperty(SAVE_KEY));
            quitMenuItem.textProperty().bind(util.getStringProperty(QUIT_KEY));
            languageMenu.textProperty().bind(util.getStringProperty(LANGUAGE_KEY));
        }
        catch (LanguageException e) {
            // TODO
        }
    }

    private void initLanguageMenu () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        util.getSupportedLanguages().get().forEach( (language) -> {
            MenuItem languageMenuItem = new MenuItem(language);
            languageMenuItem.setOnAction(e -> {
                try {
                    util.setLanguage(language);
                }
                catch (LanguageException exc) {
                    // Should never happen
                }
            });
            languageMenu.getItems().add(languageMenuItem);
        });
    }

    private void initFileMenu () {
        newCampaignMenuItem.setOnAction(e -> onNewCampaignClick());
        newLevelMenuItem.setOnAction(e -> onNewLevelClick());
        saveMenuItem.setOnAction(e -> myMainModel.saveGame());
        quitMenuItem.setOnAction(e -> myScreen.switchScreen(ViewScreen.SPLASH));
    }

    public void attachScreen (GUIScreen screen) {
        myScreen = screen;
    }

    private void onNewLevelClick () {
        Wizard wiz = WizardUtility.loadWizard(LEVEL_WIZARD, new Dimension(300, 300));
        Consumer<WizardData> bc =
                (data) -> {
                    try {
                        myMainModel.createLevel(data.getValueByKey(WizardDataType.NAME),
                                                data.getValueByKey(WizardDataType.CAMPAIGN));
                        wiz.getStage().close();
                    }
                    catch (Exception e1) {
                        wiz.setErrorMesssage(e1.getMessage());
                    }
                };
        wiz.setSubmit(bc);
    }

    private void onNewCampaignClick () {
        Wizard wiz = WizardUtility.loadWizard(CAMPAIGN_WIZARD, new Dimension(300, 300));
        Consumer<WizardData> bc = (data) -> {
            try {
                myMainModel.createCampaign(data.getValueByKey(WizardDataType.NAME));
                wiz.getStage().close();
            }
            catch (Exception e1) {
                wiz.setErrorMesssage("Campaign Already Exists!");
            }
        };
        wiz.setSubmit(bc);
    }

    @Override
    public void update () {
        // nothing to update
    }

}
