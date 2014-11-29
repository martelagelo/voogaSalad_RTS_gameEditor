package test.util;

/**
 * Abstract test class of which test game, campaign, and level classes extend.
 * Used to avoid duplication in code for GSON->JSON test.
 * 
 * @author Rahul
 *
 */
public abstract class TestDescribable {
    public String myName;
    public String myDescription;

    public TestDescribable (String name, String description) {
        myName = name;
        myDescription = description;
    }
}
