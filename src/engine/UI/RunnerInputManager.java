package engine.UI;

import javafx.geometry.Point2D;
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
public class RunnerInputManager implements InputManager {
    private MainModel myMainModel;
    private GameElementManager myElementManager;
    private GameLoop myGameLoop;
    private Participant myUser;

    public RunnerInputManager (MainModel model,
                               GameElementManager gameElementManager,
                               GameLoop gameLoop, Participant user) {
        myMainModel = model;
        myElementManager = gameElementManager;
        myGameLoop = gameLoop;
        myUser = user;
    }

    @Override
    public void primaryClickOccurred (MouseEvent e,
                                      double mapTranslateX,
                                      double mapTranslateY,
                                      SelectionBox b) {
        Point2D mapPoint2d =
                new Point2D(mapTranslateX + e.getSceneX(), mapTranslateY + e.getSceneY());
        b.primaryClick(e);
        myElementManager.selectSingleUnit(mapPoint2d, e.isShiftDown(), myUser);
    }

    @Override
    public void secondaryClickOccurred (MouseEvent e,
                                        double mapTranslateX,
                                        double mapTranslateY,
                                        SelectionBox b) {
    	
        Point2D mapPoint2d =
                new Point2D(mapTranslateX + e.getSceneX(), mapTranslateY + e.getSceneY());
        myElementManager.setSelectedUnitHeadings(mapPoint2d, e.isShiftDown(), myUser);
    }

    @Override
    public void primaryClickReleaseOccurred (MouseEvent e,
                                             double mapTranslateX,
                                             double mapTranslateY,
                                             SelectionBox b) {
        double[] points = b.clickReleased(e, mapTranslateX, mapTranslateY);
        if(points[0]!=points[2] && points[1]!=points[3])
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
    public void screenButtonClicked (String imagePath, int index) {
        // TODO: implement once buttons are created and call this from ScrollablePane
        // will manage abilities and menu button clicks
    }

}
