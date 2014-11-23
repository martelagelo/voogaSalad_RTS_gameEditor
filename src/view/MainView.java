package view;

import gamemodel.MainModel;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import util.multilanguage.MultiLanguageUtility;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * 
 * MainView that has control of the stage and handles switching between different scenes
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class MainView implements Observer {

    private static final String[] myLanguages = new String[] {
                                                              "resources.languages.English.properties",
                                                              "resources.languages.Chinese.properties"
    };

    private static final Dimension SCENE_DIMENSIONS = new Dimension(1024, 768);
    private Stage myStage;
    private Scene myScene;
    private MainModel myMainModel;
    private GUIScreen myCurrentController;
    private GUILoadStyleUtility myLoadStyleUtility;

    public MainView (Stage stage, MainModel model) {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        util.initLanguages(myLanguages);
        myStage = stage;
        myMainModel = model;
        launchScreen(ViewScreen.SPLASH);
        myLoadStyleUtility = GUILoadStyleUtility.getInstance();
        myLoadStyleUtility.setScene(myScene);
    }

    public void start () {
        myStage.show();
    }

    public void launchScreen (ViewScreen screen) {
        launchScreen(screen.getFilePath());
        
        // Shitty test code
//      try {
//          myMainModel.loadGame("Shitty Game");
//          myMainModel.createCampaign("Shitty campaign 1");
//          myMainModel.createCampaign("Shitty campaign 2");
//          myMainModel.createLevel("Shitty level 1", "Shitty campaign 1");
//          myMainModel.createLevel("Shitty level 2", "Shitty campaign 1");
//          myMainModel.createLevel("Shitty level 3", "Shitty campaign 1");
//      }
//      catch (Exception e) {
//          e.printStackTrace();
//      }
        
    }

    /**
     * private helper method to launch a screen for the stage
     * 
     * @param filePath
     */
    private void launchScreen (String filePath) {
        myCurrentController = (GUIScreen) GUILoadStyleUtility.generateGUIPane(filePath);
        myScene =
                new Scene((Parent) myCurrentController.getRoot(), SCENE_DIMENSIONS.getWidth(),
                          SCENE_DIMENSIONS.getHeight());
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
