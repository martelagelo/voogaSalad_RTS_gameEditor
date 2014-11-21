package test.util;

import java.util.ArrayList;
import java.util.List;

import util.JSONable;

import com.google.gson.Gson;

/**
 * Campaign test class composed of levels used to test GSON->JSON conversion. 
 * @author Rahul
 *
 */
public class TestCampaign extends TestDescribable implements JSONable {
    private List<TestLevel> myLevels = new ArrayList<TestLevel>();

    public TestCampaign (String name, String description) {
        super(name, description);
    }

    public void addLevel (TestLevel l) {
        myLevels.add(l);
        Gson gson = new Gson();
    }

}
