package engine.users;


/**
 * The representation of the actual player of the game.
 * 
 * 
 * @author Michael D.
 *
 */
public class HumanParticipant extends Participant{
	
	public HumanParticipant(String playerColor, String name){
		super(playerColor, name);
		isAI = false;
	}
	
}
