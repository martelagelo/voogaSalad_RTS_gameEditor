package view.editor.wizards;

/**
 * These are all of the potential types of input that can be given by a Wizard. They represent both
 * the type of the Wizard along with the fields of a WizardData
 * 
 * @author Nishad Agrawal
 *
 */
public enum WizardDataType {
    UNSPECIFIED,
    GAME_ELEMENT,
    DRAWABLE_GAME_ELEMENT,
    NAME,
    STRING_ATTRIBUTE,
    NUMBER_ATTRIBUTE,
    ATTRIBUTE,
    VALUE,
    TRIGGER,
    ACTIONTYPE,
    ACTION,
    CAMPAIGN,
    LEVEL,
    IMAGE,
    WIDTH,
    HEIGHT,
    ANIMATOR_STATE,
    FRAME_X,
    FRAME_Y,
    COLS,
    ANIMATION_TAG,
    START_FRAME,
    STOP_FRAME,
    ANIMATION_REPEAT,
    ANIMATION_SEQUENCE,
    TERRAIN,
    POSITION,
    X_POSITION,
    Y_POSITION
}
