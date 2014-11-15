package game_engine.animationEngine;
/**
 * An object capable of being updated and returning a status
 * @author zed
 *
 */
public interface Updatable {
    /**
     * Update the object
     * @return a boolean status for the update
     */
    public boolean update();
}
