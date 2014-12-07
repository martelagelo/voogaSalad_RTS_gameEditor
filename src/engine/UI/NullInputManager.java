package engine.UI;

import model.MainModel;
import engine.stateManaging.GameElementManager;
import engine.stateManaging.GameLoop;
import engine.users.Participant;
import engine.visuals.SelectionBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Input Manager that has no functionality 
 * 
 * @author John Lorenz, Jonathan Tseng
 *
 */
public class NullInputManager extends InputManager {

    //TODO GET RID OF ALL THESE PRINT LINE STATEMENTS
    // CURRENTLY JUST HERE FOR ERROR CHECKING
    
    public NullInputManager (MainModel model,
                             GameElementManager gameElementManager,
                             GameLoop gameLoop,
                             Participant user) {
        super(model, gameElementManager, gameLoop, user);
    }

    @Override
    public void primaryClickOccurred (MouseEvent e,
                               double mapTranslateX,
                               double mapTranslateY,
                               SelectionBox b) {
        // do nothing
        //System.out.println("Null Input Manager");
    }
    
    @Override
    public void secondaryClickOccurred (MouseEvent e,
                               double mapTranslateX,
                               double mapTranslateY,
                               SelectionBox b) {
        // do nothing
        //System.out.println("Null Input Manager");
    }

    @Override
    public void primaryClickReleaseOccurred (MouseEvent e,
                                      double mapTranslateX,
                                      double mapTranslateY,
                                      SelectionBox b) {
        // do nothing
        //System.out.println("Null Input Manager");
    }
    
    @Override
    public void secondaryClickReleaseOccurred (MouseEvent e,
                                      double mapTranslateX,
                                      double mapTranslateY,
                                      SelectionBox b) {
        // do nothing
        //System.out.println("Null Input Manager");
    }

    @Override
    public void keyPressed (KeyEvent e) {
        // do nothing
        System.out.println("Null Input Manager");
    }

    @Override
    public void primaryDragOccurred (MouseEvent e, SelectionBox b) {
        // do nothing
        //System.out.println("Null Input Manager");
    }
    
    @Override
    public void secondaryDragOccurred (MouseEvent e, SelectionBox b) {
        // do nothing
        //System.out.println("Null Input Manager");
    }

    @Override
    public void screenButtonClicked (int buttonID) {
        // do nothing
        //System.out.println("Null Input Manager");
    }

}
