package game_engine.gameRepresentation.evaluatables.parameters;

import java.util.HashMap;
import java.util.Map;

import game_engine.gameRepresentation.evaluatables.parameters.exceptions.BadParameterFormatException;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.*;
import game_engine.stateManaging.GameElementManager;


/**
 * A factory for parameters that takes in a string representing a parameter and returns the
 * parameter associated with it
 * 
 * @author John
 *
 */
// TODO Fix up implementation. This is a very rough implementation of a factory pattern for the
// parameters. Methods need comments as this class is currently a work in progress
public class ParameterFactory {

    private GameElementManager myManager;

    public ParameterFactory (GameElementManager manager) {
        myManager = manager;
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
        // this.double("health")
        ObjectOfInterestIdentifier identifier = getObjectOfInterestIdentifier(actorTag);
        return getParameter(identifier, dataType, attributeTag);
    }

    private Parameter<?> getParameter (ObjectOfInterestIdentifier identifier,
                                    String dataType,
                                    String attributeTag) throws BadParameterFormatException {
        Map<String, Class<?>> map = new HashMap<>();

        // TODO build this map using reflection from a properties file
        map.put("double", NumericAttributeParameter.class);
        map.put("string", StringAttributeParameter.class);

        Class<?> c = getClassFromString(map, dataType);

        try {
            return (Parameter<?>) c.getConstructor(String.class, GameElementManager.class,
                                                ObjectOfInterestIdentifier.class)
                    .newInstance(attributeTag, myManager, identifier);
        }
        catch (Exception e) {
            throw new BadParameterFormatException("data type unrecognized");
        }
    }

    private ObjectOfInterestIdentifier getObjectOfInterestIdentifier (String actorTag)
                                                                                      throws BadParameterFormatException {
        Map<String, Class<?>> map = new HashMap<>();

        // TODO build this map using reflection from the properties file
        map.put("this", ActorObjectIdentifier.class);
        map.put("other", ActeeObjectIdentifier.class); // TODO this regex doesn't work
        map.put("^[A-Z].*", GlobalObjectIdentifier.class);

        Class<?> c = getClassFromString(map, actorTag);

        try {
            return (ObjectOfInterestIdentifier) c.newInstance();
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
