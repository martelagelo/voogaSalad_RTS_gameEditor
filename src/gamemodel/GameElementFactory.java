package gamemodel;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;


/**
 * 
 * @author Nishad Agrawal
 *
 */
public class GameElementFactory {
    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * TODO: DELETE THIS CLASS 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    // private Map<String, String> myStringAttributes = new HashMap<>();
    // private Map<String, Number> myNumberAttributes = new HashMap<>();
    // private Map<String, String> myTriggers = new HashMap<>();

    public static Ga    meElementState createGameElementState (GameElementInfoBundle bundle) {
        GameElementState state = new GameElementState();
        // Map<String, String> myConditionActionPairs;
        // List<Attribute<Number>> numericalAttributes;
        // List<Attribute<String>> textualAttributes;

        return state;
    }

    public static DrawableG     ameElementState createDrawableGameElementState (GameElementInfoBundle bundle) {
        return null;
    }

    public static SelectableGa  meElementState createSelectableGameElementState (GameElementInfoBundle bundle) {
        return null;
    }

}
