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
    private static final String SPLASH_PAGE_PATH = "/view/guipanes/SplashPage.fxml";
    private static final String EDITOR_ROOT_PATH = "/editor/guipanes/EditorRoot.fxml";
    private Stage myStage;
    private Scene myScene;
    private MainModel myMainModel;
    private GUIController myCurrentController;

    private GUILoadStyleUtility myLoadStyleUtility;

    public MainView (Stage stage, MainModel model) {
        myStage = stage;
        myMainModel = model;
        myLoadStyleUtility = new GUILoadStyleUtility();
    }

    public void start () {
         displaySplashScreen();
//        displayEditorScreen();
        myStage.show();
    }
    
    public void launchScene() {
        
    }

    private void displaySplashScreen () {
        initializeScreen(SPLASH_PAGE_PATH);
    }

    private void displayEditorScreen () {
        initializeScreen(EDITOR_ROOT_PATH);
//        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
//        util.initLanguages(System.getProperty("user.dir") + "\\src\\resources\\languages");
//        util.setLanguage("Chinese");
    }

    private void initializeScreen (String filePath) {
        myCurrentController = myLoadStyleUtility.generateGUIPane(filePath);
        Scene styled =
                myLoadStyleUtility.createStyledScene(myScene,
                                                     (Parent) myCurrentController.getRoot(),
                                                     SCENE_DIMENSIONS,
                                                     myCurrentController.getCSS());
        myStage.setScene(styled);
    }

    @Override
    public void update (Observable o, Object arg) {
        
    }

}
