package gamemodel.exceptions;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public class LevelNotFoundException extends SavableDescribableStateNotFoundException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = -4882916094094783161L;
    private static final String DESCRIBABLE_STATE_TYPE = "Level";

    public LevelNotFoundException (String describableStateName) {
        super(DESCRIBABLE_STATE_TYPE, describableStateName);
    }

}
