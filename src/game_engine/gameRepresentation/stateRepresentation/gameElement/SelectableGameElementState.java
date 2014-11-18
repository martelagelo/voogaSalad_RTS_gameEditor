package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.computers.boundsComputers.Sighted;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is the most widely used GameElement. This type of GameElement has both a bounding box and a
 * vision box. This is the element that reacts to collisions with DrawableGameElements.
 * 
 * @author Steve, Jonathan, Rahul
 *
 */
public class SelectableGameElementState extends DrawableGameElementState implements
        Sighted {

    protected Map<String, Map<String, String>> myAllAbilityRepresentations;
    private Map<String, String> myCurrentAbilityRepresentation;
    protected Map<String, ArrayList<DrawableGameElementState>> myInteractingElements;

    public SelectableGameElementState (Number xPosition, Number yPosition) {
        super(xPosition, yPosition);
        myInteractingElements = new HashMap<String, ArrayList<DrawableGameElementState>>();
        // TODO Auto-generated constructor stub
    }

    public void addCollidingElements (List<DrawableGameElementState> collidingElements) {
        for (DrawableGameElementState element : collidingElements) {
            addInteractingElement("CollidingElements", element);
        }
    }

    public void addVisibleElements (List<DrawableGameElementState> visibleElements) {
        for (DrawableGameElementState element : visibleElements) {
            addInteractingElement("VisibleElements", element);
        }
    }

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

    private void updateAbilityRepresentation (String identifier) {
        myCurrentAbilityRepresentation = myAllAbilityRepresentations.get(identifier);
    }

    public Map<String, String> getCurrentInteractionInformation () {
        return myCurrentAbilityRepresentation;
    }

    public Map<String, ArrayList<DrawableGameElementState>> getInteractingElements () {
        return myInteractingElements;
    }

}
