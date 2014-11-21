package game_engine.gameRepresentation.stateRepresentation;

/**
 * A state object that has a name and description. Essentially used to create states with associated
 * name and description metadata.
 * 
 * @author Jonathan, Nishad, Rahul
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
