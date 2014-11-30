package game_engine.gameRepresentation.evaluatables;

import game_engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import game_engine.gameRepresentation.evaluatables.evaluators.exceptions.EvaluatorCreationException;
import game_engine.gameRepresentation.evaluatables.parameters.Parameter;
import game_engine.gameRepresentation.evaluatables.parameters.ParameterFactory;
import game_engine.gameRepresentation.evaluatables.parameters.exceptions.BadParameterFormatException;
import game_engine.stateManaging.GameElementManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import distilled_slogo.parsing.IOperationFactory;
import distilled_slogo.parsing.ISyntaxNode;
import distilled_slogo.util.ExternalFileLoader;
import distilled_slogo.util.FileLoader;

public class EvaluatableFactory implements IOperationFactory<Evaluatable<?>>{
    private static final String evaluatorsPackage =
            "game_engine.gameRepresentation.evaluatables.evaluators";
    private static final String parametersPackage =
            "game_engine.gameRepresentation.evaluatables.parameters";
    private static final String objectIdentifiersPackage =
            "game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers";
    private static final String evaluatableListPath = "./resources/evaluatables.json";
    private ParameterFactory myParameterFactory;
    private EvaluatorFactory myEvaluatorFactory;
    private Map<String, Class<?>> myEvaluators;
    private Map<String, Class<?>> myParameters;
    private Map<String, Class<?>> myObjectIdentifiers;
    private GameElementManager myManager = null;

    /**
     * Create a new factory that makes all evaluatables. You <b>MUST</b> set the game
     * manager manually using setManager() before using any methods.
     * 
     * @throws IOException If the path to the evaluatables list is invalid
     * @throws JSONException 
     * @throws ClassNotFoundException 
     */
    public EvaluatableFactory() throws IOException, ClassNotFoundException, JSONException {
        myEvaluators = new HashMap<>();
        myParameters = new HashMap<>();
        myObjectIdentifiers = new HashMap<>();
        String propertiesString = new ExternalFileLoader().loadFile(evaluatableListPath);
        JSONObject propertiesJSON = new JSONObject(propertiesString);
        populate("evaluators", evaluatorsPackage, myEvaluators, propertiesJSON);
        populate("parameters", parametersPackage, myParameters, propertiesJSON);
        populate("identifiers", objectIdentifiersPackage, myObjectIdentifiers, propertiesJSON);
        myParameterFactory = new ParameterFactory(myParameters, myObjectIdentifiers, myManager);
        myEvaluatorFactory = new EvaluatorFactory(myEvaluators);
    }

    private void populate(String type, String evaluatorPackage, Map<String, Class<?>> mappings,
                         JSONObject propertiesJSON)
                                 throws ClassNotFoundException, JSONException{
        JSONObject evaluatorsJSON = propertiesJSON.getJSONObject(type);
        for (String key: evaluatorsJSON.keySet()){
            mappings.put(key, Class.forName(evaluatorPackage + "." + evaluatorsJSON.getString(key)));
        }
    }

    // commissar is not in the dictionary! those capitalist dogs!
    /**
     * Set the manager of this factory
     * 
     * @param manager The <del>commissar</del> game manager associated with this factory
     * @return The newly updated EvaluatableFactory
     */
    public EvaluatableFactory setManager(GameElementManager manager) {
        myManager = manager;
        return this;
    }

    @Override
    public Evaluatable<?> makeOperation (ISyntaxNode<Evaluatable<?>> currentNode) {
        List<Evaluatable<?>> children = new ArrayList<>();
        for (ISyntaxNode<Evaluatable<?>> child: currentNode.children()) {
            children.add(child.operation());
        }
        Evaluatable<?> operation = null;
        try {
            operation = makeEvaluatable(currentNode, children);
        }
        //FIXME: add a general exception to makeOperation
        catch (BadParameterFormatException | EvaluatorCreationException e) {
            e.printStackTrace();
        }
        return operation;
    }

    private Evaluatable<?> makeEvaluatable (ISyntaxNode<Evaluatable<?>> currentNode,
                                         List<Evaluatable<?>> children) throws BadParameterFormatException, EvaluatorCreationException {
        if (children.size() == 0) {
            return myParameterFactory.make(currentNode, children);
        }
        else {
            return myEvaluatorFactory.make(currentNode, children);
        }
    }

    //FIXME
    public Map<String, List<Evaluatable<?>>> generateEvaluatables (Map<String, List<String>> actions) {
        
        return null;
    }
}
