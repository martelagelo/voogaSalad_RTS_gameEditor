package model.state.gameelement;

// import
// game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.ConditionOnImmediateElements;
import java.util.Map;
import model.state.gameelement.traits.Sighted;


/**
 * This is the most widely used GameElement. This type of GameElement has both a
 * bounding box and a vision box. This is the element that reacts to collisions
 * with DrawableGameElements.
 *
 * @author Steve, Jonathan, Rahul, Zach
 *
 */
public class SelectableGameElementState extends DrawableGameElementState implements Sighted {

    private Map<String, Map<String, String>> myAllAbilityRepresentations;
    private Map<String, String> myCurrentAbilityRepresentation;

    public SelectableGameElementState (Number xPosition, Number yPosition) {
        super(xPosition, yPosition, null);
    }

    @Override
    public double[] getVisionBounds () {
        // TODO do this
        return null;
        // return visualRepresentation.getVisionBounds();
    }

    /**
     * @return the useable abilities of the selectable game element
     */
    public Map<String, String> getCurrentInteractionInformation () {
        return myCurrentAbilityRepresentation;
    }

}