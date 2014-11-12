package view;

import editor.GUIPaneGenerator;
import javafx.scene.layout.GridPane;


public class SplashScreen extends GridPane {

    private static final String FXML_FILE = "/view/guipanes/SplashPage.fxml";

    public SplashScreen (GUIPaneGenerator guiPaneGenerator) {
        guiPaneGenerator.generateGUIPane(FXML_FILE, this);    
    }

    public String[] getCSS() {
        return new String[] { "/view/stylesheets/splash.css" };
    }

}
