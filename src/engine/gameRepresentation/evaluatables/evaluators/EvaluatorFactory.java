package engine.gameRepresentation.evaluatables.evaluators;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;

public class EvaluatorFactory {
    public Evaluator<?, ?, ?> makeEvaluator(String evaluatableClass, Evaluatable<?>... parameters) throws ClassNotFoundException, EvaluatorCreationException {
        String currentPackage = this.getClass().getPackage().getName();
        Class<Evaluator<?,?,?>> evaluatable = (Class<Evaluator<?,?,?>>) Class.forName(currentPackage + "." + evaluatableClass);
        Constructor<Evaluator<?, ?, ?>> constructor = getConstructor(evaluatable);
        try {
            return constructor.newInstance("", parameters[0], parameters[1]);
        }
        catch (InstantiationException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            throw new EvaluatorCreationException("could not create " + evaluatableClass);
        }
    }
    private Constructor<Evaluator<?,?,?>> getConstructor(Class<Evaluator<?,?,?>> theClass) throws EvaluatorCreationException {
        try {
            return theClass.getConstructor(
                String.class, Evaluatable.class, Evaluatable.class);
        }
        catch (NoSuchMethodException | SecurityException e) {
            throw new EvaluatorCreationException("constructor for " + theClass + " not found");
        }
    }
}

// In form and moving how express and admirable! In action how like an Angel!