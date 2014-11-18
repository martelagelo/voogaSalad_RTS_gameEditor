package game_engine.gameRepresentation.conditions.evaluators.evaluatorParameters;

/**
 * An evaluator parameter that extracts a parameter contained within an object
 *
 * @author Zach
 *
 */
public abstract class AttributeParameter implements EvaluatorParameter {

    private String myAttributeTag;

    /**
     * Make an attribute parameter with the given tag
     *
     * @param attributeTag
     */
    public AttributeParameter (String attributeTag) {
        myAttributeTag = attributeTag;
    }

    /**
     * Get the tag for the attribute this references
     *
     * @return the attribute tag
     */
    protected String getTag () {
        return myAttributeTag;
    }

}
