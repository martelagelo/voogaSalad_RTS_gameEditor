package game_engine.visuals.UI;

import game_engine.UI.ClickManager;
import game_engine.UI.KeyboardManager;
import java.util.Observable;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;


public class InputManager extends Observable {
    private Event e;
    private ClickManager myClickManager;
    private KeyboardManager myKeyboardManager;

    public void inputOccurred (Event e) {
        if (e instanceof MouseEvent) {

        }
        this.e = e;
        setChanged();
        notifyObservers();
    }

    public Event getEvent () {
        return e;
    }

}
