package test.conditions;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import game_engine.gameRepresentation.conditions.ConditionParser;
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
     */
    @Before
    public void setUpParser () {
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

    @Test
    public void testInputStripping () throws Exception {
        String input = "Hello\n";
        String[] result = ((String[]) getMethod("stripAndSplitInput").invoke(myParser, input));
        assertEquals("Hello", result[0]);
        assertEquals(1, result.length);
        input = "Hello World";
        result = ((String[]) getMethod("stripAndSplitInput").invoke(myParser, input));
        assertEquals("Hello", result[0]);
        assertEquals(2, result.length);
        input = "this.property(typo)";
        String[] desiredResult = { "this.property", "typo" };
        result = ((String[]) getMethod("stripAndSplitInput").invoke(myParser, input));
        assertArrayEquals(desiredResult, result);
    }

    @Test
    public void testRegexMatching () throws Exception {
        // Test the operators
        String input = "||";
        assertEquals(true, (boolean) getMethod("matchesPattern")
                     .invoke(myParser, input, "operator"));
        input = "&&";
        assertEquals(true, (boolean) getMethod("matchesPattern")
                     .invoke(myParser, input, "operator"));
        input = "bob..";
        assertEquals(false,
                     (boolean) getMethod("matchesPattern").invoke(myParser, input, "operator"));
        // Test the evaluators
        String[] evaluators = { ">", "<", ">=", "<=", "==" };
        for (String evaluator : evaluators) {
            assertEquals(true,
                         (boolean) getMethod("matchesPattern").invoke(myParser, evaluator,
                                 "evaluators"));
        }
        // Test for numbers
        String[] evaluators2 = { "66", "100.01", "100.50000", "500" };
        for (String evaluator : evaluators2) {
            assertEquals(true,
                         (boolean) getMethod("matchesPattern").invoke(myParser, evaluator,
                                 "numbers"));
        }
        // Test for object-property pairings
        String[] objectPropertyEvaluators = { "object1.properties", "object.property", "a.d" };
        for (String evaluator : objectPropertyEvaluators) {
            assertEquals(true,
                         (boolean) getMethod("matchesPattern").invoke(myParser, evaluator,
                                 "elementproperty"));
        }
    }

    @Test
    public void testConditionParsing () {

    }
}
