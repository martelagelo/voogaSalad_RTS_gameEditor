package util.exceptions;

/**
 * 
 * @author Rahul
 *
 */
public class LoadImageException extends SaveLoadException {
    /**
     * Auto-generated default serialID
     */
    private static final long serialVersionUID = 1L;
    private static final String myMessage = "Unable to load image";

    public LoadImageException (Exception exception) {
        super(myMessage, exception);
    }

    public LoadImageException () {
        super(myMessage);
    }

    @Override
    public String getMessage () {
        return myMessage;
    }

}
