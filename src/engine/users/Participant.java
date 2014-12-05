package engine.users;

import java.util.HashMap;
import java.util.Map;


/**
 * The representation of the actual player of the game.
 * 
 * 
 * @author Michael D.
 *
 */
public abstract class Participant {

    protected int myPlayerID;
    protected String myName;
    protected Map<String, Integer> myScores;
    protected boolean isAI;

    public Participant (int playerID, String name) {
        myPlayerID = playerID;
        myName = name;
        myScores = new HashMap<String, Integer>();
    }

    /**
     * Gets the scores of this user
     * 
     * @return A map of score descriptions and values
     */
    public Map<String, Integer> getScores () {
        return myScores;
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

    /**
     * Sets the current scores of the user to a new set of scores
     * 
     * @param newScores The new scores
     */
    public void setScores (Map<String, Integer> newScores) {
        myScores = newScores;
    }

    /**
     * Sets a new name for the user
     * 
     * @param newName The new name
     */
    public void setName (String newName) {
        myName = newName;
    }

    /**
     * Adds a score to the list of scores of the user
     * 
     * @param level The level description of the new score
     * @param score The value of the new score
     */
    public void addScore (String level, int score) {
        myScores.put(level, score);
    }

    /**
     * Deletes a score from the score list
     * 
     * @param level The level description of the score the user wants to delete
     */
    public void deleteScore (String level) {
        myScores.remove(level);
    }

    /**
     * Changes the value of a particular score
     * 
     * @param level The particular level description
     * @param score The new score's value
     */
    public void changeScore (String level, int score) {
        myScores.put(level, score);
    }

    /**
     * checks whether the team ID passed into this one is the 
     * same as this Participant's team ID
     * @param otherTeamID
     * @return true if the team IDs are the same
     */
    public boolean checkSameTeam (double otherTeamID) {
        return myPlayerID == otherTeamID;
    }

}
