package editor;

import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.GUIContainer;
import view.GUILoadStyleUtility;
import editor.wizards.CampaignWizard;
import editor.wizards.LevelWizard;
import editor.wizards.WizardData;
import gamemodel.GameElementStateFactory;


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
    private Menu languageMenu;

    @Override
    public Node getRoot () {
        return menuBar;
    }

    @Override
    public void init () {
        if (myMainModel == null) {
            System.out.println("null model");
        }
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
            languageMenu.textProperty().bind(util.getStringProperty(LANGUAGE_KEY));
        }
        catch (LanguageException e) {
            // TODO
        }
    }

    private void initLanguageMenu () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        util.getSupportedLanguages().get().forEach( (language) -> {
            System.out.println(language);
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
        newCampaignMenuItem.setOnAction(e -> {
            CampaignWizard wiz =
                    (CampaignWizard) GUILoadStyleUtility
                            .generateGUIPane(CAMPAIGN_WIZARD);
            Scene myScene = new Scene((Parent) wiz.getRoot(), 600, 300);
            Stage s = new Stage();
            s.setScene(myScene);
            s.show();
            Consumer<WizardData> bc = (data) -> {
                try {
                    System.out.println(data.getValueByKey(GameElementStateFactory.NAME));
                    if (myMainModel == null) {
                        System.out.println("null model");
                    }
                    myMainModel.createCampaign(data.getValueByKey(GameElementStateFactory.NAME));
                    s.close();
                }
                catch (Exception e1) {
                    wiz.setErrorMesssage("Campaign Already Exists!");
                }                
            };
            wiz.setSubmit(bc);
        });
        newLevelMenuItem.setOnAction(e -> {
            LevelWizard wiz =
                    (LevelWizard) GUILoadStyleUtility
                            .generateGUIPane(LEVEL_WIZARD);
            Scene myScene = new Scene((Parent) wiz.getRoot(), 600, 300);
            Stage s = new Stage();
            s.setScene(myScene);
            s.show();
            Consumer<WizardData> bc = (data) -> {
                try {
                    myMainModel.createLevel(data.getValueByKey(GameElementStateFactory.NAME), data.getValueByKey(GameElementStateFactory.CAMPAIGN));
                    s.close();
                }
                catch (Exception e1) {
                    wiz.setErrorMesssage(e1.getMessage());                   
                }                
            };
            wiz.setSubmit(bc);
        });
        saveMenuItem.setOnAction(e -> {
            // TODO SAVE
            });
    }

    @Override
    public void update () {
        // nothing to update
    }

}
