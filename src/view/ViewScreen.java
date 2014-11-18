package view;

import javafx.stage.Stage;

public enum ViewScreen {
    
    SPLASH("/view/guipanes/SplashPage.fxml"),
    EDITOR("/editor/guipanes/EditorRoot.fxml"),
    RUNNER("");
    
    
    private String myFilePath;
    private ViewScreen(String filePath) {
        myFilePath = filePath;
    }
    
    String getFilePath() {
        return myFilePath;
    }

}
