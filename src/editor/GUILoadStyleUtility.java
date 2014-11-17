package editor;

import java.awt.Dimension;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import view.GUIController;


/**
 * A class for the generation and styling of GUIPanes based on FXML files
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 * @author Joshua Miller
 * 
 */
public class GUILoadStyleUtility {

    public GUIController generateGUIPane (String filePath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(filePath));
        try {
            loader.load();
            GUIController controller = (GUIController) loader.getController();
            return controller;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Scene createStyledScene (Scene scene, Parent root, Dimension dim, String ... cssFiles) {
        scene = new Scene(root, dim.width, dim.height);
        try {
            for (String cssFile : cssFiles) {
                scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            }
        }
        catch (Exception e) {
            System.out.println("css file not found (or some other error)");
            e.printStackTrace();
        }
        return scene;
    }

}
