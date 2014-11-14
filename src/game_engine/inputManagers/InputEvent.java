package game_engine.inputManagers;

import java.util.function.Consumer;
import javafx.event.Event;

/**
 * A generic input event that specifies an action and an object to apply the action to
 * Essentially a wrapper for an event action pair
 * 
 * @author Zachary Bears
 *
 */
public class InputEvent <T>{
    
    private Event myEvent;
    private Consumer<T> myAction;
    
    
    public InputEvent(Event event, Consumer<T> action){
        myEvent = event;
        myAction = action;
    }
    public Event getEvent(){
        return myEvent;
    }
    public Consumer<T> getAction(){
        return myAction;
    }

}
