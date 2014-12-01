package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.MainModel;
import view.MainView;


public class Main extends Application {

    @Override
    public void start (Stage primaryStage) {
        try {
            MainModel myMainModel = new MainModel();
            MainView myMainView = new MainView(primaryStage, myMainModel);
            myMainModel.addObserver(myMainView);
            myMainView.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
