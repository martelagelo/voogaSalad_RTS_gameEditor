package model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import util.SaveLoadUtility;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
import engine.visual.animation.AnimationSequence;
import engine.visual.animation.AnimationTag;
import engine.visual.animation.AnimatorState;


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
        List<AnimationTag> tags = new ArrayList<>();
        AnimationSequence sequence = new AnimationSequence(tags, 0, 0);

        AnimatorState anim =
                new AnimatorState(data.getValueByKey(WizardDataType.IMAGE),
                                  dim,
                                  Integer.parseInt(data.getValueByKey(WizardDataType.COLS)),
                                  sequences);

        // TODO: actually get bounds from the user
        double[] myBounds = new double[] { 0.0, 0.0, 0.0, 0.0 };
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
        goal.addAction(data.getValueByKey(WizardDataType.ACTIONTYPE),
                       data.getValueByKey(WizardDataType.ACTION));
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

        // TODO: actually get bounds from the user
        double[] myBounds = new double[] { 0.0, 0.0, 0.0, 0.0 };
        state.setBounds(myBounds);
        return state;
    }

    private static GameElementState addEssentials (GameElementState state, WizardData data) {
        state.attributes.setTextualAttribute(StateTags.NAME,
                                             data.getValueByKey(WizardDataType.NAME));

        addToState( (String key, String value) ->
                   state.attributes.setTextualAttribute(key, value),
                   data, WizardDataType.STRING_ATTRIBUTE, WizardDataType.ATTRIBUTE,
                   WizardDataType.VALUE);

        addToState( (String key, String value) ->
                   state.attributes.setNumericalAttribute(key, Double.parseDouble(value)),
                   data, WizardDataType.NUMBER_ATTRIBUTE, WizardDataType.ATTRIBUTE,
                   WizardDataType.VALUE);

        addToState( (String key, String value) -> state.addAction(key, value),
                   data, WizardDataType.TRIGGER, WizardDataType.ACTIONTYPE, WizardDataType.ACTION);

        return state;
    }

    private static void addToState (BiConsumer<String, String> cons, WizardData data,
                                    WizardDataType ... dataTypes) {
        for (WizardData wiz : data.getWizardDataByType(dataTypes[0])) {
            cons.accept(wiz.getValueByKey(dataTypes[1]), wiz.getValueByKey(dataTypes[2]));
        }
    }

}
