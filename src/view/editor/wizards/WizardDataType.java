package view.editor.wizards;

/**
 * These are all of the potential types of input that can be given by a Wizard. They represent both
 * the type of the Wizard along with the fields of a WizardData
 * 
 * @author Nishad Agrawal
 *
 */
public enum WizardDataType {        
    NAME,
    //attributes
    ATTRIBUTE,
    VALUE,
    // actions: this will change
    ACTIONTYPE,
    ACTION,
    ACTION_PARAMETERS,
    
    CAMPAIGN_NAME,
    // animation specifics
    COLOR_MASK,
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
    SLOWNESS_MULTIPLIER,    
    // place into level: this will change
    X_POSITION,
    Y_POSITION,
    // bounds of dges
    BOUND_VALUES,
    // widgets
    WIDGET_TYPE,
    WIDGET_PARAMETERS
}
