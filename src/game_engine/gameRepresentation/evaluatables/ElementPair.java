package game_engine.gameRepresentation.evaluatables;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;


/**
 * A grouping for elements used in passing actor/actee to conditions and actions.
 * This is intentionally a data wrapper object to make passing parameters simpler.
 * 
 * @author Zach
 *
 */
public class ElementPair {
    private GameElementState myActor;
    private GameElementState myActee;

    public ElementPair (GameElementState actor, GameElementState actee) {
        myActor = actor;
        myActee = actee;
    }

    public GameElementState getActor () {
        return myActor;
    }

    public GameElementState getActee () {
        return myActee;
    }

}
