package vooga.engine.gameRepresentation;

import java.util.Map;
import java.util.function.BiFunction;


public class GameElement {

    private Map<BiFunction<GameElement, GameElement, Boolean>, BiFunction<GameElement, GameElement, Boolean>> ifThisThenThat;
}
