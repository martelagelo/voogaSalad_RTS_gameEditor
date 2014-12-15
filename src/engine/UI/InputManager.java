package engine.UI;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.MainModel;
import engine.stateManaging.GameElementManager;
import engine.stateManaging.GameLoop;
import engine.users.Participant;
import engine.visuals.SelectionShape;

/**
 * Input Manager interface
 *
 * @author John Lorenz, Jonathan Tseng
 *
 */
public abstract class InputManager {

    protected MainModel myMainModel;
    protected GameElementManager myElementManager;
    protected GameLoop myGameLoop;
    protected Participant myUser;

    public InputManager (MainModel model, GameElementManager gameElementManager, GameLoop gameLoop,
            Participant user) {
        myMainModel = model;
        myElementManager = gameElementManager;
        myGameLoop = gameLoop;
        myUser = user;
    }

    public abstract void primaryClickOccurred (MouseEvent e, double mapTranslateX,
            double mapTranslateY, SelectionShape b);

    public abstract void secondaryClickOccurred (MouseEvent e, double mapTranslateX,
            double mapTranslateY, SelectionShape b);

    public abstract void primaryClickReleaseOccurred (MouseEvent e, double mapTranslateX,
            double mapTranslateY, SelectionShape b);

    public abstract void secondaryClickReleaseOccurred (MouseEvent e, double mapTranslateX,
            double mapTranslateY, SelectionShape b);

    public abstract void primaryDragOccurred (MouseEvent e, SelectionShape b);

    public abstract void secondaryDragOccurred (MouseEvent e, SelectionShape b);

    public abstract void keyPressed (KeyEvent e);

    public abstract void screenButtonClicked (int buttonID);

    public void mouseMoved (MouseEvent e) {
        // TODO Auto-generated method stub
        // use this to set the scrollablepane's scrolling speed
    }

}
