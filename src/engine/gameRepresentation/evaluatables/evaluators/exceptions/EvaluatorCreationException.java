package engine.gameRepresentation.evaluatables.evaluators.exceptions;

public class EvaluatorCreationException extends Exception {
    /**
     * An exception thrown in evaluator creation
     * 
     * @author Michael R
     */
    private static final long serialVersionUID = 4746783512320805919L;

    public EvaluatorCreationException (String message) {
        super(message);
    }
}
