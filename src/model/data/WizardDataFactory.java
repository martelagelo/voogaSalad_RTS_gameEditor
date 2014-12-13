package model.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import model.state.gameelement.Attribute;
import model.state.gameelement.DrawableGameElementState;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerState;
import engine.visuals.elementVisuals.animations.AnimationSequence;

/**
 * This represents the complement to the GameElementStateFactory and it allows
 * for conversion from GameElementState to WizardData, this is useful for launching
 * for editing.
 * 
 * @author Nishad Agrawal
 *
 */
public class WizardDataFactory {
    
    public static WizardData createWizardData(DrawableGameElementState state) {
        WizardData data = new WizardData();
        data.setType(WizardType.GAME_ELEMENT);
        data.addDataPair(WizardDataType.NAME, state.getName());
        data.addDataPair(WizardDataType.IMAGE, state.myAnimatorState.getImageTag());
        data.addDataPair(WizardDataType.FRAME_X, "" + state.myAnimatorState.getViewportSize().getWidth());
        data.addDataPair(WizardDataType.FRAME_Y, "" + state.myAnimatorState.getViewportSize().getHeight());
        data.addDataPair(WizardDataType.COLOR_MASK, state.myAnimatorState.getColorMaskTag());
        for (Attribute<Number> attr: state.attributes.getNumericalAttributes()) {
            WizardData numberAtr = new WizardData();
            numberAtr.setType(WizardType.NUMBER_ATTRIBUTE);
            numberAtr.addDataPair(WizardDataType.ATTRIBUTE, attr.getName());
            numberAtr.addDataPair(WizardDataType.VALUE, attr.getData().toString());
            data.addWizardData(numberAtr);
        }
        
        for (Attribute<String> attr: state.attributes.getTextualAttributes()) {
            WizardData numberAtr = new WizardData();
            numberAtr.setType(WizardType.STRING_ATTRIBUTE);
            numberAtr.addDataPair(WizardDataType.ATTRIBUTE, attr.getName());
            numberAtr.addDataPair(WizardDataType.VALUE, attr.getData());
            data.addWizardData(numberAtr);
        }
        
        for (List<ActionWrapper> wrappers: state.getActions().values()) {
            for (ActionWrapper action: wrappers) {
                WizardData actionData = new WizardData();
                actionData.setType(WizardType.TRIGGER);
                actionData.addDataPair(WizardDataType.ACTIONTYPE, action.getActionType().name());
                actionData.addDataPair(WizardDataType.ACTION, getAction(action.getActionClassName()));
                actionData.addDataPair(WizardDataType.ACTION_PARAMETERS, removeEnds(Arrays.toString(action.getParameters())));
                data.addWizardData(actionData);
            }
        }
        
        if (state.getDisplayerStates() != null) {
            for (AttributeDisplayerState displayState: state.getDisplayerStates()) {
                WizardData displayData = new WizardData();
                displayData.setType(WizardType.WIDGET);
                displayData.addDataPair(WizardDataType.WIDGET_TYPE, displayState.getDisplayerTag().name());
                displayData.addDataPair(WizardDataType.ATTRIBUTE, displayState.getParameterTag());
                // TODO
            }
        }
        
        for (AnimationSequence sequence: state.myAnimatorState.getAllAnimationSequences()) {
            WizardData animData = new WizardData();
            animData.setType(WizardType.ANIMATION_SEQUENCE);
            // animData.addDataPair(WizardDataType.START_FRAME, sequence.
            // TODO
        }
        return data;
    }
    
    private static String getAction (String actionClassName) {
        return Arrays.asList(ActionOptions.values()).stream()
                .filter(action -> action.getClassString().equals(actionClassName))
                .collect(Collectors.toList()).get(0).name();
    }
    
    private static String removeEnds(String input) {
        return input.substring(1).substring(0, input.length()-2);
    }

}
