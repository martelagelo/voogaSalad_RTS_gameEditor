package engine.gameRepresentation.evaluatables.parameters.helpers;

import engine.stateManaging.GameElementManager;


/**
 * A data wrapper for an element creation promise containing an element name and an element manager. Needed
 * due to the 2 input restrictions of evaluators that has gains in other areas in that it is very
 * easy to specify parameter numbers.
 * 
 * @author Zach
 *
 */
public class ElementPromise {
    private String myElementName;
    private GameElementManager myElementManager;

    public ElementPromise (String elementName, GameElementManager elementManager) {
        myElementName = elementName;
        myElementManager = elementManager;
    }

    public GameElementManager getManager () {
        return myElementManager;
    }

    public String getElementName () {
        return myElementName;
    }

}
