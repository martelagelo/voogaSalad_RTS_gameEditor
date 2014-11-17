package test.util;

import util.JSONable;
import util.SaveUtility;

public class TestLevel implements JSONable {
    private String myName;
    private String myDescription;
    
    public TestLevel (String name, String description) {
        myName = name;
        myDescription = description;
    }
}
