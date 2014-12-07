package engine.gameRepresentation.evaluatables.actions.enumerations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An enumeration for the types of actions available e.g. collision, internal, vision
 * 
 * @author Zach, Stanley
 *
 */
public enum ActionType {
    COLLISION,
    INTERNAL,
    VISION,
    BUTTON,
    FOCUSED,
    OBJECTIVE,
    SELECTION;
}
