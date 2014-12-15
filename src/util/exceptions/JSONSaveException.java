//This entire file is part of my masterpiece.
//Rahul Harikrishnan
package util.exceptions;

/**
 * Exception class for failure to save JSON file.
 * 
 * @author Rahul
 *
 */
public class JSONSaveException extends SaveLoadException {
    /**
     * Auto-generated default ID
     */
    private static final long serialVersionUID = 1L;
    private static final String myMessage = "Unable to save JSON file: %s";

    public JSONSaveException (Exception exception, Object... args) {
        super(String.format(myMessage, args), exception);
    }
}