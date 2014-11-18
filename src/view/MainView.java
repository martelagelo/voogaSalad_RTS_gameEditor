package view;

import gamemodel.MainModel;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.MultiLanguageUtility;

/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class MainView implements Observer {
    private static final Dimension SCENE_DIMENSIONS = new Dimension(1024, 768);
    private Stage myStage;
    private Scene myScene;
    private MainModel myMainModel;
    private GUIScene myCurrentController;

    private GUILoadStyleUtility myLoadStyleUtility;

    public MainView (Stage stage, MainModel model) {
        myStage = stage;
        myMainModel = model;
        myLoadStyleUtility = new GUILoadStyleUtility();
    }

    public void start () {
        launchScene(ViewScreen.SPLASH, "");
        myStage.show();
    }

    public void launchScene (ViewScreen screen, String game) {
        // MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        // util.initLanguages(System.getProperty("user.dir") +
        // "\\src\\resources\\languages");
        // util.setLanguage("Chinese");
        myMainModel.loadGame(game);
        initializeScreen(screen.getFilePath());
    }

    private void initializeScreen (String filePath) {
        myCurrentController = (GUIScene) myLoadStyleUtility.generateGUIPane(filePath);
        Scene styled = myLoadStyleUtility.createStyledScene(myScene,
                (Parent) myCurrentController.getRoot(), SCENE_DIMENSIONS,
                myCurrentController.getCSS());
        myStage.setScene(styled);
        myCurrentController.attachSceneHandler(this);
    }

    @Override
    public void update (Observable o, Object arg) {

    }

}
