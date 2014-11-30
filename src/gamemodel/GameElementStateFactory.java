package gamemodel;

import editor.wizards.WizardData;
import editor.wizards.WizardDataType;
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

    private static GameElementState addEssentials (GameElementState state, WizardData data) {
<<<<<<< HEAD
        state.attributes.setTextualAttribute(NAME, data.getValueByKey(NAME));
        for (WizardData wiz : data.getWizardDataByType(STRING_ATTRIBUTE)) {
            state.attributes.setTextualAttribute(wiz.getValueByKey(ATTRIBUTE), wiz.getValueByKey(VALUE));
        }
        for (WizardData wiz : data.getWizardDataByType(NUMBER_ATTRIBUTE)) {
            state.attributes.setNumericalAttribute(wiz.getValueByKey(ATTRIBUTE),
                    Double.parseDouble(wiz.getValueByKey(VALUE)));
=======
        state.setTextualAttribute("Name",
                                  data.getValueByKey(WizardDataType.NAME));

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.STRING_ATTRIBUTE)) {
            state.setTextualAttribute(wiz.getValueByKey(WizardDataType.ATTRIBUTE),
                                      wiz.getValueByKey(WizardDataType.VALUE));
        }

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.NUMBER_ATTRIBUTE)) {
            state.setNumericalAttribute(wiz.getValueByKey(WizardDataType.ATTRIBUTE),
                                        Double.parseDouble(wiz.getValueByKey(WizardDataType.VALUE)));
>>>>>>> model
        }

        for (WizardData wiz : data.getWizardDataByType(WizardDataType.TRIGGER)) {
            state.addAction(wiz.getValueByKey(WizardDataType.ACTIONTYPE),
                            wiz.getValueByKey(WizardDataType.ACTION));
        }
        return state;
    }

    public static GameElementState createGameElementState (WizardData data) {
        return addEssentials(new GameElementState(), data);
    }

    public static DrawableGameElementState createDrawableGameElementState (WizardData data,
<<<<<<< HEAD
            String imagePath) {
        /*
         * DrawableGameElementState state = (DrawableGameElementState)
         * addEssentials(new DrawableGameElementState(0.0, 0.0), data);
         * Dimension dim = new
         * Dimension(Integer.parseInt(data.getValueByKey(FRAME_X)),
         * Integer.parseInt(data .getValueByKey(FRAME_Y))); Spritesheet
         * mySpritesheet = new Spritesheet(imagePath, dim,
         * Integer.parseInt(data.getValueByKey(ROWS)));
         * state.setSpritesheet(mySpritesheet);
         * 
         * // TODO: actually get animation name from the user AnimationSequence
         * myAnimation = new AnimationSequence("animation",
         * Integer.parseInt(data .getValueByKey(START_FRAME)),
         * Integer.parseInt(data.getValueByKey(STOP_FRAME)));
         */
        // Boolean.parseBoolean(data.getValueByKey(ANIMATION_REPEAT)));
=======
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
>>>>>>> model
        // state.addAnimation(myAnimation);

        // TODO: actually get bounds from the user - we are using the double[]
        // implementation
        // necessary to make Polygons, so we need groups of two doubles per
        // point on the polygon
        // double[] myBounds = new double[] { 0.0, 0.0, 0.0, 0.0 };
        // state.setBounds(myBounds);

        // return state;
        return null;
    }

    public static SelectableGameElementState createSelectableGameElementState (WizardData data) {
        return null;
    }

    public static GameElementState createGoal (WizardData data) {
        GameElementState goal = new GameElementState();
<<<<<<< HEAD
        for (WizardData wiz : data.getWizardDataByType(TRIGGER)) {
            goal.addAction(wiz.getValueByKey(CONDITION), wiz.getValueByKey(ACTION));
        }
=======
        goal.addAction(data.getValueByKey(WizardDataType.ACTIONTYPE),
                       data.getValueByKey(WizardDataType.ACTION));
>>>>>>> model
        return goal;
    }

}
