package util.exceptions;

/**
 * 
 * @author Rahul
 *
 */
public class SaveLoadException extends Exception {


    /**
     * Auto-generated message.
     */
    private static final long serialVersionUID = 1L;

    public SaveLoadException (Exception exception) {
        this("Save load error", exception);
    }

    public SaveLoadException (String message, Exception exception, Object... args) {
        super(String.format(message, args), exception);
    }

    
    public SaveLoadException (String message) {
        this(message, new Exception());
    }
}