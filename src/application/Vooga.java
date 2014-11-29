package application;

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
        myGameEngine = new Engine(myMainModel);
        // TODO: uncomment
        // myMainModel.addObserver(myGameEngine);
        myMainModel.addObserver(myMainView);
    }

    /**
     * Public api for initializing a vooga salad.
     */
    public void toss () {
        initialize();
    }

    /**
     * starts the program
     */
    private void initialize () {
        myMainView.start();
    }

}
