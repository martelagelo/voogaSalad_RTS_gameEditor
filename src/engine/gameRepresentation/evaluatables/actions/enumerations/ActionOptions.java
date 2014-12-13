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
     * editor. # are used to indicate the location of each string input. Additional enumerations of
     * input possibilities are given through the ActionParameters enumeration.
     * 
     * The format for one of these enumerations is
     * Human-readable action name, action class name, action prompt, list of required parameters
     * 
     */
    ACT_ON_OBJECTS_ACTION("Basic Action",
                          "ActOnObjectsAction",
                          "Do #", ActionParameters.EE_EVAL),
    OBJECT_CONDITION_ACTION("Condition Action",
                            "ObjectConditionAction",
                            "If the other object and I #, do #",
                            ActionParameters.EE_EVAL,
                            ActionParameters.EE_EVAL),
    CHECK_ATTR_SET_ATTR_ACTION(
                               "Attribute Action",
                               "CheckAttributeSetAttributeAction",
                               "If # attribute # 's value is # #, # # should be # to/than #",
                               ActionParameters.OBJECT_DESIGNATOR,
                               ActionParameters.ATTR,
                               ActionParameters.NN_EVAL,
                               ActionParameters.NUMBER,
                               ActionParameters.OBJECT_DESIGNATOR,
                               ActionParameters.ATTR,
                               ActionParameters.NN_EVAL,
                               ActionParameters.NUMBER),
    PERFORM_CALCULATION_ON_VALUE("Perform Attribute Calculation",
                                 "PerformAttrCalculationAction",
                                 "Perform # # on my # attribute",
                                 ActionParameters.NN_EVAL,
                                 ActionParameters.NUMBER,
                                 ActionParameters.ATTR),
    PLAYER_ATTRIBUTE_CONDITION(
                               "Player Stats Check",
                               "PlayerStatsCheckAction",
                               "If # attribute # is # #, make the attribute # # #",
                               ActionParameters.PLAYER_TYPE,
                               ActionParameters.ATTR,
                               ActionParameters.NN_EVAL,
                               ActionParameters.NUMBER,
                               ActionParameters.ATTR,
                               ActionParameters.NN_EVAL,
                               ActionParameters.NUMBER),
    OBJECT_LOCATION_DETECTION(
                              "Object Location Check",
                              "ObjectLocationCheckAction",
                              "If # object of type # is within # of the location #,# make the attribute, # # #",
                              ActionParameters.PLAYER_TYPE,
                              ActionParameters.STRING,
                              ActionParameters.NUMBER,
                              ActionParameters.NUMBER,
                              ActionParameters.NUMBER,
                              ActionParameters.ATTR,
                              ActionParameters.NN_EVAL,
                              ActionParameters.NUMBER),

    CHECK_CONDITION_CREATE_OBJECT_ACTION(
                                         "Check attribute create object",
                                         "CheckAttributeCreateObjectAction",
                                         "If my attribute # value # # then create a # at my spawn location that that costs the player # (element attribute name) "
                                                 +
                                                 "from their # value with a cooldown timer named # with a value of # frames with specific attributes # set to be #",
                                         ActionParameters.ATTR,
                                         ActionParameters.NN_EVAL,
                                         ActionParameters.NUMBER,
                                         ActionParameters.STRING,
                                         ActionParameters.ATTR,
                                         ActionParameters.ATTR,
                                         ActionParameters.STRING,
                                         ActionParameters.NUMBER,
                                         ActionParameters.STRING_LIST,
                                         ActionParameters.ATTR_LIST),
    INCREMENT_DECREMENT_ACTION(
                               "Decrement object's attribute and add it to mine",
                               "DecrementIncrementAttributeAction",
                               "If other element is of type #, subtract its # by my # attribute to a minimum of # and add that amount to"
                                       +
                                       " my # with a cooldown timer named # with a value of # frames.",
                               ActionParameters.STRING,
                               ActionParameters.ATTR,
                               ActionParameters.ATTR,
                               ActionParameters.NUMBER,
                               ActionParameters.ATTR,
                               ActionParameters.STRING,
                               ActionParameters.NUMBER),
    ATTRIBUTE_INTERACTION_ACTION("Change an attribute of the player based on my attribute",
                                 "PlayerAttributeInteractionAction",
                                 "# my # attribute to #(my/another player's) # attribute",
                                 ActionParameters.NN_EVAL,
                                 ActionParameters.ATTR,
                                 ActionParameters.STRING,
                                 ActionParameters.ATTR),
    ATTRIBUTE_INCRIMENT_ACTION(
                               "Incriment an attribute periodically",
                               "AttributeIncrementerAction",
                               "Incriment my # attribute by # to a max of # every # frames on timer #",
                               ActionParameters.ATTR,
                               ActionParameters.ATTR,
                               ActionParameters.ATTR,
                               ActionParameters.NUMBER,
                               ActionParameters.STRING),
    OBJECT_TYPE_CHECK_ACTION(
                             "Object Type Action",
                             "ObjectTypeCheckAction",
                             "If the other object is of type #, "
                                     +
                                     "set # (me/your) # (attribute name) # (assignment evaluator) to/with # (number) # (assignment evaluator) (me/your) # (attr) AND "
                                     +
                                     "do # with both the objects AND "
                                     +
                                     " perform # # (my attribute name) on # (my/other) player's # attribute.",
                             ActionParameters.STRING,
                             ActionParameters.OBJECT_DESIGNATOR,
                             ActionParameters.ATTR,
                             ActionParameters.NN_EVAL,
                             ActionParameters.NUMBER,
                             ActionParameters.NN_EVAL,
                             ActionParameters.OBJECT_DESIGNATOR,
                             ActionParameters.ATTR,
                             ActionParameters.EE_EVAL,
                             ActionParameters.NN_EVAL,
                             ActionParameters.ATTR,
                             ActionParameters.PLAYER_TYPE,
                             ActionParameters.ATTR),

    MOTHER_OF_ALL_ACTIONS(
                          "Mother of all Actions",
                          "MotherOfAllActions",
                          "If #(my/other) participant's # (attribute name) # (evaluator) my # (attr) AND "
                                  +
                                  "If  # (me/your) # (attribute name) # (evaluator < > =) my # (attr), "
                                  +
                                  "Do: # (me/your) #(attribute name) # (assignment evaluator) # (attr) AND "
                                  +
                                  "Do: # (me/your) #(attribute name) # (assignment evaluator) # (attr) AND "
                                  +
                                  "Do: # on both the interacting elements AND "
                                  +
                                  "Do: Set the timer # (timer name) to the value # (attribute) AND "
                                  +
                                  "Do: Perform # # attribute on # (my/other) participant's # (attribute)"
                                  +
                                  "On a timer named # with a value of # ticks.",
                          ActionParameters.PLAYER_TYPE,
                          ActionParameters.ATTR,
                          ActionParameters.NN_EVAL,
                          ActionParameters.ATTR,
                          ActionParameters.OBJECT_DESIGNATOR,
                          ActionParameters.ATTR,
                          ActionParameters.NN_EVAL,
                          ActionParameters.ATTR,
                          ActionParameters.OBJECT_DESIGNATOR,
                          ActionParameters.ATTR,
                          ActionParameters.NN_EVAL,
                          ActionParameters.ATTR,
                          ActionParameters.OBJECT_DESIGNATOR,
                          ActionParameters.ATTR,
                          ActionParameters.NN_EVAL,
                          ActionParameters.ATTR,
                          ActionParameters.EE_EVAL,
                          ActionParameters.STRING,
                          ActionParameters.ATTR,
                          ActionParameters.NN_EVAL,
                          ActionParameters.ATTR,
                          ActionParameters.PLAYER_TYPE,
                          ActionParameters.ATTR,
                          ActionParameters.STRING,
                          ActionParameters.ATTR
    );

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
