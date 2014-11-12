package vooga.engine.gameRepresentation.conditions;

import java.util.function.BiFunction;
import vooga.engine.gameRepresentation.GameElement;


public abstract class Condition implements BiFunction<GameElement, GameElement, Boolean> {

}
