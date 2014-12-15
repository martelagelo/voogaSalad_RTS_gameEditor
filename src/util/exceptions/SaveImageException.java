//This entire file is part of my masterpiece.
//Rahul Harikrishnan
package util.exceptions;

/**
 * Exception class for failure to save an image.
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
