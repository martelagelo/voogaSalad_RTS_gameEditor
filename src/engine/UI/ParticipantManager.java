package engine.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import model.state.gameelement.StateTags;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.stateManaging.GameElementManager;
import engine.users.AIParticipant;
import engine.users.HumanParticipant;
import engine.users.Participant;
import engine.visuals.ScrollablePane;


/**
 * The manager that controls all of the Participants
 * TODO: actually implement some logic for the AI. For now, all the AI
 * is going to do is have all of it's units just run haphazardly
 * 
 * @author John Lorenz
 *
 */
public class ParticipantManager {
    private GameElementManager myElementManager;
    private List<AIParticipant> myAIUsers;
    private HumanParticipant humanUser;

    private ObjectProperty<String> player1Resources;

    public ParticipantManager (HumanParticipant user,
                               GameElementManager gameElementManager) {
        myElementManager = gameElementManager;
        myAIUsers = new ArrayList<>();
        humanUser = user;
        player1Resources = new SimpleObjectProperty<>();
    }

    /**
     * Determines AI actions to take on each frame. Functions as a finite state machine
     * in that each action the AI takes is based on the current situation of the game
     * 
     * @param allUnits
     */
    public void update (List<SelectableGameElement> allUnits) {

        player1Resources.set(humanUser.getAttributes().getNumericalAttribute("Resources")
                .intValue() +
                             "");

        /**
         * Goes through and for every team that does not correspond to the human player that exists,
         * creates a new AI team for it
         */
        for (SelectableGameElement element : allUnits) {
            int ID = (element.getNumericalAttribute(StateTags.TEAM_ID).intValue());
            boolean containsID =
                    myAIUsers.stream().filter(e -> e.checkSameTeam(ID))
                            .collect(Collectors.toList()).size() > 0;
            if (!containsID && ID!=1) {
                myAIUsers.add(new AIParticipant(ID, "AI" + ID));
            }
        }
        /**
         * Tells all units on all teams to move at complete random around the screen
         */
        for (AIParticipant p : myAIUsers) {
            for (SelectableGameElement e : allUnits
                    .stream()
                    .filter(e -> p.checkSameTeam(e.getNumericalAttribute(StateTags.TEAM_ID)
                            .doubleValue())).collect(Collectors.toList())) {
                Random r = new Random();
                if (r.nextDouble() > 0.99) {
                    e.addWaypoint(r.nextDouble() * ScrollablePane.FIELD_WIDTH,
                                 r.nextDouble() * ScrollablePane.FIELD_HEIGHT);
                }

            }
        }
    }

    /**
     * Used to adjust the participant's (by ID value) numerical attribute by the adjustment value
     * 
     * @param ID
     * @param tag
     * @param value the value to adjust the current numerical attribute's value by
     */
    public void adjustParticipantNumericalAttribute (int ID, String tag, double value) {
        for (Participant p : filterParticipants(ID)) {
            double currentVal = p.getAttributes().getNumericalAttribute(tag).doubleValue();
            p.getAttributes().setNumericalAttribute(tag, currentVal + value);
        }
    }

    /**
     * Used to reset the participant's (by ID value) numerical attribute. Overwrites current value
     * 
     * @param ID
     * @param tag
     * @param value
     */
    public void setParticipantNumericalAttribute (int ID, String tag, Number value) {
        for (Participant p : filterParticipants(ID)) {
            p.getAttributes().setNumericalAttribute(tag, value);
        }
    }

    /**
     * Used to reset participant's string attribute by ID value
     * 
     * @param ID
     * @param tag
     * @param value
     */
    public void setParticipantTextualAttribute (int ID, String tag, String value) {
        for (Participant p : filterParticipants(ID)) {
            p.getAttributes().setTextualAttribute(tag, value);
        }
    }

    /**
     * filters participants by ID. if ID = -1, returns all participants, both human and ai
     * 
     * @param ID
     * @return
     */
    private List<Participant> filterParticipants (int ID) {
        // java allows casting subclass -> superclass but not List<subclass> -> list<superclass>
        List<Participant> participants = myAIUsers.stream().collect(Collectors.toList());
        if (humanUser.checkSameTeam(ID)) participants.add(humanUser);
        if (ID == -1) return participants;
        return participants.stream().filter(e -> e.checkSameTeam(ID))
                .collect(Collectors.toList());
    }

    // TODO: for testing
    public ObservableValue<? extends String> getPlayerResourceProperty () {
        return player1Resources;
    }
}
