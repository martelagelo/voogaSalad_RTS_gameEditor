package view;

/**
 * Class for enumerated types of screens that can be opened
 * 
 * @author Jonathan Tseng, Rahul
 *
 */
public enum ViewScreen {

<<<<<<< HEAD
    SPLASH("/view/guipanes/SplashPage.fxml"), EDITOR("/editor/guipanes/EditorRoot.fxml"), RUNNER("");

=======
    SPLASH("/view/guipanes/SplashPage.fxml"),
    EDITOR("/editor/guipanes/EditorRoot.fxml"),
    RUNNER("/view/gamerunner/RunnerScreenView.fxml");
    
>>>>>>> model
    private String myFilePath;

    private ViewScreen (String filePath) {
        myFilePath = filePath;
    }

    String getFilePath () {
        return myFilePath;
    }

}
