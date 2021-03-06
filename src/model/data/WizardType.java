package model.data;

/**
 * These are all of the potential types of input that can be given by a Wizard. They represent both
 * the type of the Wizard along with the fields of a WizardData
 * 
 * @author Nishad Agrawal
 *
 */
public enum WizardType {
    UNSPECIFIED,
    ANIMATION_SEQUENCE,
    STRING_ATTRIBUTE,
    BOUNDS,
    VISION_BOUNDS,
    CAMPAIGN,
    GAME_ELEMENT,
    LEVEL,
    NUMBER_ATTRIBUTE,
    POSITION,
    TRIGGER,
    WIDGET
}
