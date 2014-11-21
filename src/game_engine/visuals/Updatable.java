package game_engine.visuals;

/**
 * An object capable of being updated and returning a status
 * 
 * @author Zach
 *
 */
public interface Updatable {
    /**
     * Update the object
     * 
     * @return a boolean status for the update
     */
    public boolean update ();
}
