package gamemodel;

import game_engine.elementFactories.DeepCopy;
import game_engine.gameRepresentation.stateRepresentation.gameElement.Attribute;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Class that represents all possible elements that could make up a game. This
 * is a passive data structure/wrapper class** with no behavior.
 * 
 * 
 * @author Jonathan Tseng, Rahul
 *
 */
public class GameUniverse {
    private Set<Attribute<Number>> myNumericalAttributes;
    private Set<Attribute<String>> myStringAttributes;
    private Set<GameElementState> myGameElementStates;
    private Set<DrawableGameElementState> myDrawableGameElementStates;
    private Set<SelectableGameElementState> mySelectableGameElementStates;

    public GameUniverse () {
        myNumericalAttributes = new HashSet<>();
        myStringAttributes = new HashSet<>();
        myDrawableGameElementStates = new HashSet<>();
        mySelectableGameElementStates = new HashSet<>();
        myGameElementStates = new HashSet<>();
    }

    public void addGameElementState (GameElementState ges) {
        myGameElementStates.add(ges);
    }

    public void addDrawableGameElementState (DrawableGameElementState dges) {
        myDrawableGameElementStates.add(dges);
    }

    public void addSelectableGameElementState (SelectableGameElementState sges) {
        mySelectableGameElementStates.add(sges);
    }

    public Set<GameElementState> getGameElementStates () {
        return (Set<GameElementState>) DeepCopy.deepCopy(myGameElementStates);
    }

    public Set<DrawableGameElementState> getDrawableGameElementStates () {
        return Collections.unmodifiableSet(myDrawableGameElementStates);
    }

    public Set<SelectableGameElementState> getSelectableGameElementStates () {
        return (Set<SelectableGameElementState>) DeepCopy.deepCopy(mySelectableGameElementStates);
    }

    public void removeSelectableGameElementState (SelectableGameElementState sges) {
        mySelectableGameElementStates.remove(sges);
    }

    public void removeDrawableGameElementState (DrawableGameElementState dges) {
        myDrawableGameElementStates.remove(dges);
    }

    public void removeGameElementState (GameElementState ges) {
        myGameElementStates.remove(ges);
    }

    public GameElementState getGameElementState (String elementName) {
        List<GameElementState> matches = myGameElementStates.stream()
                .filter(e -> (e.getName().equals(elementName))).collect(Collectors.toList());
        return (matches.size() != 0) ? matches.get(0) : null;

    }

    public DrawableGameElementState getDrawableGameElementState (String elementName) {
        List<DrawableGameElementState> matches = myDrawableGameElementStates.stream()
                .filter(e -> (e.getName().equals(elementName))).collect(Collectors.toList());
        return (matches.size() != 0) ? matches.get(0) : null;
    }

    public SelectableGameElementState getSelectableElementState (String elementName) {
        List<SelectableGameElementState> matches = mySelectableGameElementStates.stream()
                .filter(e -> (e.getName().equals(elementName))).collect(Collectors.toList());
        return (matches.size() != 0) ? matches.get(0) : null;
    }

    public void addNumericalAttribute (Attribute<Number> number) {
        myNumericalAttributes.add(number);
    }

    public void addStringAttribute (Attribute<String> string) {
        myStringAttributes.add(string);
    }

    public Set<Attribute<Number>> getNumericalAttributes () {
        return myNumericalAttributes;
    }

    public Set<Attribute<String>> getStringAttributes () {
        return myStringAttributes;
    }
}
