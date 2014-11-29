package game_engine.gameRepresentation.evaluatables;

import game_engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.EvaluatorFactory;
import game_engine.gameRepresentation.evaluatables.parameters.Parameter;
import game_engine.gameRepresentation.evaluatables.parameters.ParameterFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import distilled_slogo.parsing.IOperationFactory;
import distilled_slogo.parsing.ISyntaxNode;
import distilled_slogo.util.FileLoader;

public class EvaluatableFactory implements IOperationFactory<Evaluatable>{
    private static final String evaluatorsPackage =
            "game_engine.gameRepresentation.evaluatables.evaluators";
    private static final String parametersPackage =
            "game_engine.gameRepresentation.evaluatables.parameters";
    private static final String objectIdentifiersPackage =
            "game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers";
    private ParameterFactory myParameterFactory;
    private EvaluatorFactory myEvaluatorFactory;
    private Map<String, Class> myEvaluators;
    private Map<String, Class> myParameters;
    private Map<String, Class> myObjectIdentifiers;

    /**
     * Create a new factory that makes all evaluatables
     * 
     * @param evaluatableListPath The path to the list of evaluatables that can
     *                            be created
     * @param loader The loader used to load the path
     * @throws IOException If the path to the evaluatables list is invalid
     * @throws JSONException 
     * @throws ClassNotFoundException 
     */
    public EvaluatableFactory(String evaluatableListPath, FileLoader loader) throws IOException, ClassNotFoundException, JSONException {
        myEvaluators = new HashMap<>();
        myParameters = new HashMap<>();
        myObjectIdentifiers = new HashMap<>();
        String propertiesString = loader.loadFile(evaluatableListPath);
        JSONObject propertiesJSON = new JSONObject(propertiesString);
        populate("evaluators", myEvaluators, propertiesJSON);
        populate("parameters", myParameters, propertiesJSON);
        populate("identifiers", myObjectIdentifiers, propertiesJSON);
        myParameterFactory = new ParameterFactory(myParameters, myObjectIdentifiers);
        myEvaluatorFactory = new EvaluatorFactory(myEvaluators);
    }

    public void populate(String type, Map<String, Class> mappings,
                         JSONObject propertiesJSON)
                                 throws ClassNotFoundException, JSONException{
        JSONObject evaluatorsJSON = propertiesJSON.getJSONObject(type);
        for (String key: evaluatorsJSON.keySet()){
            mappings.put(key, Class.forName(evaluatorsJSON.getString(key)));
        }
    }

    @Override
    public Evaluatable makeOperation (ISyntaxNode<Evaluatable> currentNode) {
        List<Evaluatable> children = new ArrayList<>();
        for (ISyntaxNode<Evaluatable> child: currentNode.children()) {
            children.add(child.operation());
        }
        Evaluatable operation = makeEvaluatable(currentNode, children);
        return operation;
    }

    private Evaluatable makeEvaluatable (ISyntaxNode<Evaluatable> currentNode,
                                         List<Evaluatable> children) {
        if (children.size() == 0) {
            return myParameterFactory.make(currentNode, children);
        }
        else {
            return myEvaluatorFactory.make(currentNode, children);
        }
    }
}
