package gamemodel;

import editor.wizards.WizardData;
import editor.wizards.WizardDataType;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Dimension;
import game_engine.visuals.Spritesheet;


/**
 * Factory that creates a SavableGameElementState based on the info args
 * 
 * @author Nishad Agrawal, Jonathan Tseng
 *
 */
public class GameElementStateFactory {

    private static GameElementState addEssentials (GameElementState state, WizardData data) {
        state.setTextualAttribute("Name",
                                  data.getValueByKey(WizardDataType.NAME));

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.STRING_ATTRIBUTE)) {
            state.setTextualAttribute(wiz.getValueByKey(WizardDataType.ATTRIBUTE),
                                      wiz.getValueByKey(WizardDataType.VALUE));
        }

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.NUMBER_ATTRIBUTE)) {
            state.setNumericalAttribute(wiz.getValueByKey(WizardDataType.ATTRIBUTE),
                                        Double.parseDouble(wiz.getValueByKey(WizardDataType.VALUE)));
        }

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.TRIGGER)) {
            state.addConditionActionPair(wiz.getValueByKey(WizardDataType.CONDITION),
                                         wiz.getValueByKey(WizardDataType.ACTION));
        }
        return state;
    }

    public static GameElementState createGameElementState (WizardData data) {
        return addEssentials(new GameElementState(), data);
    }

    public static DrawableGameElementState createDrawableGameElementState (WizardData data,
                                                                           String imagePath) {
        DrawableGameElementState state =
                (DrawableGameElementState) addEssentials(
                                                         new DrawableGameElementState(0.0, 0.0),
                                                         data);
        Dimension dim = new Dimension(Integer.parseInt(data.getValueByKey(WizardDataType.FRAME_X)),
                                      Integer.parseInt(data.getValueByKey(WizardDataType.FRAME_Y)));
        Spritesheet mySpritesheet = new Spritesheet(imagePath, dim, Integer.parseInt(data
                .getValueByKey(WizardDataType.ROWS)));
        state.setSpritesheet(mySpritesheet);

        // TODO: actually get animation name from the user
        AnimationSequence myAnimation =
                new AnimationSequence("animation",
                                      Integer.parseInt(data
                                              .getValueByKey(WizardDataType.START_FRAME)),
                                      Integer.parseInt(data
                                              .getValueByKey(WizardDataType.STOP_FRAME)),
                                      Boolean.parseBoolean(data
                                              .getValueByKey(WizardDataType.ANIMATION_REPEAT)));
        // state.addAnimation(myAnimation);

        // TODO: actually get bounds from the user
        double[] myBounds = new double[] { 0.0, 0.0, 0.0, 0.0 };
        state.setBounds(myBounds);

        return state;
    }

    public static SelectableGameElementState createSelectableGameElementState (WizardData data) {
        return null;
    }

    public static GameElementState createGoal (WizardData data) {
        GameElementState goal = new GameElementState();
        for (WizardData wiz : data.getWizardDataByType(WizardDataType.TRIGGER)) {
            goal.addConditionActionPair(wiz.getValueByKey(WizardDataType.CONDITION), 
                                        wiz.getValueByKey(WizardDataType.ACTION));
        }
        return goal;
    }

}
