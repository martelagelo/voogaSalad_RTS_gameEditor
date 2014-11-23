package game_engine.gameRepresentation.evaluatables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * A class used to take a String and return an Evaluatable object corresponding to that string's
 * content
 *
 * @author Zach
 *
 */
//TODO implement. This is a very rough class that needs a lot of work
public class ConditionParser {
    // TODO make work
    public final static String REGEX_LOCATION = "resources.properties.CommentRegex";
    private ResourceBundle myBundle;
    private Stack<String> stack1;
    private Stack<String> stack2;

    public ConditionParser () {
        myBundle = ResourceBundle.getBundle(REGEX_LOCATION);
        stack1 = new Stack<>();
        stack2 = new Stack<>();

    }

    private String stripInput (String input) {
        return input.replaceAll(myBundle.getString("strip"), "");
    }

    // TODO stripInput
    public Evaluatable parseCondition (String conditionString) {
        System.out.println(Pattern.matches(".*(" + myBundle.getString("operators") + ").*",
                                           conditionString));
        return null;

    }

    /**
     * A method used to remove empty strings from a list that result from malformed input
     *
     * @return the list with no empty strings
     */
    private List<String> sanitizeStringList (List<String> list) {
        return list.stream().filter(s -> s.length() > 0).collect(Collectors.toList());

    }

    /**
     * Split a string on a given regex, returning a sanitized list of the split stiring
     *
     * @param str the string to split
     * @param regex the regex to split it on
     * @return a List of the split components
     */
    private List<String> splitStringAndSanitize (String str, String regex) {
        return sanitizeStringList(new ArrayList<String>(Arrays.asList(str.split(regex))));
    }

    private void parseOperators (String input) {
        new ArrayList<>();

    }

}
