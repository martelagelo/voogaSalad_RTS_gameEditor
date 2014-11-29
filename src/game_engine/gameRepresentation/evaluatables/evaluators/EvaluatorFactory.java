package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import java.util.List;
import java.util.Map;
import distilled_slogo.parsing.ISyntaxNode;

public class EvaluatorFactory {

    public EvaluatorFactory (Map<String, Class> myEvaluators) {
        // TODO Auto-generated constructor stub
    }

    public Evaluator make (ISyntaxNode<Evaluatable> currentNode, List<Evaluatable> children) {
        return null;
    }

}
