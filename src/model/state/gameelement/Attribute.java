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

    public String getName () {
        return myName;
    }

    public T getData () {
        return myData;
    }

    public void setData (T newData) {

        this.myData = newData;
    }

    @Override
    public String toString () {
        return myName + " : " + myData.toString();
    }

    @Override
    public boolean equals (Object arg) {
        if (!(arg instanceof Attribute)) return false;
        return this.hashCode() == arg.hashCode();
    }

    @Override
    public int hashCode () {
        return myName.hashCode();
    }

}
