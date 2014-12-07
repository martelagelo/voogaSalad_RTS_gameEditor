package util.exceptions;

/**
 * 
 * @author Rahul
 *
 */
public class SaveLoadException extends Exception {
    private static String myMessage;
    private Exception myException;

    /**
     * Auto-generated message.
     */
    private static final long serialVersionUID = 1L;

    public SaveLoadException (Exception exception) {
        this("", exception);
    }

    public SaveLoadException (Exception exception, Object... args) {
        super(String.format(myMessage, args), exception);
    }

    
    public SaveLoadException (String message) {
        this(message, new Exception());
    }

    public SaveLoadException (String message, Exception exception) {
        super(message, exception);
        myMessage = message;
        myException = exception;
    }

    @Override
    public String getMessage () {
        return myMessage;
    }

    @Override
    public Throwable getCause () {
        return myException;
    }

}