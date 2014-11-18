package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start (Stage primaryStage) {
        try {
            Vooga salad = new Vooga(primaryStage);
            salad.toss();
            // gotta have some freshly tossed salad
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}