package util.exceptions;

/**
 * 
 * @author Rahul
 *
 */
public class SaveFileException extends SaveLoadException {
    /**
     * Auto-generated default serialID
     */
    private static final long serialVersionUID = 1L;
    private static final String myMessage = "Unable to save file at location";

    public SaveFileException (Exception exception) {
        super(myMessage, exception);
    }

    public SaveFileException () {
        super(myMessage);
    }

    @Override
    public String getMessage () {
        return myMessage;
    }

}
