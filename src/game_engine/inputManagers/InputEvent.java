package game_engine.inputManagers;

import java.util.function.Consumer;
import javafx.event.Event;
import javafx.event.EventType;


/**
 * A generic input event that specifies an action and an object to apply the action to
 * Essentially a wrapper for an event action pair
 * 
 * @author Zachary Bears
 *
 */

// TODO need to have input events take requests and have input events able to check conditions on
// the eventType. need another lambda
public class InputEvent<E extends Event, T> {

    private EventType<E> myEventType;
    private Consumer<T> myAction;
    private T myActionAcceptor;

    public InputEvent (EventType<E> event, T actionAcceptor, Consumer<T> action) {
        myEventType = event;
        myAction = action;
        myActionAcceptor = actionAcceptor;
    }

    public EventType<E> getEventType () {
        return myEventType;
    }

    public void executeAction () {
        myAction.accept(myActionAcceptor);
    }

}
