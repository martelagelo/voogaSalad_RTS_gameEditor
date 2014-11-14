package view;

import java.awt.Dimension;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class MainView {

    private static final Dimension SCENE_DIMENSIONS = new Dimension(1024, 768);
    private static final String SPLASH_PAGE_PATH = "/view/guipanes/SplashPage.fxml";
    private static final String EDITOR_ROOT_PATH = "/editor/guipanes/EditorRoot.fxml";
    private Stage myStage;
    private Scene myScene;

    private GUILoadStyleUtility myLoadStyleUtility;

    public MainView (Stage stage) {
        myStage = stage;
        myLoadStyleUtility = new GUILoadStyleUtility();
    }

    public void start () {
        // TODO CLEAN DIS SHIT WITH REQUESTS
        // displaySplashScreen();
        displayEditorScreen();
        myStage.show();
    }

    private void displaySplashScreen () {
        initializeScreen(SPLASH_PAGE_PATH);
    }

    private void displayEditorScreen () {
        initializeScreen(EDITOR_ROOT_PATH);
    }

    private void initializeScreen (String filePath) {
        GUIController controller = myLoadStyleUtility.generateGUIPane(filePath);
        Scene styled =
                myLoadStyleUtility.createStyledScene(myScene,
                                                     (Parent) controller.getRoot(),
                                                     SCENE_DIMENSIONS,
                                                     controller.getCSS());
        myStage.setScene(styled);
    }

}
