package gamemodel;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public abstract class SavableDescribableState {

    private String myName;
    private String myDescription;

    protected SavableDescribableState (String name) {
        myName = name;
    }

    protected void updateName (String name) {
        myName = name;
    }

    protected void updateDescription (String description) {
        myDescription = description;
    }

    protected String getName () {
        return myName;
    }

    protected String getDescription () {
        return myDescription;
    }

}
