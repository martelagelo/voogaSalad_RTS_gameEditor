package model.state;

public class HighScore {
	
	private int myScore;
	private String myPlayerName;
	private String myAchievement;
	
	public HighScore (int score, String playerName, String achievement) {
		myScore = score;
		myPlayerName = playerName;
		myAchievement = achievement;
	}
	
	@Override
	public String toString () {
		return "Player " 
				+ myPlayerName
				+ " Achieved Victory by "
				+ myAchievement
				+ " with a score of "
				+ myScore;
	}

}
