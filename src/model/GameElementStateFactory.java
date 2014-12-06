package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import util.SaveLoadUtility;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
import view.editor.wizards.WizardType;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
import engine.visuals.Dimension;
import engine.visuals.elementVisuals.animations.AnimationSequence;
import engine.visuals.elementVisuals.animations.AnimationTag;
import engine.visuals.elementVisuals.animations.AnimatorState;


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

        Dimension dim = new Dimension(Integer.parseInt(data.getValueByKey(WizardDataType.FRAME_X)),
                                      Integer.parseInt(data.getValueByKey(WizardDataType.FRAME_Y)));

        Set<AnimationSequence> sequences = new HashSet<>();
        for (WizardData animationData : data.getWizardDataByType(WizardType.ANIMATION_SEQUENCE)) {
            List<AnimationTag> tags =
                    Arrays.asList(animationData.getValueByKey(WizardDataType.ANIMATION_TAG)
                                          .split(","))
                            .stream().map(tag -> AnimationTag.valueOf(tag))
                            .collect(Collectors.toList());
            AnimationSequence sequence = new AnimationSequence(tags, 0, 0);
            sequences.add(sequence);
        }
        AnimatorState anim =
                new AnimatorState(data.getValueByKey(WizardDataType.IMAGE),
                                  dim,
                                  Integer.parseInt(data.getValueByKey(WizardDataType.COLS)),
                                  sequences);
        state.myAnimatorState = anim;

        String bounds =
                data.getWizardDataByType(WizardType.BOUNDS).get(0)
                        .getValueByKey(WizardDataType.BOUND_VALUES);
        String[] points = bounds.split(",");
        double[] myBounds = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            myBounds[i] = Double.parseDouble(points[i]);
        }
        state.setBounds(myBounds);

        return state;
    }

    public static SelectableGameElementState createSelectableGameElementState (WizardData data) {
        SelectableGameElementState state = (SelectableGameElementState)
                addEssentials(new SelectableGameElementState(0.0, 0.0), data);
        return (SelectableGameElementState) addVisuals(data, state);
    }

    public static GameElementState createGoal (WizardData data) {
        GameElementState goal = new GameElementState();
        String[] params = data.getValueByKey(WizardDataType.ACTION_PARAMETERS).split(",");        
        ActionWrapper wrapper = new ActionWrapper(ActionType.valueOf(data.getValueByKey(WizardDataType.ACTIONTYPE)), 
                                                  ActionOptions.valueOf(data.getValueByKey(WizardDataType.ACTION)), 
                                                  params);
        goal.addAction(wrapper);
        return goal;
    }

    private static DrawableGameElementState addVisuals (WizardData data,
                                                        DrawableGameElementState state) {
        try {
            AnimatorState anim =
                    SaveLoadUtility.loadResource(AnimatorState.class,
                                                 data.getValueByKey(WizardDataType.ANIMATOR_STATE));
            state.myAnimatorState = anim;
        }
        catch (Exception e) {
            System.out.println("unable to load json into animator state object");
            e.printStackTrace();
        }

        return state;
    }

    private static GameElementState addEssentials (GameElementState state, WizardData data) {
        state.attributes.setTextualAttribute(StateTags.NAME,
                                             data.getValueByKey(WizardDataType.NAME));

        addToState( (String key, String value) ->
                   state.attributes.setTextualAttribute(key, value),
                   data, WizardType.STRING_ATTRIBUTE, WizardDataType.ATTRIBUTE,
                   WizardDataType.VALUE);

        addToState( (String key, String value) ->
                   state.attributes.setNumericalAttribute(key, Double.parseDouble(value)),
                   data, WizardType.NUMBER_ATTRIBUTE, WizardDataType.ATTRIBUTE,
                   WizardDataType.VALUE);
        //TODO: clean this up into the consumer
        for (WizardData wiz : data.getWizardDataByType(WizardType.TRIGGER)) {
            String[] params = wiz.getValueByKey(WizardDataType.ACTION_PARAMETERS).split(",");
            ActionWrapper wrapper = new ActionWrapper(ActionType.valueOf(data.getValueByKey(WizardDataType.ACTIONTYPE)), 
                                                      ActionOptions.valueOf(data.getValueByKey(WizardDataType.ACTION)), 
                                                      params);
            state.addAction(wrapper);            
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
