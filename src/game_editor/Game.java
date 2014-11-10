package game_editor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Joshua Miller
 *
 * A Game class which contains all the necessary
 * information to build a game
 */
public class Game extends Describable{
	
	private List<Campaign> myCampaigns = new ArrayList<Campaign>();
	
	public Game(String name, String desc){
		super(name,desc);
	}

}
