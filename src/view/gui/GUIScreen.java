package view.gui;

import view.MainView;

/**
 * 
 * @author Nishad Agrawal, Jonathan Tseng
 *
 */
public abstract class GUIScreen extends GUIContainer {

    private MainView myMainView;

    public void attachSceneHandler (MainView mainView) {
        myMainView = mainView;
    }
    
    public void switchScreen(ViewScreenPath screen) {
        myMainView.launchScreen(screen);
    }

}
