package game_engine;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Joshua Miller
 *
 * A Campaign class which contains all the necessary
 * information to build a campaign
 */
public class Campaign extends Describable{
	
	private List<Level> myLevels = new ArrayList<Level>();
	
	public Campaign (String name, String desc) {
		super(name, desc);
	}

}
