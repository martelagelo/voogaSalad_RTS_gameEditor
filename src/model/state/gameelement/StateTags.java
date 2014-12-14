package model.state.gameelement;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * An enumeration to allow for the important tags of states.
 * 
 * @author Zach, Stanley
 *
 */
public enum StateTags {

    BACKGROUND_PATH(StateType.STRING, "backgroundPath"),
    NAME(StateType.STRING, "name"),
    TYPE(StateType.STRING, "type"),
    CURRENT_ACTION(StateType.STRING, "currentAction"), // TODO: attack, die, decay, gather??
    ATTRIBUTE_DESCRIPTION(StateType.STRING, "attributeDescription"),

    TEAM_COLOR(StateType.NUMBER, "teamColor"),
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

    public StateType getType () {
        return myType;
    }

    public String getValue () {
        return myValue;
    }

    public enum StateType {
        STRING, NUMBER
    }

    private static Set<StateTags> getAttributes () {
        return Arrays.asList(StateTags.values())
                .stream().collect(Collectors.toSet());
    }

    public static Set<String> getAllAttributes () {
        return getAttributes().stream().map(tag -> tag.getValue()).collect(Collectors.toSet());
    }

    private static Set<String> filterByType (StateType type) {
        return getAttributes().stream().filter(tag -> tag.getType().equals(type))
                .map(tag -> tag.getValue()).collect(Collectors.toSet());
    }

    public static Set<String> getAllNumericalAttributes () {
        return filterByType(StateType.NUMBER);
    }

    public static Set<String> getAllTextualAttributes () {
        return filterByType(StateType.STRING);
    }

}
