package gamemodel;

import java.util.HashMap;
import java.util.Map;
import editor.wizards.WizardData;
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

    public static final String GAME_ELEMENT = "GameElement";
    public static final String DRAWABLE_GAME_ELEMENT = "DrawableGameElement";
    public static final String NAME = "Name";
    public static final String STRING_ATTRIBUTE = "StringAttribute";
    public static final String NUMBER_ATTRIBUTE = "NumberAttribute";
    public static final String ATTRIBUTE = "Attribute";
    public static final String VALUE = "Value";
    public static final String TRIGGER = "Trigger";
    public static final String CONDITION = "Condition";
    public static final String ACTION = "Action";
    public static final String CAMPAIGN = "Campaign";
    public static final String LEVEL = "Level";
    public static final String IMAGE = "Image";

    public static final String WIDTH = "Width";
    public static final String HEIGHT = "Height";
    public static final String FRAME_X = "FrameX";
    public static final String FRAME_Y = "FrameY";
    public static final String ROWS = "Rows";
    public static final String START_FRAME = "StartFrame";
    public static final String STOP_FRAME = "StopFrame";
    public static final String ANIMATION_REPEAT = "AnimationRepeat";

    private static GameElementState addEssentials (GameElementState state, WizardData data) {
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
        return state;
    }

    public static GameElementState createGameElementState (WizardData data) {
        return addEssentials(new GameElementState(), data);
    }

    public static DrawableGameElementState createDrawableGameElementState (WizardData data,
                                                                           String imagePath) {
        DrawableGameElementState state =
                (DrawableGameElementState) addEssentials(new DrawableGameElementState(0.0, 0.0),
                                                         data);
        Dimension dim = new Dimension(Integer.parseInt(data.getValueByKey(FRAME_X)),
                                      Integer.parseInt(data.getValueByKey(FRAME_Y)));
        Spritesheet mySpritesheet =
                new Spritesheet(imagePath, dim, Integer.parseInt(data.getValueByKey(ROWS)));
        state.setSpritesheet(mySpritesheet);
        
      //TODO: actually get animation name from the user
        AnimationSequence myAnimation =
                new AnimationSequence("animation",
                                      Integer.parseInt(data.getValueByKey(START_FRAME)),
                                      Integer.parseInt(data.getValueByKey(STOP_FRAME)),
                                      Boolean.parseBoolean(data.getValueByKey(ANIMATION_REPEAT)));        
        state.addAnimation(myAnimation);
        
        //TODO: actually get bounds from the user
        double[] myBounds = new double[] { 0.0, 0.0, 0.0, 0.0 };
        state.setBounds(myBounds);
        
        return state;
    }

    public static SelectableGameElementState createSelectableGameElementState (WizardData data) {
        return null;
    }

}
