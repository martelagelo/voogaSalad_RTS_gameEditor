package view.gui;

/**
 * Class for enumerated types of screens that can be opened
 * 
 * @author Jonathan Tseng, Nishad, Rahul
 *
 */
public enum ViewScreenPath {
    SPLASH("/view/splash/SplashPage.fxml"),
    EDITOR("/view/editor/guipanes/EditorRoot.fxml"),
    RUNNER("/view/runner/RunnerScreenView.fxml");
    
    private String myFilePath;

    private ViewScreenPath (String filePath) {
        myFilePath = filePath;
    }

    public String getFilePath () {
        return myFilePath;
    }

}
