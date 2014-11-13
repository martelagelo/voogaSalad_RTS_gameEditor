package test.editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class lets you test what a single individual view loaded from fxml looks like as the root of
 * a scene in a stage as opposed to having it be part of the entire editor launcher
 * 
 * @author Nishad Agrawal
 *
 */
public class GUIPaneTester extends Application {

    @Override
    public void start (Stage stage) {
        try {
            Parent parent =
                    FXMLLoader.load(getClass().getResource("/editor/guipanes/GameInfo.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
