package test.conditions;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import game_engine.gameRepresentation.conditions.ConditionParser;
import java.lang.reflect.InvocationTargetException;
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
        String result = ((String) getMethod("stripInput").invoke(myParser, input));
        assertEquals("Hello", result);
        input = "Hello World";
        result = ((String) getMethod("stripInput").invoke(myParser, input));
        assertEquals(input, result);
    }

   

    @Test
    public void testConditionParsing () throws Exception {
        getMethod("parseCondition").invoke(myParser, "this||other");
    }
}
