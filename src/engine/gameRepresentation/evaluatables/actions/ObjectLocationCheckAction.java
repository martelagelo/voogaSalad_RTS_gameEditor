package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.stateManaging.GameElementManager;


public class ObjectLocationCheckAction extends Action {

    public ObjectLocationCheckAction (String id,
                                      EvaluatorFactory factory,
                                      GameElementManager elementManager,
                                      ParticipantManager participantManager,
                                      String[] args) {
        super(id, factory, elementManager, participantManager, args);
    }

    @Override
    protected Evaluatable<?> initializeAction (String[] args,
                                               EvaluatorFactory factory,
                                               GameElementManager elementManager,
                                               ParticipantManager participantManager)
                                                                                     throws ClassNotFoundException,
                                                                                     EvaluatorCreationException {
        //TODO implmenent this
        return null;
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        // TODO Auto-generated method stub
        return null;
    }

}
