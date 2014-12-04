package model.state.gameelement;

/**
 * A temporary file to allow for the important tags of states to be moved
 * outside of individual classes in the state hierarchy. Should eventually be
 * moved to a config file or interface of sorts.
 * 
 * @author Zach
 *
 */
// TODO move this into a config file or equivalent
public class StateTags {
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String X_POSITION = "xPosition";
    public static final String Y_POSITION = "yPosition";
    public static final String X_VELOCITY = "xVelocity";
    public static final String Y_VELOCITY = "yVelocity";
    public static final String RANDOM_MOVEMENT_STRING = "randomMove";
    public static final String TEAM_ID = "teamID";
    public static final String MOVEMENT_SPEED = "movementSpeed";
    public static final String BLOCKING = "blocking";
    public static final String TEAM_COLOR = "teamColor";
    public static final String IS_SELECTED = "isSelected";
    public static final String CURRENT_ACTION = "currentAction"; // attack, die, decay, gather??
    public static final String LEVEL_HEIGHT = "LevelHeight";
    public static final String LEVEL_WIDTH = "LevelWidth";

}
