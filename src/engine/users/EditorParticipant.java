package engine.users;

public class EditorParticipant extends Participant {

    public static final String EDITOR_NAME = "EDITOR";

    public EditorParticipant () {
        super(0, EDITOR_NAME);
        isAI = false;
    }

    /**
     * The editor controls all units on all teams
     */
    @Override
    public boolean checkSameTeam (double otherTeamID) {
        return true;
    }
}
