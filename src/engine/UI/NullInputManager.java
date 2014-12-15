package engine.UI;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.MainModel;
import engine.stateManaging.GameElementManager;
import engine.stateManaging.GameLoop;
import engine.users.Participant;
import engine.visuals.SelectionShape;

/**
 * Input Manager that has no functionality
 *
 * @author John Lorenz, Jonathan Tseng
 *
 */
public class NullInputManager extends InputManager {

    public NullInputManager (MainModel model, GameElementManager gameElementManager,
            GameLoop gameLoop, Participant user) {
        super(model, gameElementManager, gameLoop, user);
    }

    @Override
    public void primaryClickOccurred (MouseEvent e, double mapTranslateX, double mapTranslateY,
            SelectionShape b) {
        // do nothing
    }

    @Override
    public void secondaryClickOccurred (MouseEvent e, double mapTranslateX, double mapTranslateY,
            SelectionShape b) {
        // do nothing
    }

    @Override
    public void primaryClickReleaseOccurred (MouseEvent e, double mapTranslateX,
            double mapTranslateY, SelectionShape b) {
        // do nothing
    }

    @Override
    public void secondaryClickReleaseOccurred (MouseEvent e, double mapTranslateX,
            double mapTranslateY, SelectionShape b) {
        // do nothing
    }

    @Override
    public void keyPressed (KeyEvent e) {
        // do nothing
    }

    @Override
    public void primaryDragOccurred (MouseEvent e, SelectionShape b) {
        // do nothing
    }

    @Override
    public void secondaryDragOccurred (MouseEvent e, SelectionShape b) {
        // do nothing
    }

    @Override
    public void screenButtonClicked (int buttonID) {
        // do nothing
    }

}
