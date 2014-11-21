package game_engine.gameRepresentation.stateRepresentation.gameElement;

// import
// game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.ConditionOnImmediateElements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is the most widely used GameElement. This type of GameElement has both a
 * bounding box and a vision box. This is the element that reacts to collisions
 * with DrawableGameElements.
 *
 * @author Steve, Jonathan, Rahul
 *
 */
public class SelectableGameElementState extends DrawableGameElementState implements Sighted {
    protected Map<String, Map<String, String>> myAllAbilityRepresentations;
    private Map<String, String> myCurrentAbilityRepresentation;
    protected Map<String, List<DrawableGameElementState>> myInteractingElements;

    public SelectableGameElementState (Number xPosition, Number yPosition) {
        super(xPosition, yPosition);
        myInteractingElements = new HashMap<>();
    }

    /**
     * Add elements that the selectable game element can collide with.
     * 
     * @param collidingElements the elements that will be evaluated on collision check
     */
    public void addCollidingElements (List<DrawableGameElementState> collidingElements) {
        for (DrawableGameElementState element : collidingElements) {
            addInteractingElement("CollidingElements", element);
        }
    }

    /**
     * Add elements visible by the game element to be evaluated when the element updates due to
     * visible objects
     * 
     * @param visibleElements the elements that are currently visible by the object
     */
    public void addVisibleElements (List<DrawableGameElementState> visibleElements) {
        for (DrawableGameElementState element : visibleElements) {
            addInteractingElement("VisibleElements", element);
        }
    }

    /**
     * Add an element that the game element is currently interacting with
     * 
     * @param elementType the type of element i.e. visible, colliding, etc
     * @param element the element to be added
     */
    public void addInteractingElement (String elementType, DrawableGameElementState element) {
        ArrayList<DrawableGameElementState> elements = new ArrayList<DrawableGameElementState>();
        elements.addAll(myInteractingElements.get(elementType));
        elements.add(element);
        myInteractingElements.put(elementType, elements);
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
    /**
     * @return the elements that the game element is currently interacting with
     */
    public Map<String, List<DrawableGameElementState>> getInteractingElements () {
        return myInteractingElements;
    }

}
