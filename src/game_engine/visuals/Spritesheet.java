package game_engine.visuals;

/**
 * A data wrapper object used to group the pertinent information for a spritesheet.
 * Internal data is intentionally made public as this is just a data wrapper to simplify the passing
 * of data
 * 
 * @author Zach
 *
 */
public class Spritesheet {
    public String imageTag;
    public Dimension frameDimensions;
    public int numCols;

    /**
     * Create the Spritesheet
     * 
     * @param imageTag the tag for the image that will be sent to the save/load utility to get the
     *        image
     * @param frameDimensions the dimensions of a frame of the spritesheet
     * @param numCols the number of columns across in the spritesheet
     */
    public Spritesheet (String imageTag, Dimension frameDimensions, int numCols) {
        this.imageTag = imageTag;
        this.frameDimensions = frameDimensions;
        this.numCols = numCols;
    }

}
