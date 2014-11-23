package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.GUIContainer;


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
    public void initialize () {
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
    
    private void initLanguageMenu() {
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
    
    private void initFileMenu() {
        newCampaignMenuItem.setOnAction(e->{
            
        });
        newLevelMenuItem.setOnAction(e->{});
        saveMenuItem.setOnAction(e->{
            //TODO SAVE
        });
    }

    @Override
    public void update () {
        // nothing to update
    }

}
