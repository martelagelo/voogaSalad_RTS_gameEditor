package game_engine.gameRepresentation;

/**
 * This interface allows for the Editor and GameRunner GUIs to display information describing
 * high-level game structure.
 * 
 * @author Steve
 *
 */
public interface Describable {

    public String getName ();

    public String getDescription ();
}
