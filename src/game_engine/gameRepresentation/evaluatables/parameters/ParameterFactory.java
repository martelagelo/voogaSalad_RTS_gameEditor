package game_engine.gameRepresentation.evaluatables.parameters;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.evaluatables.parameters.exceptions.BadParameterFormatException;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.stateManaging.GameElementManager;
import java.util.List;
import java.util.Map;
import distilled_slogo.parsing.ISyntaxNode;


/**
 * A factory for parameters that takes in a string representing a parameter and returns the
 * parameter associated with it
 * 
 * @author John
 *
 */
public class ParameterFactory {

    private GameElementManager myManager;
    private Map<String, Class<?>> myParameters;
    private Map<String, Class<?>> myObjectIdentifiers;

    public ParameterFactory (Map<String, Class<?>> parameters,
                             Map<String, Class<?>> objectIdentifiers,
                             GameElementManager manager) {
        myParameters = parameters;
        myObjectIdentifiers = objectIdentifiers;
        myManager = manager;
    }

    public Parameter<?> make (ISyntaxNode<Evaluatable<?>> currentNode, List<Evaluatable<?>> children) throws BadParameterFormatException {
        String parameterText = currentNode.token().text().trim();
        // I can haz sed(1) and cut(1)?
        parameterText = parameterText.replaceFirst("^\\$", "");
        parameterText = parameterText.replaceFirst("\\]$", "");
        String[] splitByPeriod = parameterText.split("\\.");
        String[] splitByOpeningBracket = splitByPeriod[1].split("\\[");
        String actorTag = splitByPeriod[0];
        String dataType = splitByOpeningBracket[0];
        String attributeTag = splitByOpeningBracket[1];
        return createParameter(actorTag, dataType, attributeTag);
    }

    /**
     * Given the three important attributes of a parameter, return the parameter associated with
     * these attributes
     * 
     * @param actorTag the type of actor this parameter is referencing
     * @param dataType the type of data this parameter will be interacting with
     * @param attributeTag the tag of the attribute referenced by this parameter
     * @return
     * @throws BadParameterFormatException
     */
    public Parameter<?> createParameter (String actorTag, String dataType, String attributeTag)
                                                                                            throws BadParameterFormatException {
        ObjectOfInterestIdentifier identifier = getObjectOfInterestIdentifier(actorTag);
        return makeParameter(identifier, dataType, attributeTag);
    }

    private Parameter<?> makeParameter (ObjectOfInterestIdentifier identifier,
                                    String dataType,
                                    String attributeTag) throws BadParameterFormatException {
        Class<?> parameterClass = getClassFromString(myParameters, dataType);

        try {
            return (Parameter<?>) parameterClass.getConstructor(String.class, GameElementManager.class,
                                                ObjectOfInterestIdentifier.class)
                    .newInstance(attributeTag, myManager, identifier);
        }
        catch (Exception e) {
            throw new BadParameterFormatException("data type unrecognized");
        }
    }

    private ObjectOfInterestIdentifier getObjectOfInterestIdentifier (String actorTag)
                                                                                      throws BadParameterFormatException {
        Class<?> identifierClass = getClassFromString(myObjectIdentifiers, actorTag);
        try {
            return (ObjectOfInterestIdentifier) identifierClass.newInstance();
        }
        catch (Exception e) {
            throw new BadParameterFormatException("actor tag unrecognized");
        }
    }

    private Class<?> getClassFromString (Map<String, Class<?>> classMap, String tagString) {
        Class<?> c = null;
        for (String regex : classMap.keySet()) {
            if (tagString.matches(regex)) {
                c = classMap.get(regex);
            }
        }
        return c;
    }

}
