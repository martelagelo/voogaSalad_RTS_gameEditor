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
    
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myDescription == null) ? 0 : myDescription.hashCode());
        result = prime * result + ((myName == null) ? 0 : myName.hashCode());
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DescribableState other = (DescribableState) obj;
        if (myDescription == null) {
            if (other.myDescription != null) return false;
        }
        else if (!myDescription.equals(other.myDescription)) return false;
        if (myName == null) {
            if (other.myName != null) return false;
        }
        else if (!myName.equals(other.myName)) return false;
        return true;
    }
    
    

}
