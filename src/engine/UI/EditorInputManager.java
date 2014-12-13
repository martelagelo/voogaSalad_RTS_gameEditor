package engine.UI;

import java.util.function.Consumer;
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
 * Input Manager when the editor is toggled as opposed to the game runner
 * does not use the selection box... only 1 element can be selected at a time
 * 
 * @author Jonathan Tseng, John L.
 *
 */
public class EditorInputManager extends InputManager {

    public EditorInputManager (MainModel model,
                               GameElementManager gameElementManager,
                               GameLoop gameLoop, Participant user) {
        super(model, gameElementManager, gameLoop, user);
        gameLoop.setEditorLoop();
    }

    @Override
    public void primaryClickOccurred (MouseEvent e,
                                      double mapTranslateX,
                                      double mapTranslateY,
                                      SelectionBox b) {
        Point2D mapPoint2d =
                new Point2D(mapTranslateX + e.getX(), mapTranslateY + e.getY());
        String drawable = myMainModel.getEditorChosenDrawable();
        String selectable = myMainModel.getEditorChosenSelectable();
        boolean success = checkElementAndCreate(selectable, (element) ->
        myElementManager.addSelectableGameElementToLevel(element,
                                                         mapPoint2d.getX(),
                                                         mapPoint2d.getY(),
                                                         myMainModel.getEditorChosenColor()));
        success = success | checkElementAndCreate(drawable, (element) ->
        myElementManager.addDrawableGameElementToLevel(element, mapPoint2d.getX(),
                                                       mapPoint2d.getY())
        );
        myMainModel.clearEditorChosen();
        if (!success) {
            myElementManager.selectSingleUnit(mapPoint2d, e.isShiftDown(), myUser);
            myElementManager.selectAnySingleUnit(mapPoint2d, myUser);
        }
    }

    private boolean checkElementAndCreate(String element, Consumer<String> consumer) {
        boolean canAccept = !(element == null || element.isEmpty());
        if (canAccept) {
            consumer.accept(element);
        }
        return canAccept;
    }
    
    @Override
    public void secondaryClickOccurred (MouseEvent e,
                                        double mapTranslateX,
                                        double mapTranslateY,
                                        SelectionBox b) {
        Point2D mapPoint2d =
                new Point2D(mapTranslateX + e.getX(), mapTranslateY + e.getY());
        myElementManager.moveSelectedUnit(mapPoint2d, myUser);
    }

    @Override
    public void primaryClickReleaseOccurred (MouseEvent e,
                                             double mapTranslateX,
                                             double mapTranslateY,
                                             SelectionBox b) {
        // do nothing
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
        if (e.getCode() == KeyCode.BACK_SPACE) {
            myElementManager.deleteSelectedUnit();
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
