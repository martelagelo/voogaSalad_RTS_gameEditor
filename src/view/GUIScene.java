package view;

public abstract class GUIScene implements GUIController {

    private MainView myMainView;

    public void attachSceneHandler (MainView mainView) {
        myMainView = mainView;
    }
    
    protected void switchScene(ViewScreen screen, String game) {
        myMainView.launchScene(screen, game);
    }

}
