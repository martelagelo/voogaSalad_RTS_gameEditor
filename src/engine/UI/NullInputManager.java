package engine.UI;

import engine.visuals.SelectionBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Input Manager that has no functionality 
 * 
 * @author John Lorenz, Jonathan Tseng
 *
 */
public class NullInputManager implements InputManager {

    //TODO GET RID OF ALL THESE PRINT LINE STATEMENTS
    // CURRENTLY JUST HERE FOR ERROR CHECKING
    
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
        //System.out.println("Null Input Manager");
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
    public void screenButtonClicked (String imagePath, int index) {
        // do nothing
        //System.out.println("Null Input Manager");
    }

}
