package util.exceptions;

/**
 * 
 * @author Rahul
 *
 */
public class SaveImageException extends SaveLoadException {
    /**
     * Auto-generated default serialID
     */
    private static final long serialVersionUID = 1L;
    private static final String myMessage = "Unable to save image";

    public SaveImageException (Exception exception) {
        super(myMessage, exception);
    }

    @Override
    public String getMessage () {
        return myMessage;
    }

}
