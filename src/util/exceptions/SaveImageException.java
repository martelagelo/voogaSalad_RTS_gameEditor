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
    private static final String myMessage = "Unable to save image from %s to %s: ";

    public SaveImageException (Exception exception, Object... args) {
        super(String.format(myMessage, args), exception);
    }


}
