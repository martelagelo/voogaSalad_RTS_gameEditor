package application;

import view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start (Stage primaryStage) {
        try {
            MainView mainView = new MainView(primaryStage);
            mainView.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
