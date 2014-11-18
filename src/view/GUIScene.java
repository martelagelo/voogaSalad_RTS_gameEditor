package view;

public abstract class GUIScene implements GUIController {

    private MainView myMainView;

    public void attachSceneHandler (MainView mainView) {
        myMainView = mainView;
    }
    
    protected launchScene(GUIScene scene) {
        
    }

}
