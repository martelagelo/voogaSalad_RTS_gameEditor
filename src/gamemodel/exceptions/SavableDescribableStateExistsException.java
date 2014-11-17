package gamemodel.exceptions;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public abstract class SavableDescribableStateExistsException extends
        SavableDescribableStateException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = 2965454764348670978L;
    private static final String MESSAGE_FORMAT = "%s: %s already exists.";

    protected SavableDescribableStateExistsException (String describableStateType,
                                                      String describableStateName) {
        super(describableStateType, describableStateName, MESSAGE_FORMAT);
    }

}
