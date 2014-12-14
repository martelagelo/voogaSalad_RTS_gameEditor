package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.MainModel;
import view.MainView;
import view.dialog.DialogBoxUtility;

/**
 * 
 * This class is the starting point of our application.
 * It instantiates a model and a view, and adds the view
 * as an observer of the model.
 * 
 * @author Jonathan, Nishad
 *
 */

public class Main extends Application {

    @Override
    public void start (Stage primaryStage) {
        try {
            MainModel myMainModel = new MainModel();
            MainView myMainView = new MainView(primaryStage, myMainModel);
            myMainModel.addObserver(myMainView);
            myMainView.start();
        } catch (Exception e) {
            DialogBoxUtility.createMessageDialog(e.getMessage());
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
