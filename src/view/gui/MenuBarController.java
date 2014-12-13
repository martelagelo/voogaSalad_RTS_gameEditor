package view.gui;

import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


public abstract class MenuBarController extends GUIContainer {

    private final static String FILE_KEY = "File";
    private final static String SAVE_KEY = "Save";
    private final static String QUIT_KEY = "Quit";
    private final static String LANGUAGE_KEY = "Languages";

    @FXML
    protected MenuItem saveMenuItem;
    @FXML
    protected MenuItem quitMenuItem;
    @FXML
    protected Menu fileMenu;
    @FXML
    protected MenuBar menuBar;

    @FXML
    protected Menu languageMenu;

    protected GUIScreen myScreen;

    public void attachScreen (GUIScreen screen) {
        myScreen = screen;
    }

    @Override
    public Node getRoot () {
        return menuBar;
    }

    @Override
    protected final void init () {
        attachTextProperties();
        initLanguageMenu();
        initMenuBar();
    }

    protected abstract void initMenuBar ();

    private final void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            fileMenu.textProperty().bind(util.getStringProperty(FILE_KEY));
            saveMenuItem.textProperty().bind(util.getStringProperty(SAVE_KEY));
            quitMenuItem.textProperty().bind(util.getStringProperty(QUIT_KEY));
            languageMenu.textProperty().bind(util.getStringProperty(LANGUAGE_KEY));
            bindTextProperties();
        }
        catch (LanguageException e) {
            DialogBoxUtility.createMessageDialog(e.getMessage());
        }
    }

    protected abstract void bindTextProperties () throws LanguageException;

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

    @Override
    public void modelUpdate () {
        // nothing to update
    }

}
