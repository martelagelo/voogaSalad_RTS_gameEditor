package view;

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
    
    protected void switchScreen(ViewScreen screen, String game) {
        myMainView.launchScreen(screen, game);
    }

}
