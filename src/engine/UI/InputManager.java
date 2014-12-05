package engine.UI;

import engine.visuals.SelectionBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


/**
 * Input Manager interface
 * 
 * @author John Lorenz, Jonathan Tseng
 *
 */
public interface InputManager {

    public abstract void primaryClickOccurred (MouseEvent e,
                                               double mapTranslateX,
                                               double mapTranslateY,
                                               SelectionBox b);

    public abstract void secondaryClickOccurred (MouseEvent e,
                                                 double mapTranslateX,
                                                 double mapTranslateY,
                                                 SelectionBox b);

    public abstract void primaryClickReleaseOccurred (MouseEvent e,
                                                      double mapTranslateX,
                                                      double mapTranslateY,
                                                      SelectionBox b);

    public abstract void secondaryClickReleaseOccurred (MouseEvent e,
                                                        double mapTranslateX,
                                                        double mapTranslateY,
                                                        SelectionBox b);

    public abstract void primaryDragOccurred (MouseEvent e, SelectionBox b);

    //Michael D. - I don't know if this will ever be used
    public abstract void secondaryDragOccurred (MouseEvent e, SelectionBox b);

    public abstract void keyPressed (KeyEvent e);

    public abstract void screenButtonClicked (String imagePath, int index);

}
