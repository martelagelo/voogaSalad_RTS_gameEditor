package game_engine.gameRepresentation.evaluatables;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.json.JSONException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import distilled_slogo.util.GrammarRuleLoader;
import distilled_slogo.util.InvalidRulesException;
import distilled_slogo.parsing.IParser;
import distilled_slogo.parsing.InvalidGrammarRuleException;
import distilled_slogo.parsing.MalformedSyntaxException;
import distilled_slogo.parsing.Parser;
import distilled_slogo.parsing.ISyntaxNode;
import distilled_slogo.tokenization.IToken;
import distilled_slogo.tokenization.InvalidTokenRulesException;
import distilled_slogo.util.TokenRuleLoader;
import distilled_slogo.tokenization.Tokenizer;


/**
 * A class used to take a String and return an Evaluatable object corresponding to that string's
 * content
 *
 * @author Zach
 *
 */
// TODO implement. This is a very rough class that needs a lot of work
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
        TokenRuleLoader tokenLoader = null;
        Tokenizer tokenizer = null;
        List<IToken> tokens = null;
        try {
            tokenLoader = new TokenRuleLoader("./resources/token_rules.json");
            tokenizer = new Tokenizer(tokenLoader.getRules());
            tokens = tokenizer.tokenize(new StringReader(conditionString));
        }
        catch (IOException | InvalidTokenRulesException | ProcessingException e) {
            e.printStackTrace();
        }
        GrammarRuleLoader grammarLoader = null;
        IParser<String> parser = null;
        ISyntaxNode<String> tree = null;
        try {
            grammarLoader = new GrammarRuleLoader("./resources/parsing_rules.json");
            parser = new Parser<String>(grammarLoader.getRules());
            tree = parser.parse(tokens);
        }
        catch (IOException | InvalidRulesException | MalformedSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(tree);
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
