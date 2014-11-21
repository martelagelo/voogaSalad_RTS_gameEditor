package test.util;

import util.JSONable;

/**
 * Test level class used to test GSON->JSON conversion.
 * @author Rahul
 *
 */
public class TestLevel implements JSONable {
    private String myName;
    private String myDescription;

    public TestLevel (String name, String description) {
        myName = name;
        myDescription = description;
    }
}
