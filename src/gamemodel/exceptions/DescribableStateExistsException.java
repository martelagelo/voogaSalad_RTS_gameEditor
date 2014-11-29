package gamemodel.exceptions;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public abstract class DescribableStateExistsException extends
        DescribableStateException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = 2965454764348670978L;
    private static final String MESSAGE_FORMAT = "%s: %s already exists.";

    protected DescribableStateExistsException (String describableStateType,
                                               String describableStateName) {
        super(describableStateType, describableStateName, MESSAGE_FORMAT);
    }

}
