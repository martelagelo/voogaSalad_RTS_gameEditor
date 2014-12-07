package util.exceptions;

/**
 * 
 * @author Rahul
 *
 */
public class JSONSaveException extends SaveLoadException {
    /**
     * Auto-generated default ID
     */
    private static final long serialVersionUID = 1L;
    private static final String myMessage = "Unable to save JSON file";

    public JSONSaveException (Exception exception) {
        super(myMessage, exception);

    }

    @Override
    public String getMessage () {
        return myMessage;
    }

}
