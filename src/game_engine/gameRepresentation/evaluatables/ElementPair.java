package game_engine.gameRepresentation.evaluatables;

import game_engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * A grouping for elements used in passing actor/actee to conditions and actions.
 * This is intentionally a data wrapper object to make passing parameters simpler.
 * 
 * @author Zach
 *
 */
public class ElementPair {
    private GameElement myActor;
    private GameElement myActee;

    public ElementPair (GameElement actor, GameElement actee) {
        myActor = actor;
        myActee = actee;
    }

    public GameElement getActor () {
        return myActor;
    }

    public GameElement getActee () {
        return myActee;
    }

}
