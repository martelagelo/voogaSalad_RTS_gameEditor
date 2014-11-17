package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainView;


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
