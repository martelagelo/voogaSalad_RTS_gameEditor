package game_engine.gameRepresentation;

import java.util.List;


/**
 * This class is a collection of Levels used to string together gameplay in order to make a cohesive
 * user experience.
 * 
 * @author Steve
 *
 */
public class Campaign {

    public String name;
    public String description;
    private List<Level> levels;

    public List<Level> getLevels () {
        return levels;
    }

}
