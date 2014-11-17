package game_engine.gameRepresentation.conditions;

import java.util.ResourceBundle;
import java.util.regex.Pattern;
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector.Matcher;

/**
 * A class used to take a String and return a ConditionObject corresponding to that string's content
 * 
 * @author Zach
 *
 */
public class ConditionParser {

    public final static String REGEX_LOCATION= "resources.properties.CommentRegex";
    private ResourceBundle myBundle;
    public ConditionParser () {
        myBundle = ResourceBundle.getBundle(REGEX_LOCATION);
        

    }
    private String[] stripAndSplitInput(String input){
        return input.replaceAll(myBundle.getString("strip"), " ").split(myBundle.getString("split"));
    }
    private boolean matchesPattern(String item,String regexTag){
        return Pattern.matches(myBundle.getString(regexTag),item);
        
    }
    public Condition parseCondition(String conditionString){
        //String input = stripInput(conditionString);
       // Pattern p = Pattern.compile(myBundle.getString("operator"));
        return null;
    }
    

}
