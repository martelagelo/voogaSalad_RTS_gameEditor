package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.parameters.helpers.ElementPromise;


/**
 * A parameter that serves as a wrapper for an element promise
 * 
 * @author Zach
 *
 */
public class ElementPromiseParameter extends Parameter<ElementPromise> {

    private ElementPromise myPromise;

    public ElementPromiseParameter (String id, ElementPromise promise) {
        super(ElementPromise.class, id);
        myPromise = promise;
    }

    @Override
    public ElementPromise evaluate (ElementPair elements) {
        return myPromise;
    }

    @Override
    public boolean setValue (ElementPair elements, ElementPromise value) {
        return false;
    }

}
