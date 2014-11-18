package application;

import javafx.stage.Stage;
import gamemodel.MainModel;
import view.MainView;

public class Vooga {
    
    private MainView myMainView;
    private MainModel myMainModel;
    //TODO: IMPLEMENT GAME ENGINE HERE
    //private GameEngine myGameEngine;
    
    public Vooga(Stage stage) {
        myMainModel = new MainModel();
        myMainView = new MainView(stage, myMainModel);        
        
        myMainModel.addObserver(myMainView);
        //
    }
    
    /**
     * Public api for initializing a vooga salad.
     */
    public void toss() {
        initialize();
    }
    
    private void initialize() {
        myMainView.start();        
    }

}
