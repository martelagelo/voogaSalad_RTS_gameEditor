package engine.UI;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.MainModel;
import engine.stateManaging.GameElementManager;
import engine.users.EditorParticipant;
import engine.users.Participant;
import engine.visuals.SelectionBox;


/**
 * Input Manager when the editor is toggled as opposed to the game runner
 * does not use the selection box... only 1 element can be selected at a time
 * 
 * @author Jonathan Tseng, John L.
 *
 */
public class EditorInputManager implements InputManager {

    private MainModel myMainModel;
    // TODO not sure if supposed to talk to elementmanager: yeah, you probably need to -John L
    private GameElementManager myElementManager;
    private Participant myUser;

    public EditorInputManager (MainModel mainModel, GameElementManager gameElementManager) {
        myMainModel = mainModel;
        myElementManager = gameElementManager;
        myUser = new EditorParticipant();
    }

    @Override
    public void primaryClickOccurred (MouseEvent e,
                                      double mapTranslateX,
                                      double mapTranslateY,
                                      SelectionBox b) {
        Point2D mapPoint2d =
                new Point2D(mapTranslateX + e.getX(), mapTranslateY + e.getY());
        myElementManager.selectSingleUnit(mapPoint2d, e.isShiftDown(), myUser);

        // I believe this selects units
        // I'll probably have to refactor this to be done somewhere else
        if (e.getButton() == MouseButton.PRIMARY) {
            // TODO try to add elements to the thing based on thing selected in editor
            // if terrain then switch out terrain
            // if unit then add unit to graph
            // if nothing then try to select units
        }

        myElementManager.setSelectedUnitCommand(mapPoint2d, e.isShiftDown(), myUser);
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
        // Probably also do nothing since no selection box
    }

    @Override
    public void secondaryClickReleaseOccurred (MouseEvent e,
                                               double mapTranslateX,
                                               double mapTranslateY,
                                               SelectionBox b) {
        // Probably also do nothing since no selection box
    }

    @Override
    public void keyPressed (KeyEvent e) {
        // TODO: key presses
        if (e.getCode() == KeyCode.BACK_SPACE) {
            // TODO Delete selected elements from level
        }
    }

    @Override
    public void primaryDragOccurred (MouseEvent e, SelectionBox b) {
        // do nothing b/c selection box not used
    }

    @Override
    public void secondaryDragOccurred (MouseEvent e, SelectionBox b) {
        // do nothing b/c selection box not used
    }

    @Override
    public void screenButtonClicked (int buttonID) {
        // TODO decide if we want this to do anything in the editor... maybe not
    }

}
