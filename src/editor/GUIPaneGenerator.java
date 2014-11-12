package editor;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * 
 * @author Joshua Miller
 *
 * A class for the generation of GUIPanes based on
 * FXML files
 */
public class GUIPaneGenerator {

    public void generateGUIPane(String filePath, Node node) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setRoot(node);
        fxmlLoader.setController(node);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            //TODO Exception handling
            throw new RuntimeException(exception);
        }
    }

}
