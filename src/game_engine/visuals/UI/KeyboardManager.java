package game_engine.visuals.UI;

import java.util.Observable;
import javafx.scene.input.KeyEvent;

/**
 * 
 * The class for managing the key presses the user makes. Takes in key press
 * information and allows observers to get that information.
 * 
 * @author John
 *
 */
public class KeyboardManager extends Observable {

    private String myCharacter;
    
    public KeyboardManager () {
    }
    
    /**
     * Method to call by the containing class when it senses a key has been pressed.
     * Takes in the event and sets the character that was selected so that observers can
     * retrieve that character
     * @param event the keyEvent that was detected
     */
    public void keyPressed (KeyEvent event) {
        myCharacter = event.getCharacter();
        setChanged();
        notifyObservers();
    }
    
    /**
     * gets the last key that was pressed that this KeyboardManager
     * detected
     * @return the String containing the character
     */
    public String getLastCharacter(){
        return this.myCharacter;
    }

}
