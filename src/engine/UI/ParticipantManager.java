package engine.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import model.state.gameelement.StateTags;
import engine.Engine;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.users.AIParticipant;
import engine.users.HumanParticipant;
import engine.users.Participant;

/**
 * The manager that controls all of the Participants TODO: actually implement
 * some logic for the AI. For now, all the AI is going to do is have all of it's
 * units just run haphazardly
 *
 * @author John Lorenz
 *
 */
public class ParticipantManager {
    
    private static final String AI_CONSTANT = "AI";
    
    private List<AIParticipant> myAIUsers;
    private HumanParticipant humanUser;

    private ObjectProperty<String> player1Resources;

    public ParticipantManager (HumanParticipant user) {
        myAIUsers = new ArrayList<>();
        humanUser = user;
        initializeAIParticipant();
        player1Resources = new SimpleObjectProperty<>();
    }

    /**
     * Get the current manager's users
     *
     * @return
     */
    public Participant getUser () {
        return humanUser;
    }

    /**
     * Get the current manager's AI
     *
     * @return
     */
    public List<Participant> getAI () {

        return new ArrayList<>(myAIUsers);
    }

    /**
     * Determines AI actions to take on each frame. Functions as a finite state
     * machine in that each action the AI takes is based on the current
     * situation of the game
     *
     * @param allUnits
     */
    public void update (List<SelectableGameElement> allUnits) {

        player1Resources.set(humanUser.getAttributes()
                .getNumericalAttribute(StateTags.RESOURCES.getValue()).intValue()
                + "");

        /**
         * Goes through and for every team that does not correspond to the human
         * player that exists, creates a new AI team for it
         */
        for (SelectableGameElement element : allUnits) {
            long teamColor = element.getNumericalAttribute(StateTags.TEAM_COLOR.getValue())
                    .longValue();
            boolean containsID = myAIUsers.stream().filter(e -> e.checkSameTeam(teamColor))
                    .collect(Collectors.toList()).size() > 0;
            if (!containsID && teamColor != Engine.DEFAULT_PLAYER_COLOR) {
                myAIUsers.add(new AIParticipant(teamColor, Long.toString(teamColor)));
            }
        }
        /**
         * Tells all units on all teams to move at complete random around the
         * screen
         */
        for (AIParticipant p : myAIUsers) {
            for (SelectableGameElement e : allUnits
                    .stream()
                    .filter(e -> p.checkSameTeam(e.getNumericalAttribute(StateTags.TEAM_COLOR
                            .getValue()))).collect(Collectors.toList())) {
                Random r = new Random();
                if (r.nextDouble() > 0.99) {
                    e.addWaypoint(e.getPosition().getX() * r.nextDouble() * 2, e.getPosition()
                            .getY() * r.nextDouble() * 2);
                }

            }
        }
    }

    private void initializeAIParticipant() {
        myAIUsers.add(new AIParticipant(0, AI_CONSTANT));
    }
    
    /**
     * Used to adjust the participant's (by ID value) numerical attribute by the
     * adjustment value
     *
     * @param teamColor
     * @param tag
     * @param value
     *            the value to adjust the current numerical attribute's value by
     */
    public void adjustParticipantNumericalAttribute (Number teamColor, String tag, double value) {
        for (Participant p : filterParticipants(teamColor)) {
            double currentVal = p.getAttributes().getNumericalAttribute(tag).doubleValue();
            p.getAttributes().setNumericalAttribute(tag, currentVal + value);
        }
    }

    /**
     * Used to reset the participant's (by ID value) numerical attribute.
     * Overwrites current value
     *
     * @param teamColor
     * @param tag
     * @param value
     */
    public void setParticipantNumericalAttribute (Number teamColor, String tag, Number value) {
        for (Participant p : filterParticipants(teamColor)) {
            p.getAttributes().setNumericalAttribute(tag, value);
        }
    }

    /**
     * Used to reset participant's string attribute by ID value
     *
     * @param teamColor
     * @param tag
     * @param value
     */
    public void setParticipantTextualAttribute (Number teamColor, String tag, String value) {
        for (Participant p : filterParticipants(teamColor)) {
            p.getAttributes().setTextualAttribute(tag, value);
        }
    }

    /**
     * filters participants by ID. if ID = -1, returns all participants, both
     * human and ai
     *
     * @param teamColor
     * @return
     */
    private List<Participant> filterParticipants (Number teamColor) {
        // java allows casting subclass -> superclass but not List<subclass> ->
        // list<superclass>
        List<Participant> participants = myAIUsers.stream().collect(Collectors.toList());
        if (humanUser.checkSameTeam(teamColor)) {
            participants.add(humanUser);
        }
        return participants.stream().filter(e -> e.checkSameTeam(teamColor))
                .collect(Collectors.toList());
    }

    // TODO: for testing
    public ObservableValue<? extends String> getPlayerResourceProperty () {
        return player1Resources;
    }
}
