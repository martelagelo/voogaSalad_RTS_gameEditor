package engine.users;

import model.state.gameelement.AttributeContainer;
import model.state.gameelement.StateTags;


/**
 * The representation of the actual player of the game.
 * 
 * 
 * @author Michael D.
 *
 */
public abstract class Participant {

    protected AttributeContainer attributes;
    protected String myTeamColor;
    protected String myName;
    protected boolean isAI;

    public Participant (String teamColor, String name) {
    	myTeamColor = teamColor;
        myName = name;
        attributes = new AttributeContainer();
        attributes.setTextualAttribute(StateTags.TEAM_COLOR, teamColor);
    }

    /**
     * Gets the name of the user
     * 
     * @return The name of the user
     */
    public String getName () {
        return myName;
    }

    /**
     * Gets whether or not the particular user is run by an AI
     * 
     * @return true when an AI
     */
    public boolean getIsAI () {
        return isAI;
    }

    public AttributeContainer getAttributes () {
        return attributes;
    }

    /**
     * checks whether the team ID passed into this one is the
     * same as this Participant's team ID
     * 
     * @param otherTeamID
     * @return true if the team IDs are the same
     */
    public boolean checkSameTeam (String otherTeamColor) {
        return myTeamColor.equals(otherTeamColor);
    }

}
