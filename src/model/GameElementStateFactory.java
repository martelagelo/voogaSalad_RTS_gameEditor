package model;

import java.util.function.BiConsumer;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
import view.editor.wizards.WizardType;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerState;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerTags;


/**
 * Factory that creates a SavableGameElementState based on the info args
 * 
 * @author Nishad Agrawal, Jonathan Tseng
 *
 */
public class GameElementStateFactory {

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
                addEssentials(new SelectableGameElementState(0.0, 0.0), data);
        return (SelectableGameElementState) addVisuals(data, state);
    }

    public static GameElementState createGoal (WizardData data) {
        GameElementState goal = new GameElementState();
        goal.addAction(createActionWrapper(data));
        return goal;
    }

    private static ActionWrapper createActionWrapper (WizardData data) {
        String[] params = data.getValueByKey(WizardDataType.ACTION_PARAMETERS).split(",");        
        ActionWrapper wrapper = new ActionWrapper(ActionType.valueOf(data.getValueByKey(WizardDataType.ACTIONTYPE)), 
                                                  ActionOptions.valueOf(data.getValueByKey(WizardDataType.ACTION)), 
                                                  params);
        return wrapper;
    }

    private static DrawableGameElementState addVisuals (WizardData data,
                                                        DrawableGameElementState state) {        
        state.myAnimatorState = AnimatorStateFactory.createAnimatorState(data);
        state.setBounds(createBounds(data));          
        createAttributeDisplayerState(data, state);        
        return state;
    }

    private static void createAttributeDisplayerState (WizardData data,
                                                       DrawableGameElementState state) {
        for (WizardData widget: data.getWizardDataByType(WizardType.WIDGET)) {            
            String[] arguments = widget.getValueByKey(WizardDataType.WIDGET_PARAMETERS).split(",");
            AttributeDisplayerState displayerState;
            if (arguments.length == 2) {
                displayerState = new AttributeDisplayerState(AttributeDisplayerTags.valueOf(widget.getValueByKey(WizardDataType.WIDGET_TYPE)), 
                                                widget.getValueByKey(WizardDataType.ATTRIBUTE), 
                                                Double.parseDouble(arguments[0]), 
                                                Double.parseDouble(arguments[1]));
            }
            else {
                displayerState = new AttributeDisplayerState(AttributeDisplayerTags.valueOf(widget.getValueByKey(WizardDataType.WIDGET_TYPE)), 
                                                             widget.getValueByKey(WizardDataType.ATTRIBUTE), 
                                                             arguments[0]);
            }
            state.addAttributeDisplayerState(displayerState);
        }
    }

    private static double[] createBounds (WizardData data) {        
        if (data.getWizardDataByType(WizardType.BOUNDS).size() > 0) {
            String bounds =
                    data.getWizardDataByType(WizardType.BOUNDS).get(0)
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
        state.attributes.setTextualAttribute(StateTags.NAME.getValue(),
                                             data.getValueByKey(WizardDataType.NAME));

        addToState( (String key, String value) ->
                   state.attributes.setTextualAttribute(key, value),
                   data, WizardType.STRING_ATTRIBUTE, WizardDataType.ATTRIBUTE,
                   WizardDataType.VALUE);

        addToState( (String key, String value) ->
                   state.attributes.setNumericalAttribute(key, Double.parseDouble(value)),
                   data, WizardType.NUMBER_ATTRIBUTE, WizardDataType.ATTRIBUTE,
                   WizardDataType.VALUE);

        for (WizardData wiz : data.getWizardDataByType(WizardType.TRIGGER)) {            
            state.addAction(createActionWrapper(wiz));            
        }        
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
