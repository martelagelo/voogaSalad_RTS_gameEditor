package engine.users;


/**
 * The representation of the actual player of the game.
 * 
 * 
 * @author Michael D.
 *
 */
public class HumanParticipant extends Participant{
	
	/**
     * 
     */
    private static final long serialVersionUID = 5825471747765735517L;

    public HumanParticipant(String playerColor, String name){
		super(playerColor, name);
		isAI = false;
	}
	
}
