package engine.actions.enumerations;

import java.util.Arrays;
import java.util.List;

/**
 * An enumeration listing all the possibilities for action creation
 * 
 * @author Zach
 */
public enum ActionParameters {
    // Element element evaluators
    EE_EVAL(
            "Attack",
            "Collision",
            "Equals",
            "HeadingUpdate",
            "MoveBack",
            "MovePlayer",
            "RandomWaypoint",
            "UpdateMovementDirection"),
    // Number number evaluators
    NN_EVAL(
            "AdditionAssignment",
            "EqualsAssignment",
            "Equals",
            "GreaterThan",
            "GreaterThanEqual",
            "LessThan",
            "LessThanEqual",
            "NotEquals",
            "SubtractionAssignment"),
    // Boolean boolean evaluators
    BB_EVAL("And", "Equals", "Or"),
    // Attributes
    ATTR(),
    // Strings
    STRING(),
    // Numbers
    NUMBER();

    String[] myOptions;

    /**
     * Create an action parameter with a list of java class names for the Possible elements in the
     * 
     * @param options
     */
    private ActionParameters (String ... options) {
        myOptions = options;
    }

    /**
     * Get all the options for an action
     */
    public List<String> getOptions () {
        return Arrays.asList(myOptions);
    }

}