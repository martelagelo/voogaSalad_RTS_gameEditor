package game_engine.gameRepresentation.conditions.evaluators.parameters;

import java.util.HashMap;
import java.util.Map;
import game_engine.gameRepresentation.conditions.evaluators.parameters.exceptions.BadParameterFormatException;
import game_engine.gameRepresentation.conditions.evaluators.parameters.objectIdentifiers.*;
import game_engine.stateManaging.GameElementManager;


/**
 * A factory for parameters that takes in a string representing a parameter and returns the
 * parameter associated with it
 * 
 * @author John
 *
 */
public class ParameterFactory {

    private GameElementManager myManager;

    public ParameterFactory (GameElementManager manager) {
        myManager = manager;
    }

    public Parameter createParameter (String actorTag, String dataType, String attributeTag) throws BadParameterFormatException {
        // this.double("health")
        ObjectOfInterestIdentifier identifier = getObjectOfInterestIdentifier(actorTag);
        return getParameter(identifier, dataType, attributeTag);
    }

    private Parameter getParameter (ObjectOfInterestIdentifier identifier, String dataType, String attributeTag) throws BadParameterFormatException {
        Map<String, Class<?>> map = new HashMap<>();
        
        // TODO build this map using reflection from a properties file
        map.put("double", NumericalAttributeParameter.class);
        map.put("string", StringAttributeParameter.class);
        
        Class<?> c = getClassFromString(map, dataType);

        try {
            return (Parameter) c.getConstructor(String.class, GameElementManager.class, 
                                                ObjectOfInterestIdentifier.class).newInstance(attributeTag, myManager, identifier);
        }
        catch (Exception e) {
            throw new BadParameterFormatException("data type unrecognized");
        }
    }

    private ObjectOfInterestIdentifier getObjectOfInterestIdentifier (String actorTag) throws BadParameterFormatException{
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
    
    public Class<?> getClassFromString(Map<String, Class<?>> classMap, String tagString){
        Class<?> c = null;
        for(String regex : classMap.keySet()){
            if(tagString.matches(regex)){
                c = classMap.get(regex);
            }
        }
        return c;
    }

}
