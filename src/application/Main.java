package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.dialog.DialogBoxController;
import view.dialog.DialogBoxUtility;
import view.dialog.DialogChoice;


public class Main extends Application {

    @Override
    public void start (Stage primaryStage) {
        try {
            Vooga salad = new Vooga(primaryStage);
            salad.toss();
            // gotta have some freshly tossed salad
            DialogBoxUtility.createDialogBox("message", new DialogChoice("OK", e -> System.out
                                                                         .println("clicked")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
