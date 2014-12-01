package view.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * Data Structure for storing a button label and an event to be called when that button is clicked
 * 
 * @author Jonathan Tseng
 *
 */
public class DialogChoice {

    public String myLabel;
    public EventHandler<ActionEvent> myOnClick;

    public DialogChoice (String label, EventHandler<ActionEvent> onClick) {
        myLabel = label;
        myOnClick = onClick;
    }

}
