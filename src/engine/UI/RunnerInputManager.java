package engine.UI;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.MainModel;
import engine.stateManaging.GameElementManager;
import engine.stateManaging.GameLoop;
import engine.users.Participant;
import engine.visuals.SelectionBox;


/**
 * 
 * Input Manager for the game runner
 * 
 * @author John Lorenz, Jonathan Tseng
 *
 */
public class RunnerInputManager extends InputManager {

    public RunnerInputManager (MainModel model,
                               GameElementManager gameElementManager,
                               GameLoop gameLoop, Participant user) {
        super(model, gameElementManager, gameLoop, user);
        gameLoop.setRunnerLoop();
    }

    @Override
    public void primaryClickOccurred (MouseEvent e,
                                      double mapTranslateX,
                                      double mapTranslateY,
                                      SelectionBox b) {
        Point2D mapPoint2d =
                new Point2D(mapTranslateX + e.getX(), mapTranslateY + e.getY());
        b.primaryClick(e);
        myElementManager.selectSingleUnit(mapPoint2d, e.isShiftDown(), myUser);
    }

    @Override
    public void secondaryClickOccurred (MouseEvent e,
                                        double mapTranslateX,
                                        double mapTranslateY,
                                        SelectionBox b) {

        Point2D mapPoint2d =
                new Point2D(mapTranslateX + e.getX(), mapTranslateY + e.getY());
        myElementManager.setSelectedUnitCommand(mapPoint2d, e.isShiftDown(), myUser);
    }

    @Override
    public void primaryClickReleaseOccurred (MouseEvent e,
                                             double mapTranslateX,
                                             double mapTranslateY,
                                             SelectionBox b) {
        double[] points = b.clickReleased(e, mapTranslateX, mapTranslateY);
        if (points[0] != points[2] && points[1] != points[3])
            myElementManager.selectUnitsInBounds(points, e.isShiftDown(), myUser);
    }

    @Override
    public void secondaryClickReleaseOccurred (MouseEvent e,
                                               double mapTranslateX,
                                               double mapTranslateY,
                                               SelectionBox b) {
        // do nothing
    }

    @Override
    public void keyPressed (KeyEvent e) {
        // TODO: implement and pass key presses to GameElementManager
        KeyCode key = e.getCode();
        if (key.equals(KeyCode.P)) {
            myGameLoop.pause();
        }
    }

    @Override
    public void primaryDragOccurred (MouseEvent e, SelectionBox b) {
        b.reactToDrag(e);
    }

    @Override
    public void secondaryDragOccurred (MouseEvent e, SelectionBox b) {
        // do nothing
    }

    @Override
    public void screenButtonClicked (int buttonID) {
        myElementManager.notifyButtonClicked(buttonID, myUser);
    }

}
