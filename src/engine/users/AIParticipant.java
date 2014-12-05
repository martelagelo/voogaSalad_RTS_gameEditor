package engine.users;

/**
 * The Participant that controls AI units
 * @author John Lorenz
 *
 */
public class AIParticipant extends Participant{

    public AIParticipant (int playerID, String name) {
        super(playerID, name);
        this.isAI = true;
    }
}