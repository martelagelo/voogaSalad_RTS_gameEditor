package game_engine.UI;

import java.util.Observable;
import java.util.Observer;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

public class InputManager extends Observable {
    private Event e;
    private ClickManager myClickManager;
    private KeyboardManager myKeyboardManager;
    private MouseDragManager myMouseDragManager;
    
    public InputManager(){
        myClickManager = new ClickManager();
        myMouseDragManager = new MouseDragManager();
        myKeyboardManager = new KeyboardManager();
    }
    
    public void inputOccurred(Event e){
        this.e = e;
        setChanged();
        notifyObservers();
    }
    
    public void inputOccurred(MouseEvent e, double mapTranslationX, double mapTranslationY, boolean drag){
        if(drag){
            myMouseDragManager.clicked(e, mapTranslationX+e.getSceneX(), mapTranslationY+e.getSceneY());
        }
        else{
            myClickManager.clicked(e, mapTranslationX+e.getSceneX(), mapTranslationY+e.getSceneY());
        }
    }
    
    public Event getEvent(){
        return e;
    }

    /**
     * adds an observer to the clicks on the scene
     * 
     * @param o the object that wants to be notified about the location
     *        on the map that was clicked and which button was used
     */
    public void addClickObserver (Observer o) {
        myClickManager.addObserver(o);
    }
    
    /**
     * adds an observer to the keys pressed while focused on the scene
     * 
     * @param o the object that wants to be notified about the key
     *        that was pressed
     */
    public void addKeyboardObserver (Observer o) {
        myKeyboardManager.addObserver(o);
    }
    
    public void addMouseDragObserver (Observer o) {
        myMouseDragManager.addObserver(o);
    }
    
}
