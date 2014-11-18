package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;

public class GameElement {

	private GameElementState myGameElementState;
	
	public GameElement(GameElementState GES) {
		myGameElementState = GES;
	}
	
	public GameElementState getGameElementState() {
		return myGameElementState;
	}
	
	
}
