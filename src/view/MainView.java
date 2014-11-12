package view;

import java.awt.Dimension;
import editor.EditorScreen;
import editor.GUIPaneGenerator;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView {

    private static final Dimension SCENE_DIMENSIONS = new Dimension(1024, 768);
    private static final String FILE_PATH = "/game_editor/GUIPanes/SplashPage.fxml";
    private Stage myStage;
    private Scene myScene;
    private EditorScreen myEditorScreen;

    private GUIPaneGenerator myGuiPaneGenerator;

    public MainView (Stage stage) {
        myStage = stage;
        myGuiPaneGenerator = new GUIPaneGenerator();
    }

    public void start() {
        //TODO CLEAN DIS SHIT
        //displaySplashScreen();
        displayEditorScreen();
        myStage.show();
    }

    private void displaySplashScreen() {
        SplashScreen splashScreen = new SplashScreen(myGuiPaneGenerator);
        setScene(splashScreen, splashScreen.getCSS());
    }

    private void displayEditorScreen() {
        if (myEditorScreen == null) {
            myEditorScreen = new EditorScreen(myGuiPaneGenerator);
        }
        setScene(myEditorScreen);
    }

    private void setScene(Parent root, String ... cssFiles) {
        myScene = new Scene(root, SCENE_DIMENSIONS.width, SCENE_DIMENSIONS.height);
        try {
            for (String cssFile : cssFiles) {
                myScene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            }   
        } catch (Exception e) {
            System.out.println("css file not found (or some other error)");
            e.printStackTrace();
        }
        myStage.setScene(myScene);
    }

}
