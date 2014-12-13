package engine.gameRepresentation.evaluatables;

/**
 * An evaluatable that always evaluates to false. Used for silently failing in
 * the case of user input error. If the input is malformed or the top level
 * evaluatable of the hierarchy is not a boolean evaluatable, use this to ensure
 * proper function of the game element.
 * 
 * @author Zach
 *
 */
public class FalseEvaluatable extends Evaluatable<Boolean> {

    public FalseEvaluatable () {
        super(Boolean.class);
    }

    @Override
    public Boolean evaluate (ElementPair elements) {
        return false;
    }

}
