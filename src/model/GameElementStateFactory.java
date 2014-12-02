package model;

import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import util.SaveLoadUtility;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
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
                addEssentials(new DrawableGameElementState(0.0, 0.0,null), data);
        return addVisuals(data, state);
    }

    public static SelectableGameElementState createSelectableGameElementState (WizardData data) {
        SelectableGameElementState state =(SelectableGameElementState) 
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
            AnimatorState anim = SaveLoadUtility.loadResource(AnimatorState.class,
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

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.STRING_ATTRIBUTE)) {
            state.attributes.setTextualAttribute(wiz.getValueByKey(WizardDataType.ATTRIBUTE),
                                                 wiz.getValueByKey(WizardDataType.VALUE));
        }

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.NUMBER_ATTRIBUTE)) {
            state.attributes.setNumericalAttribute(wiz.getValueByKey(WizardDataType.ATTRIBUTE),
                                                   Double.parseDouble(wiz
                                                           .getValueByKey(WizardDataType.VALUE)));
        }

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.TRIGGER)) {
            state.addAction(wiz.getValueByKey(WizardDataType.ACTIONTYPE),
                            wiz.getValueByKey(WizardDataType.ACTION));
        }
        return state;
    }

}
