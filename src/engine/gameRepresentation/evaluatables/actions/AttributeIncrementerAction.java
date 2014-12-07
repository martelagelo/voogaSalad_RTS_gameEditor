package engine.gameRepresentation.evaluatables.actions;

import engine.UI.ParticipantManager;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import engine.gameRepresentation.evaluatables.evaluators.FalseEvaluator;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;


/**
 * Create an action that incriments an attribute to a max value based on a timer
 * 
 * @author Zach
 *
 */
public class AttributeIncrementerAction extends Action {

    private String myAttributeTag;
    private String myAttributeAmountTag;
    private String myMaxValueTag;
    private Long myNumFrames;
    private String myTimerName;

    public AttributeIncrementerAction (String id,
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
        myAttributeTag = args[0];
        myAttributeAmountTag = args[1];
        myMaxValueTag = args[2];
        myNumFrames = Long.valueOf(args[3]);
        myTimerName = args[4];
        return new FalseEvaluator();
    }

    @Override
    protected Boolean evaluate (Evaluatable<?> action, ElementPair elements) {
        GameElement element = elements.getActor();
        if (element.getTimer(myTimerName) > 0) {
            System.out.println("Incrimenting");
            double parameter = element.getNumericalAttribute(myAttributeTag).doubleValue();
            double max = element.getNumericalAttribute(myAttributeAmountTag).doubleValue();
            double amount = element.getNumericalAttribute(myMaxValueTag).doubleValue();
            double newParameter = parameter + amount;

            System.out.println(parameter);
            System.out.println(newParameter);
            if (parameter > max) {
                parameter = max;
            }
            element.setNumericalAttribute(myAttributeTag, newParameter);
            element.setTimer(myTimerName, myNumFrames);
            return true;
        }
        return false;
    }

}
