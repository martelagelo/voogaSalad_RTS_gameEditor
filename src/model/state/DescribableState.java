package model.state;

import util.JSONable;

/**
 * A state object that has a name and description. Essentially used to create states with associated
 * name and description metadata.
 * 
 * @author Jonathan, Nishad, Rahul
 *
 */
public abstract class DescribableState implements JSONable{

    private String myName;
    private String myDescription;
    private final int myHashingPrime = 31;

    protected DescribableState (String name) {
        myName = name;
        myDescription = "";
    }

    /**
     * Sets the name of the DescribableState
     * 
     * @param name
     *        the name of the describable state 
     */
    public void updateName (String name) {
        myName = name;
    }

    /**
     * Sets the description of the DescribableState
     * 
     * @param description
     *        the description of the describable state
     */
    public void updateDescription (String description) {
        myDescription = description;
    }

    /**
     * Returns the name of the DescribableState
     * 
     * @return name
     *         the name of the describable state
     */
    public String getName () {
        return myName;
    }

    /**
     * Returns the description of the DescribableState
     * 
     * @return description
     *         the description of the describable state
     */
    public String getDescription () {
        return myDescription;
    }

    /**
     * Returns the Stringified version of the Describable State
     * 
     * @return String
     *         a concatenated string of the name and description
     */
    @Override
    public String toString () {
        return getName() + getDescription();
    }

    /**
     * Returns the hashcode of the describable state for comparison
     * 
     * @return int
     *         the custom hashcode of the describable state
     */
    @Override
    public int hashCode () {
        final int prime = myHashingPrime; // Large prime helps prevent collisions
        int result = 1;
        result = prime * result + ((myDescription == null) ? 0 : myDescription.hashCode());
        result = prime * result + ((myName == null) ? 0 : myName.hashCode());
        return result;
    }

    /**
     * Checks equality of describable states
     */
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
