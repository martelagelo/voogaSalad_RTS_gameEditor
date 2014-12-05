package engine.users;


/**
 * The representation of the actual player of the game.
 * 
 * 
 * @author Michael D.
 *
 */
public class HumanParticipant extends Participant{
	
	public HumanParticipant(int playerID, String name){
		super(playerID, name);
		isAI = false;
	}
	
}
