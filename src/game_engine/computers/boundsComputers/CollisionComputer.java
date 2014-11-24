package game_engine.computers.boundsComputers;

import game_engine.computers.Computer;
import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.evaluators.CollisionEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import game_engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Checks for collisions between groups of objects and give the primary object references
 *
 * @author Zach, Jonathan, Rahul, Nishad
 *
 */
public class CollisionComputer extends
        Computer<DrawableGameElement, DrawableGameElement> {
    private Evaluator<?, ?, ?> collisionEvaluator;

    /**
     * Initialize the Collision computer to use an evaluatable to check if objects are colliding
     */
    public CollisionComputer () {
        collisionEvaluator =
                new CollisionEvaluator<>(
                                         new GameElementParameter(new ActorObjectIdentifier(), null),
                                         new GameElementParameter(new ActeeObjectIdentifier(), null));
    }

    /**
     * Returns true if there is a collision between the two bounded objects
     *
     * @see Computer#checkComputingCondition
     */
    @Override
    protected boolean checkComputingCondition (
                                               DrawableGameElement primaryObject,
                                               DrawableGameElement otherObject) {
        ElementPair elementPair = new ElementPair(primaryObject, otherObject);
        return (Boolean) collisionEvaluator.getValue(elementPair);
    }

    /**
     * Give the primary object the objects to add in its colliding elements collection
     *
     * @see Computer#givePrimaryObjectElements
     */
    @Override
    protected void givePrimaryObjectElements (
                                              DrawableGameElement primaryObject,
                                              List<DrawableGameElement> listToAdd) {
        if(listToAdd.size()>0){
            System.out.println("Collision!");
        }
        ((SelectableGameElementState) primaryObject.getGameElementState())
                .addCollidingElements(listToAdd.stream()
                        .map(element -> (DrawableGameElementState) element.getGameElementState())
                        .collect(Collectors.toList()));
    }

}
