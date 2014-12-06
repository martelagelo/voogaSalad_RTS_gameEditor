package engine.gameRepresentation.evaluatables.parameters;

import java.util.Arrays;
import java.util.List;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.users.Participant;


/**
 * A wrapper for a participant's numerical attribute values
 * 
 * @author zach
 *
 */
public class ParticipantValueParameter extends Parameter<Number> {
    private List<Participant> myParticipants;
    private String myAttributeTag;

    public ParticipantValueParameter (List<Participant> participants,
                                      String attributeName,
                                      String id) {
        super(Number.class, id);
        myParticipants = participants;
        myAttributeTag = attributeName;

    }

    public ParticipantValueParameter (Participant participant, String attributeName, String id) {
        this(Arrays.asList(participant), attributeName, id);
    }

    /**
     * Return the average of the value for all the participants
     */
    @Override
    public Number evaluate (ElementPair elements) {
        double sum = 0;
        int numParticipants = myParticipants.size();
        for (Participant p : myParticipants) {
            sum += p.getAttributes().getNumericalAttribute(myAttributeTag).doubleValue();
        }
        return sum / numParticipants;
    }

    /**
     * Set the value to be a certain number for all participants
     */
    @Override
    public boolean setValue (ElementPair elements, Number value) {
        myParticipants.forEach(participant -> participant.getAttributes()
                .setNumericalAttribute(myAttributeTag, value));
        return true;
    }

}
