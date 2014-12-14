package engine.gameRepresentation.evaluatables.parameters.helpers;

import engine.stateManaging.GameElementManager;

/**
 * A data wrapper for an element creation promise containing an element name and
 * an element manager. Needed due to the 2 input restrictions of evaluators that
 * has gains in other areas. This requirement for a wrapper comes from a design
 * choice that was made in the design of evaluators to make the system more
 * dynamic.
 *
 * @author Zach
 *
 */
public class ElementPromise {
    private String myElementName;
    private GameElementManager myElementManager;

    /**
     * Create the data wrapper for element creation
     *
     * @param elementName
     *            the name of the element
     * @param elementManager
     *            the manager used to create the parameter
     */
    public ElementPromise (String elementName, GameElementManager elementManager) {
        myElementName = elementName;
        myElementManager = elementManager;
    }

    /**
     * @return the element manager from the promise
     */
    public GameElementManager getManager () {
        return myElementManager;
    }

    /**
     * @return the name parameter of the element to be created.
     */
    public String getElementName () {
        return myElementName;
    }

}
