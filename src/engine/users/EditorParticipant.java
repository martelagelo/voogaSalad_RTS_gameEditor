package engine.users;

public class EditorParticipant extends Participant {

    /**
     * 
     */
    private static final long serialVersionUID = 3561060335779656127L;
    
    public static final String EDITOR_NAME = "EDITOR";

    // TODO: color set to empty string for compilation
    public EditorParticipant () {
        super("", EDITOR_NAME);
        isAI = false;
    }

    /**
     * The editor controls all units on all teams
     */
    @Override
    public boolean checkSameTeam (String otherTeamColor) {
        return true;
    }
}
