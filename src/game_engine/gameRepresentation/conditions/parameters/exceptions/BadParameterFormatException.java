package game_engine.gameRepresentation.conditions.parameters.exceptions;

/**
 * Throw an exception if a poorly formed parameter is received.
 * 
 * @author John
 *
 */
public class BadParameterFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public BadParameterFormatException (String s) {
        super(s);
    }

}
