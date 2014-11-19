package gamemodel;

import game_engine.elementFactories.DeepCopy;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.Attribute;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents all possible elements that could make up a game. This
 * is a **passive data structure/wrapper class** with no behavior.
 * 
 * @author Jonathan Tseng, Rahul
 *
 */
public class GameUniverse {
    private Set<Attribute<Number>> myNumericalAttributesList;
    private Set<Attribute<String>> myStringAttributeList;
    private Set<GameElementState> myGameElementStates;
    private Set<DrawableGameElementState> myDrawableGameElementStates;
    private Set<SelectableGameElementState> mySelectableGameElementStates;

    // TODO THIS WHOLE CLASS
    public GameUniverse () {
        myNumericalAttributesList = new HashSet<>();
        myStringAttributeList = new HashSet<>();
        myDrawableGameElementStates = new HashSet<>();
        mySelectableGameElementStates = new HashSet<>();
        myGameElementStates = new HashSet<>();
    }

    public void addGameElementState (GameElementState ges) {
        myGameElementStates.add(ges);
    }

    public void removeGameElementState (GameElementState ges) {
        myGameElementStates.remove(ges);
    }

    public Set<GameElementState> getGameElementStates () {
        return (Set<GameElementState>) DeepCopy.deepCopy(myGameElementStates);
    }

    public void addDrawableGameElementState (DrawableGameElementState dges) {
        myDrawableGameElementStates.add(dges);
    }

    public Set<DrawableGameElementState> getDrawableGameElementStates () {
        return (Set<DrawableGameElementState>) DeepCopy.deepCopy(myDrawableGameElementStates);
    }

    public void removeDrawableGameElementState (DrawableGameElement dges) {
        myDrawableGameElementStates.remove(dges);
    }
    
    public void removeGameElementState(GameElementState ges ) {
        myGameElementStates.remove(ges);
    }
    

    // TODO
    public void addElement (GameElementState gameElementState) {
        // TODO
    }

    public public GameElementState getElement (String elementName) {
        return null;
    }

}
