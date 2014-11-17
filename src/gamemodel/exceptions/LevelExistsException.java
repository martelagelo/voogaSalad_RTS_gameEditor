package gamemodel.exceptions;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public class LevelExistsException extends SavableDescribableStateExistsException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = -1237717952177308827L;
    private static final String DESCRIBABLE_STATE_TYPE = "Level";

    public LevelExistsException (String describableStateName) {
        super(DESCRIBABLE_STATE_TYPE, describableStateName);
    }

}
