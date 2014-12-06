package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.users.Participant;

/**
 * A parameter that wraps a participant
 * @author Zach
 *
 */
public class ParticipantParameter extends Parameter<Participant> {
    private Participant myParticipant;

    public ParticipantParameter (Participant participant, String id) {
        super(Participant.class, id);
        myParticipant = participant;
    }

    @Override
    public Participant evaluate (ElementPair elements) {
        return myParticipant;
    }

    @Override
    public boolean setValue (ElementPair elements, Participant value) {
        return false;
    };

}
