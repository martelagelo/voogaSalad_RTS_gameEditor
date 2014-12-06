package view.runner;

import util.multilanguage.LanguageException;
import view.gui.MenuBarController;
import view.gui.ViewScreenPath;


/**
 * MenuBar for the runner view
 * 
 * @author Jonathan Tseng
 *
 */
public class RunnerMenuBarController extends MenuBarController {

    @Override
    protected void initMenuBar () {
        initFileMenu();
    }

    private void initFileMenu () {
        saveMenuItem.setOnAction(e -> myMainModel.saveGame());
        quitMenuItem.setOnAction(e -> myScreen.switchScreen(ViewScreenPath.SPLASH));
    }

    @Override
    protected void bindTextProperties () throws LanguageException {
        // nothing new to bind
    }

}
