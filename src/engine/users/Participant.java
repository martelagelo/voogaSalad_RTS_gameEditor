package engine.users;

import java.io.Serializable;
import util.JSONable;
import model.state.gameelement.AttributeContainer;
import model.state.gameelement.StateTags;


/**
 * The representation of the actual player of the game.
 * 
 * 
 * @author Michael D.
 *
 */
public abstract class Participant implements JSONable, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9150235534545105031L;
    
    
    protected AttributeContainer attributes;
    protected String myTeamColor;
    protected String myName;
    protected boolean isAI;

    public Participant (String teamColor, String name) {
    	myTeamColor = teamColor;
        myName = name;
        attributes = new AttributeContainer();
        attributes.setTextualAttribute(StateTags.TEAM_COLOR.getValue(), teamColor);
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

    public String getTeamColor () {
        return myTeamColor;
    }

}
