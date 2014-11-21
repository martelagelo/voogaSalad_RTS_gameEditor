package gamemodel;

import editor.wizards.WizardData;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;


/**
 * Factory that creates a SavableGameElementState based on the info args
 * 
 * @author Nishad Agrawal, Jonathan Tseng
 *
 */
public class GameElementStateFactory {

    public static final String GAME_ELEMENT = "GameElement";
    public static final String NAME = "Name";
    public static final String STRING_ATTRIBUTE = "StringAttribute";
    public static final String NUMBER_ATTRIBUTE = "NumberAttribute";
    public static final String ATTRIBUTE = "Attribute";
    public static final String VALUE = "Value";
    public static final String TRIGGER = "Trigger";
    public static final String CONDITION = "Condition";
    public static final String ACTION = "Action";

    public static GameElementState createGameElementState (WizardData data) {
        GameElementState state = new GameElementState();

        if (data.getName().equals(GAME_ELEMENT)) {
            state.setTextualAttribute(NAME, data.getValueByKey(NAME));
            for (WizardData wiz : data.getWizardDataByType(STRING_ATTRIBUTE)) {
                state.setTextualAttribute(wiz.getValueByKey(ATTRIBUTE),
                                          wiz.getValueByKey(VALUE));
            }
            for (WizardData wiz : data.getWizardDataByType(NUMBER_ATTRIBUTE)) {
                state.setNumericalAttribute(wiz.getValueByKey(ATTRIBUTE),
                                            Double.parseDouble(wiz.getValueByKey(VALUE)));
            }
            for (WizardData wiz : data.getWizardDataByType(TRIGGER)) {
                state.addConditionActionPair(wiz.getValueByKey(CONDITION),
                                             wiz.getValueByKey(ACTION));
            }
        }
        return state;
    }

    public static DrawableGameElementState createDrawableGameElementState (WizardData data) {
        return null;
    }

    public static SelectableGameElementState createSelectableGameElementState (WizardData data) {
        return null;
    }

}
