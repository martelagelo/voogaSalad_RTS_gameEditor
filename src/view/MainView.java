package view;

import gamemodel.MainModel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.multilanguage.MultiLanguageUtility;


/**
 * 
 * MainView that has control of the stage and handles switching between different scenes
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class MainView implements Observer {

    private static final String[] myLanguages =
            new String[] {
                          "resources.languages.English.properties",
                          "resources.languages.Chinese.properties"
            };

    private static final Dimension SCENE_DIMENSIONS = Toolkit.getDefaultToolkit().getScreenSize();
    private Stage myStage;
    private Scene myScene;
    private MainModel myMainModel;
    private GUIScreen myCurrentController;
    private GUILoadStyleUtility myLoadStyleUtility;

    public MainView (Stage stage, MainModel model) {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        util.initLanguages(myLanguages);
        myStage = stage;
        myScene = new Scene(new Pane(), SCENE_DIMENSIONS.getWidth(),
                SCENE_DIMENSIONS.getHeight());
        myMainModel = model;
        launchScreen(ViewScreen.SPLASH);
        myLoadStyleUtility = GUILoadStyleUtility.getInstance();
        myLoadStyleUtility.setScene(myScene);
        myLoadStyleUtility.addStyle("./stylesheets/JMetroDarkTheme.css");
        myLoadStyleUtility.addStyle("./stylesheets/main.css");
    }

    public void start () {
        myStage.show();
    }

    public void launchScreen (ViewScreen screen) {
        launchScreen(screen.getFilePath());
    }

    /**
     * private helper method to launch a screen for the stage
     * 
     * @param filePath
     */
    private void launchScreen (String filePath) {
        myCurrentController = (GUIScreen) GUILoadStyleUtility.generateGUIPane(filePath);
        myScene.setRoot((Parent) myCurrentController.getRoot());
        myStage.setScene(myScene);
        myCurrentController.attachSceneHandler(this);
        myCurrentController.setModel(myMainModel);
        myCurrentController.update();
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        myCurrentController.update();
    }

}
