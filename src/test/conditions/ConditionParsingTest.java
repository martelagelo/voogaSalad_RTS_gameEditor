package test.conditions;

import static org.junit.Assert.assertEquals;
import game_engine.gameRepresentation.evaluatables.ConditionParser;
import java.io.IOException;
import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;


/**
 * Test condition parsing and creation
 *
 * @author Zach
 *
 */
public class ConditionParsingTest {
    private ConditionParser myParser;
    // Used to make private methods public using reflection
    private Method[] myMethods;

    /**
     * Set up the parser, grab a reference to all of its methods and set them to be public
     * @throws InvalidRulesException 
     * @throws IOException 
     */
    @Before
    public void setUpParser () throws IOException {
        myParser = new ConditionParser();
        myMethods = myParser.getClass().getDeclaredMethods();
        for (Method method : myMethods) {
            method.setAccessible(true);
        }
    }

    /**
     * Get a method made public by reflection by inputting its name
     *
     * @param methodName
     * @return
     */
    private Method getMethod (String methodName) {
        for (Method method : myMethods) {
            if (methodName.equals(method.getName())) return method;
        }
        return null;
    }

    //@Test
    public void testInputStripping () throws Exception {
        String input = "Hello\n";
        String result = ((String) getMethod("stripInput").invoke(myParser, input));
        assertEquals("Hello", result);
        input = "Hello World";
        result = ((String) getMethod("stripInput").invoke(myParser, input));
        assertEquals(input, result);
    }

    //@Test
    public void testConditionParsing () throws Exception {
        // getMethod("parseCondition").invoke(myParser, "this||other");
    }
    
    @Test
    public void testConditionParsingTokensFlat () throws IOException {
        String command = "\\subtractAssign($this.string[xPosition], $this.number[xVelocity]);";
        String[] tokens_string = { "function_name", "opening_parentheses",
                            "game_variable", "comma", "game_variable",
                            "closing_parentheses", "semicolon" };
        testTokens(command, tokens_string);
    }
    @Test
    public void testConditionParsingTokensNested () throws IOException {
        String command =
                "\\ifThen("
                        + "\\collides($this.self, $other.self),"
                        + "\\and("
                            + "\\subtractAssign($this.number[xPosition],1),"
                            + "\\subtractAssign($this.number[yPosition],\"hi\")"
                        + ")"
                + ");";
        String[] tokens_string = { "function_name", "opening_parentheses",
                                       "function_name", "opening_parentheses",
                                           "game_variable", "comma", "game_variable",
                                       "closing_parentheses", "comma",
                                       "function_name", "opening_parentheses",
                                           "function_name", "opening_parentheses",
                                               "game_variable", "comma", "number",
                                           "closing_parentheses", "comma",
                                           "function_name", "opening_parentheses",
                                               "game_variable", "comma", "string",
                                           "closing_parentheses",
                                       "closing_parentheses",
                                   "closing_parentheses", "semicolon"
        };
        testTokens(command, tokens_string);
    }
    public void testTokens(String command, String[] expected) throws IOException {
//        List<IToken> tokens = myParser.tokenize(command);
//        for (int i = 0; i < expected.length; i++) {
//            assertEquals(expected[i], tokens.get(i).label());
//        }
    }
}
