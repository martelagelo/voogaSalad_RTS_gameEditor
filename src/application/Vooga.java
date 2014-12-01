package application;

import java.io.IOException;
import org.json.JSONException;
import game_engine.Engine;
import gamemodel.MainModel;
import javafx.stage.Stage;
import view.MainView;


/**
 * Functions as a main method to instantiate the modules of the program and
 * set them up to communicate between each other.
 * 
 * @author Jonathan Tseng
 *
 */
public class Vooga {
    private MainView myMainView;
    private MainModel myMainModel;
    private Engine myGameEngine;

    public Vooga (Stage stage) {
        myMainModel = new MainModel();
        myMainView = new MainView(stage, myMainModel);
        try {
            myGameEngine = new Engine(myMainModel);
        }
        catch (ClassNotFoundException | JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //TODO: This still needs to be fixed to handle all of the editor changes appropriately.
        // its currently throwing lots of null pointer exceptions which the editor doesn't know about
//        myMainModel.addObserver(myGameEngine);
        myMainModel.addObserver(myMainView);
    }

    /**
     * Public api for initializing a vooga salad.
     */
    public void dress () {
        initialize();
    }

    /**
     * starts the program
     */
    private void initialize () {
        myMainView.start();
    }

}
