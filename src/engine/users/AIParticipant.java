package engine.users;

/**
 * The Participant that controls AI units
 *
 * @author John Lorenz
 *
 */
public class AIParticipant extends Participant {

    /**
     *
     */
    private static final long serialVersionUID = -2253692877077542505L;

    public AIParticipant (long playerColor, String name) {
        super(playerColor, "AI " + name);
        isAI = true;
    }
}