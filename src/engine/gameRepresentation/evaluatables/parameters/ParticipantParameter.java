package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.users.Participant;

/**
 * A parameter that wraps around and returns a participant
 *
 * @author Zach
 *
 */
public class ParticipantParameter extends Parameter<Participant> {
    private Participant myParticipant;

    public ParticipantParameter (Participant participant) {
        super(Participant.class);
        myParticipant = participant;
    }

    @Override
    public Participant evaluate (ElementPair elements) {
        return myParticipant;
    }

    @Override
    protected boolean setEvaluatableValue (ElementPair elements, Participant value) {
        return false;
    };

}
