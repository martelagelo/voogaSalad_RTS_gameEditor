package game_engine.gameRepresentation.gameElement;

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

}
