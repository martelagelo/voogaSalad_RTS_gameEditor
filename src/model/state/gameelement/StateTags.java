package model.state.gameelement;


/**
 * An enumeration to allow for the important tags of states.
 * 
 * @author Zach, Stanley
 *
 */
// TODO move this into a config file or equivalent, UPDATE COMMENTS
public enum StateTags {
    
    NAME(StateType.STRING, "name"),
    TYPE(StateType.STRING, "type"),
    TEAM_COLOR(StateType.STRING, "teamColor"),
    CURRENT_ACTION(StateType.STRING, "currentAction"), // TODO: attack, die, decay, gather??
    ATTRIBUTE_DESCRIPTION(StateType.STRING, "attributeDescription"),
    
    X_POSITION(StateType.NUMBER, "xPosition"),
    Y_POSITION(StateType.NUMBER, "yPosition"),
    X_VELOCITY(StateType.NUMBER, "xVelocity"),
    Y_VELOCITY(StateType.NUMBER, "yVelocity"),
    X_GOAL_POSITION(StateType.NUMBER, "xGoalPosition"),
    Y_GOAL_POSITION(StateType.NUMBER, "yGoalPosition"),
    X_TEMP_GOAL_POSITION(StateType.NUMBER, "xTempGoalPosition"),
    Y_TEMP_GOAL_POSITION(StateType.NUMBER, "yTempGoalPosition"),
    RANDOM_MOVEMENT_FLAG(StateType.NUMBER, "randomMovementFlag"),
    MOVEMENT_SPEED(StateType.NUMBER, "movementSpeed"),
    BLOCKING(StateType.NUMBER, "blocking"),
    IS_SELECTED(StateType.NUMBER, "isSelected"),
    ATTACK(StateType.NUMBER, "attack"),
    HEALTH(StateType.NUMBER, "health"),
    LEVEL_HEIGHT(StateType.NUMBER, "levelHeight"),
    LEVEL_WIDTH(StateType.NUMBER, "levelWidth"),
    RELOAD_TIME(StateType.NUMBER, "reloadTime"),
    IS_DEAD(StateType.NUMBER, "isDead"),
    X_SPAWN_OFFSET(StateType.NUMBER, "xSpawnOffset"),
    Y_SPAWN_OFFSET(StateType.NUMBER, "ySpawnOffset"),
    SUPPORTS_RANGED_ATTACK(StateType.NUMBER, "supportsRangedAttack"),
    LAST_BUTTON_CLICKED_ID(StateType.NUMBER, "lastButtonID"),
    RESOURCES(StateType.NUMBER, "resources");
    
    private StateType myType;
    private String myValue;
    
    private StateTags (StateType type, String value) {
        myType = type;
        myValue = value;
    }
    
    public StateType getType() {
        return myType;
    }
    
    public String getValue(){
        return myValue;
    }

    public enum StateType {
        STRING, NUMBER
    }
    
    

    
    


}

