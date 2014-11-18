package game_engine.gameRepresentation.stateRepresentation;

/**
 * 
 * @author Jonathan Tseng, Nishad Agrawal, Rahul
 *
 */
public abstract class DescribableState {

    private String myName;
    private String myDescription;

    protected DescribableState (String name) {
        myName = name;
        myDescription = "";
    }

    public void updateName (String name) {
        myName = name;
    }

    public void updateDescription (String description) {
        myDescription = description;
    }

    public String getName () {
        return myName;
    }

    public String getDescription () {
        return myDescription;
    }

}
