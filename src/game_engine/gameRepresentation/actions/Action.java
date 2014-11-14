package game_engine.gameRepresentation.actions;

import game_engine.gameRepresentation.GameElement;
import java.util.List;


public abstract class Action {

    public abstract void doAction (List<GameElement> elementList, String typeName);

}
