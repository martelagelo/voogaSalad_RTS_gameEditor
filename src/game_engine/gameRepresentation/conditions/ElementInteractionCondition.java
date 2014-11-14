package game_engine.gameRepresentation.conditions;

import game_engine.gameRepresentation.GameElement;
import java.util.List;
import java.util.function.BiFunction;

public class ElementInteractionCondition extends Condition {

	private BiFunction<GameElement, GameElement, Boolean> function;

	@Override
	public Boolean evaluate(List<GameElement> parameters) {
		// TODO: Add exception return when error checking fails
		return function.apply(parameters.get(0), parameters.get(1));
	}

}
