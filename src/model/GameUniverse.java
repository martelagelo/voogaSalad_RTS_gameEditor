package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import model.state.gameelement.Attribute;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import util.DeepCopy;


/**
 * Class that represents all possible elements that could make up a game. This
 * is a passive data structure/wrapper class** with no behavior.
 * 
 * 
 * @author Jonathan Tseng, Rahul
 *
 */
public class GameUniverse {
    private Set<Long> myParticipantColors;
    private Set<Attribute<Number>> myNumericalAttributes;
    private Set<Attribute<String>> myTextualAttributes;
    private Set<GameElementState> myGameElementStates;
    private Set<DrawableGameElementState> myDrawableGameElementStates;
    private Set<SelectableGameElementState> mySelectableGameElementStates;

    public GameUniverse () {
        myParticipantColors = new HashSet<>();
        myNumericalAttributes = new HashSet<>();
        myTextualAttributes = new HashSet<>();
        myDrawableGameElementStates = new HashSet<>();
        mySelectableGameElementStates = new HashSet<>();
        myGameElementStates = new HashSet<>();
    }

    public void addParticipantColor(Long color) {
        myParticipantColors.add(color);
    }
    
    public Set<Long> getParticipantColors() {
        return Collections.unmodifiableSet(myParticipantColors);
    }
    
    public void addGameElementState (GameElementState ges) {
        myGameElementStates.add(ges);
        storeAttributes(ges);
    }    

    public void addDrawableGameElementState (DrawableGameElementState dges) {
        myDrawableGameElementStates.add(dges);
        storeAttributes(dges);
    }

    public void addSelectableGameElementState (SelectableGameElementState sges) {
        mySelectableGameElementStates.add(sges);
        storeAttributes(sges);
    }
    
    private void storeAttributes (GameElementState ges) {
        myNumericalAttributes.addAll(ges.myAttributes.getNumericalAttributes());
        myTextualAttributes.addAll(ges.myAttributes.getTextualAttributes());
    }

    public Set<GameElementState> getGameElementStates () {
        return Collections.unmodifiableSet(myGameElementStates);
    }

    public Set<DrawableGameElementState> getDrawableGameElementStates () {
        return Collections.unmodifiableSet(myDrawableGameElementStates);
    }

    public Set<SelectableGameElementState> getSelectableGameElementStates () {
        return Collections.unmodifiableSet(mySelectableGameElementStates);
    }

    public void removeSelectableGameElementState (String sges) {
        Set<SelectableGameElementState> nonMatches = mySelectableGameElementStates.stream().filter(e -> (!e.getName().equals(sges)))
                .collect(Collectors.toSet());
        mySelectableGameElementStates = nonMatches; 
    }

    public void removeDrawableGameElementState (String dges) {
        Set<DrawableGameElementState> nonMatches = myDrawableGameElementStates.stream().filter(e -> (!e.getName().equals(dges)))
                        .collect(Collectors.toSet());
        myDrawableGameElementStates = nonMatches;   
    }

    public void removeGameElementState (GameElementState ges) {
        Set<GameElementState> nonMatches = myGameElementStates.stream().filter(e -> (!e.getName().equals(ges)))
                .collect(Collectors.toSet());
        myGameElementStates = nonMatches;          
    }

    public GameElementState getGameElementState (String elementName) {
        List<GameElementState> matches =
                myGameElementStates.stream().filter(e -> (e.getName().equals(elementName)))
                        .collect(Collectors.toList());
        return (matches.size() != 0) ? (GameElementState) DeepCopy.deepCopy(matches.get(0)) : null;
    }

    public DrawableGameElementState getDrawableGameElementState (String elementName) {
        List<DrawableGameElementState> matches =
                myDrawableGameElementStates.stream().filter(e -> (e.getName().equals(elementName)))
                        .collect(Collectors.toList());
        return (matches.size() != 0) ? (DrawableGameElementState) DeepCopy.deepCopy(matches.get(0)) : null;
    }

    public SelectableGameElementState getSelectableGameElementState (String elementName) {
        List<SelectableGameElementState> matches =
                mySelectableGameElementStates.stream()
                        .filter(e -> (e.getName().equals(elementName)))
                        .collect(Collectors.toList());
        return (matches.size() != 0) ? (SelectableGameElementState) DeepCopy.deepCopy(matches.get(0)) : null;
    }

    public Set<Attribute<Number>> getNumericalAttributes () {
        return myNumericalAttributes;
    }

    public Set<Attribute<String>> getStringAttributes () {
        return myTextualAttributes;
    }
}
