package engine.gameRepresentation.evaluatables.actions;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import engine.stateManaging.GameElementManager;


/**
 * A basic action that just acts on two objects when given them on an update
 * 
 * @author Zach
 *
 */
public class ActOnObjectsAction extends Action {

    public ActOnObjectsAction (String id,
                               EvaluatorFactory factory,
                               GameElementManager manager,
                               String[] args) {
        super(id, factory, manager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args, EvaluatorFactory factory, GameElementManager manager)
                                                                                       throws ClassNotFoundException,
                                                                                       EvaluatorCreationException {
        GameElementParameter me = new GameElementParameter("", new ActorObjectIdentifier());
        GameElementParameter you = new GameElementParameter("", new ActeeObjectIdentifier());
        return factory.makeEvaluator(args[0], me, you);
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        action.evaluate(elements);
        return true;
    }
}
