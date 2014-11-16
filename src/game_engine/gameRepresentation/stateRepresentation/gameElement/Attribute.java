package game_engine.gameRepresentation.stateRepresentation.gameElement;

/**
 * This is the manner in which all basic attributes will be encoded in a GameElement.
 * 
 * @author Steve
 *
 * @param <T> - the type of attribute that is being encoded.
 */
public class Attribute<T> {

    private T myData;
    private String myName;

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
    
    public void setData(T newData) {
    	this.myData = newData;
    }

}
