package engine.gameRepresentation.evaluatables.actions.enumerations;

import java.util.Arrays;
import java.util.List;


/**
 * An enumeration for all the possible action options. The enum provides the java class name of the
 * Action through its getClassString method as well as its prompt
 * Required string inputs from the editor for the constructor are placed in the prompt string as a #
 * 
 * @author Zach
 *
 */
public enum ActionOptions {
    /**
     * Action types give a string representation of the prompt needed to create the action for the
     * editor. # are used to indicate the location of each string input. Symbols are used to
     * indicate which type of strings need to be input into the
     * constructor at various locations.
     *
     * Symbol meanings are as follows
     * EE_EVAL -> String class name of an evaluator that operates on 2 game elements.
     * NN_EVAL -> String class name of an evaluator that operates on 2 numbers.
     * ATTR -> a string corresponding to an attribute name.
     * STRING -> any string.
     * NUMBER -> any numeric string.
     * 
     */
    ACT_ON_OBJECTS_ACTION("Basic Action",
                          "ActOnObjectsAction",
                          "If I'm interacting with any object, do #", ActionParameters.EE_EVAL),
    OBJECT_CONDITION_ACTION("Condition Action",
                            "ObjectConditionAction",
                            "If the other object and I #, do #",
                            ActionParameters.EE_EVAL,
                            ActionParameters.EE_EVAL),
    CHECK_ATTR_SET_ATTR_ACTION(
                               "Attribute Action",
                               "CheckAttributeSetAttributeAction",
                               "If my attribute # 's value is # #, my # should be # to/than #",
                               ActionParameters.ATTR,
                               ActionParameters.NN_EVAL,
                               ActionParameters.NUMBER,
                               ActionParameters.ATTR,
                               ActionParameters.NN_EVAL,
                               ActionParameters.NUMBER),
    CREATE_OBJECT_ACTION(
                         "Create Object",
                         "CreateObjectAction",
                         "Create a # at my spawn location.",
                         ActionParameters.STRING),
    PLAYER_ATTRIBUTE_CONDITION(
                               "Player Stats Check",
                               "PlayerStatsCheckAction",
                               "If #(my/another player's) attribute # is # #",
                               ActionParameters.STRING,
                               ActionParameters.ATTR,
                               ActionParameters.NN_EVAL,
                               ActionParameters.NUMBER),
    OBJECT_LOCATION_DETECTION(
                              "Object Location Check",
                              "ObjectLocationCheckAction",
                              "If #(my/another player's) object of type # is at the location #,#",
                              ActionParameters.STRING,
                              ActionParameters.STRING,
                              ActionParameters.NUMBER,
                              ActionParameters.NUMBER);

    private String myClassName;
    private String myDisplayName;
    private String myMessage;
    private ActionParameters[] myOperators;

    /**
     * Make a new action enum
     * 
     * @param className the class name of the action needed by the factory
     * @param message the prompt displayed by the editor to make the action
     * @param argTypes the types of arguments to replace all # instances in the message
     */
    private ActionOptions (String displayName,
                           String className,
                           String message,
                           ActionParameters ... argTypes) {
        myDisplayName = displayName;
        myClassName = className;
        myMessage = message;
        myOperators = argTypes;
    }

    /**
     * Return the name of the enum
     */
    @Override
    public String toString () {
        return myDisplayName;
    }

    /**
     * Return the message to be displayed by the enum
     */
    public String getMessage () {
        return myMessage;
    }

    /**
     * Return a string List of all the string types needed by the enum
     * 
     * @return the iterator
     */
    public List<ActionParameters> getOperators () {
        return Arrays.asList(myOperators);
    }

    /**
     * @return the string name of the class that must be constructed for the action by the factory
     */
    public String getClassString () {
        return myClassName;
    }

}
