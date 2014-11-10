package game_editor;

/**
 * 
 * @author Joshua Miller
 *
 * An abstract class which gives an object a name
 * and a description and the corresponding methods
 * to interact with them
 */
public abstract class Describable {
	
	private String myName;
	private String myDescription;
	
	public Describable (String name, String desc) {
		this.myName = name;
		this.myDescription = desc;
	}
	
	/**
	 * A method to return the name of a describable
	 *
	 * @return myName a String of the name of the describable
	 */
	public String getName () {
		return myName;
	}
	
	/**
	 * A method to return the description of a describable
	 *
	 * @return myName a String of the description of the describable
	 */
	public String getDescription () {
		return myDescription;
	}

}
