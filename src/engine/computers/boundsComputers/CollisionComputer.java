package engine.computers.boundsComputers;

import engine.computers.Computer;
import engine.computers.objectClassifications.InteractingElementType;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.evaluators.Collision;
import engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Checks for collisions between groups of objects and give the primary object references
 *
 * @author Zach, Jonathan
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
                new Collision<>(new GameElementParameter(new ActorObjectIdentifier(),
                                                         null),
                                new GameElementParameter(new ActeeObjectIdentifier(),
                                                         null));
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
        ElementPair elementPair =
                new ElementPair(primaryObject,
                                otherObject);
        return (Boolean) collisionEvaluator.evaluate(elementPair);
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
        if (listToAdd.size() > 0) {
        }
        ((SelectableGameElement) primaryObject)
                .addInteractingElements(InteractingElementType.COLLIDING, listToAdd.stream()
                        .map(element -> (DrawableGameElement) element)
                        .collect(Collectors.toList()));
    }

}
