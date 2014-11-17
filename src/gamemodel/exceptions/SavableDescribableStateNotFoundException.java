package gamemodel.exceptions;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public abstract class SavableDescribableStateNotFoundException extends
        SavableDescribableStateException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = -3200017521971176803L;
    private static final String MESSAGE_FORMAT = "%s: %s could not be found.";

    protected SavableDescribableStateNotFoundException (String describableStateType,
                                                        String describableStateName) {
        super(describableStateType, describableStateName, MESSAGE_FORMAT);
    }

}
