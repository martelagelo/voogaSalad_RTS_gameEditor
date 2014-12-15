// This entire file is part of my masterpiece.
// Nishad Agrawal (nna6)
package model;

import java.util.function.BiConsumer;
import model.data.WizardData;
import model.data.WizardDataType;
import model.data.WizardType;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import model.state.gameelement.traits.AttributeDisplayerState;
import model.state.gameelement.traits.AttributeDisplayerTags;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;


/**
 * Factory that creates a GameElementState of the specified type based upon the
 * data that is stored within a WizardData. This factory has the ability to generate
 * plain GameElementStates, DrawableGameElementStates, SelectableGameElementStates, along
 * with Goals which are just simplified GameElementStates that can only hold a singular action.
 * 
 * @author Nishad Agrawal, Jonathan Tseng
 *
 */
public class GameElementStateFactory {

    private static final int DISPLAYER_STATE_NUMERIC_ARGUMENTS = 2;

    public static GameElementState createGameElementState (WizardData data) {
        return addEssentials(new GameElementState(), data);
    }

    public static DrawableGameElementState createDrawableGameElementState (WizardData data) {
        DrawableGameElementState state = (DrawableGameElementState)
                addEssentials(new DrawableGameElementState(0.0, 0.0, null), data);
        return (DrawableGameElementState) addVisuals(data, state);
    }

    public static SelectableGameElementState createSelectableGameElementState (WizardData data) {
        SelectableGameElementState state = (SelectableGameElementState)
                addEssentials(new SelectableGameElementState(0.0, 0.0, null), data);
        return (SelectableGameElementState) addVisuals(data, state);
    }

    public static GameElementState createGoal (WizardData data) {
        GameElementState goal = new GameElementState();
        goal.addAction(createActionWrapper(data));
        return goal;
    }

    private static ActionWrapper createActionWrapper (WizardData data) {
        String[] params = data.getValueByKey(WizardDataType.ACTION_PARAMETERS).split(",");
        ActionWrapper wrapper =
                new ActionWrapper(
                                  ActionType.valueOf(data.getValueByKey(WizardDataType.ACTIONTYPE)),
                                  ActionOptions.valueOf(data.getValueByKey(WizardDataType.ACTION)),
                                  params);
        return wrapper;
    }

    private static DrawableGameElementState addVisuals (WizardData data,
                                                        DrawableGameElementState state) {
        state.myAnimatorState = AnimatorStateFactory.createAnimatorState(data);
        state.setBounds(createBounds(data, WizardType.BOUNDS));
        state.setVisionBounds(createBounds(data, WizardType.VISION_BOUNDS));
        createAttributeDisplayerState(data, state);
        return state;
    }

    private static void createAttributeDisplayerState (WizardData data,
                                                       DrawableGameElementState state) {
        for (WizardData widget : data.getWizardDataByType(WizardType.WIDGET)) {
            String[] arguments = widget.getValueByKey(WizardDataType.WIDGET_PARAMETERS).split(",");
            AttributeDisplayerState displayerState;
            if (arguments.length == DISPLAYER_STATE_NUMERIC_ARGUMENTS) {
                displayerState =
                        new AttributeDisplayerState(AttributeDisplayerTags.valueOf(widget
                                .getValueByKey(WizardDataType.WIDGET_TYPE)),
                                                    widget.getValueByKey(WizardDataType.ATTRIBUTE),
                                                    Double.parseDouble(arguments[0]),
                                                    Double.parseDouble(arguments[1]));
            }
            else {
                displayerState =
                        new AttributeDisplayerState(AttributeDisplayerTags.valueOf(widget
                                .getValueByKey(WizardDataType.WIDGET_TYPE)),
                                                    widget.getValueByKey(WizardDataType.ATTRIBUTE),
                                                    arguments[0]);
            }
            state.addAttributeDisplayerState(displayerState);
        }
    }

    private static double[] createBounds (WizardData data, WizardType boundsType) {
        if (!data.getWizardDataByType(boundsType).isEmpty()) {
            String bounds =
                    data.getWizardDataByType(boundsType).get(0)
                            .getValueByKey(WizardDataType.BOUND_VALUES);
            String[] points = bounds.split(",");
            double[] myBounds = new double[points.length];
            for (int i = 0; i < points.length; i++) {
                myBounds[i] = Double.parseDouble(points[i]);
            }
            return myBounds;
        }
        return new double[8];
    }

    private static GameElementState addEssentials (GameElementState state, WizardData data) {
        state.myAttributes.setTextualAttribute(StateTags.NAME.getValue(),
                                               data.getValueByKey(WizardDataType.NAME));

        addToState( (key, value) -> state.myAttributes.setTextualAttribute(key, value),
                   data, WizardType.STRING_ATTRIBUTE, WizardDataType.ATTRIBUTE,
                   WizardDataType.VALUE);

        addToState( (key, value) -> state.myAttributes.setNumericalAttribute(key, Double
                .parseDouble(value)),
                   data, WizardType.NUMBER_ATTRIBUTE, WizardDataType.ATTRIBUTE,
                   WizardDataType.VALUE);

        data.getWizardDataByType(WizardType.TRIGGER)
                .forEach(wiz -> state.addAction(createActionWrapper(wiz)));

        return state;
    }

    private static void addToState (BiConsumer<String, String> cons,
                                    WizardData data,
                                    WizardType type,
                                    WizardDataType ... dataTypes) {
        for (WizardData wiz : data.getWizardDataByType(type)) {
            cons.accept(wiz.getValueByKey(dataTypes[0]), wiz.getValueByKey(dataTypes[1]));
        }
    }

}
