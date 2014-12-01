package model.state.gameelement;

/**
 * This is the manner in which all basic attributes will be encoded in a GameElement. Essentially is
 * a data wrapper around an attribute with a given name and given data. Intentionally has very
 * little functionality and is merely a data object.
 * 
 * @author Steve, Zach
 *
 * @param <T> - the type of attribute that is being encoded.
 */
public class Attribute<T> {

    private T myData;
    private String myName;

    /**
     * Create an attribute
     * 
     * @param name the attribute's name
     * @param data the data of the attribute
     */
    public Attribute (String name, T data) {
        myData = data;
        myName = name;
    }

    /**
     * Returns the name of the attribute
     * @return myName
     *         the name of the attribute
     */
    public String getName () {
        return myName;
    }

    /**
     * Returns the data of the attribute
     * 
     * @return myData
     *         the data of the attribute
     */
    public T getData () {
        return myData;
    }

    /**
     * Sets the data of the attribute
     * 
     * @param newData
     *        the data of the attribute to be set to
     */
    public void setData (T newData) {

        this.myData = newData;
    }

    /**
     * Returns the string of the attribute
     * 
     * @return String
     *         a concatenated string of the name and data
     *         of the attribute
     */
    @Override
    public String toString () {
        return myName + " : " + myData.toString();
    }

    /**
     * Returns true if the object's hashcodes are
     * the same
     * 
     * @return boolean
     *         true if the object is the same
     *         false if the object is not the same
     */
    @Override
    public boolean equals (Object arg) {
        if (!(arg instanceof Attribute)) return false;
        return this.hashCode() == arg.hashCode();
    }

    /**
     * Returns the hashcode of the attribute's name
     * 
     * @return int
     *         the hashcode of the attribute's name
     */
    @Override
    public int hashCode () {
        return myName.hashCode();
    }

}
