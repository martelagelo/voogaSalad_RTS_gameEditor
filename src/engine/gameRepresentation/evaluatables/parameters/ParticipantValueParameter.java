package engine.gameRepresentation.evaluatables.parameters;

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
                                      String attributeName) {
        super(Number.class);
        myParticipants = participants;
        myAttributeTag = attributeName;

    }

    /**
     * Return the max of the value for all the participants
     */
    @Override
    public Number evaluate (ElementPair elements) {
        double maxValue = 0;
        for (Participant p : myParticipants) {
            double val = p.getAttributes().getNumericalAttribute(myAttributeTag).doubleValue();
            if (val > maxValue)
                maxValue = val;
        }
        return maxValue;
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
