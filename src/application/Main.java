package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.dialog.DialogBoxUtility;
import view.dialog.DialogChoice;




public class Main extends Application {

    @Override
    public void start (Stage primaryStage) {
        try {
            Vooga salad = new Vooga(primaryStage);
            // gotta have some freshly dressed salad
            salad.dress();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
