package game_engine.gameRepresentation.evaluatables.evaluators;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import distilled_slogo.parsing.ISyntaxNode;

public class EvaluatorFactory {
    Map<String, Class<?>> myEvaluators;
    
    public EvaluatorFactory (Map<String, Class<?>> evaluators) {
        myEvaluators = evaluators;
    }

    public Evaluator<?,?,?> make (ISyntaxNode<Evaluatable<?>> currentNode, List<Evaluatable<?>> children) throws EvaluatorCreationException {
        String commandName = currentNode.token().text().trim();
        commandName = commandName.replaceFirst("^\\", "");
        Evaluator<?,?,?> evaluator;
        if (myEvaluators.containsKey(commandName)) {
            return makeEvaluatorWithArguments(currentNode, myEvaluators.get(commandName), children);
        }
        else {
            return makeEvaluatorWithArguments(currentNode, ResultEvaluator.class, children);
        }
    }

    private Evaluator<?,?,?> makeEvaluatorWithArguments(ISyntaxNode<Evaluatable<?>> currentNode, Class<?> evaluator, List<Evaluatable<?>> children) throws EvaluatorCreationException {
        try {
            Constructor<?> constructor = evaluator.getConstructor(String.class, Evaluatable.class, Evaluatable.class);
            Evaluatable<?> lastChild = null;
            if (children.size() > 1) {
                lastChild = children.get(1);
            }
            return (Evaluator<?,?,?>) constructor.newInstance(currentNode.token().text(), children.get(0), lastChild);
        }
        catch (IllegalAccessException | NoSuchMethodException | SecurityException | InstantiationException | IllegalArgumentException | InvocationTargetException e) {
            throw new EvaluatorCreationException(e.getMessage());
        }
    }
}
