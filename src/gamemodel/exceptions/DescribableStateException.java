package gamemodel.exceptions;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public abstract class DescribableStateException extends Exception {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = -8000865052693555762L;
    private String myDescribableType;
    private String myDescribableName;

    protected DescribableStateException (String describableStateType,
                                         String describableStateName,
                                         String messageFormat) {
        super(String.format(messageFormat, describableStateType, describableStateName));
        myDescribableType = describableStateType;
        myDescribableName = describableStateName;

    }

    public String getDescribableType () {
        return myDescribableType;
    }

    public String getDescribableName () {
        return myDescribableName;
    }

}
