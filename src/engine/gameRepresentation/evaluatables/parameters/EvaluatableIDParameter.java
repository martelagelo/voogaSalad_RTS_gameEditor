package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.ElementPair;


/**
 * A parameter that returns an evaluator's ID string
 * 
 * @author Zach
 *
 */
public class EvaluatableIDParameter extends Parameter<String> {

    public EvaluatableIDParameter (String id) {
        super(String.class, id);
    }

    @Override
    public String evaluate (ElementPair elements) {
        return getID();
    }

    @Override
    public boolean setValue (ElementPair elements, String value) {
        return false;// This valueis one tied to a tree and is not mutable by leaves of the tree
    }

}
