package game_engine.gameRepresentation.conditions.conditionsOnRemoteElements;

import game_engine.stateManaging.GameElementManager;


public class AmountOfRemoteElementsIsLessThanValueCondition implements ConditionOnRemoteElements {

    private GameElementManager theElementManager;
    private String myElementName;
    private Number myValue;

    public AmountOfRemoteElementsIsLessThanValueCondition (String elementName,
                                                           Number value,
                                                           GameElementManager elementManager) {
        theElementManager = elementManager;
        myElementName = elementName;
        myValue = value;
    }

    @Override
    public boolean evaluate (String identifier) {
        return theElementManager.findAllElementsOfType(myElementName).size() < myValue.intValue();
    }

}
