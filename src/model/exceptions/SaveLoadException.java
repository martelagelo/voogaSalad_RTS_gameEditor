package model.exceptions;

/**
 * 
 * @author Rahul
 *
 */
public class SaveLoadException extends Exception {
    private String myMessage;
    private Exception myException;

    /**
     * Auto-generated message.
     */
    private static final long serialVersionUID = 1L;

    public SaveLoadException (Exception exception) {
        this("", exception);
    }

    public SaveLoadException (String message, Exception exception) {
        super(message, exception);
        myMessage = message;
    }

    @Override
    public String getMessage () {
        return myMessage;
    }
}