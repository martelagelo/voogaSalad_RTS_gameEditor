package test.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class to experiment with GSON->JSON conversion. Game is the top level of
 * which campaigns and levels are children.
 * 
 * @author Rahul
 *
 */
public class TestGame extends TestDescribable {
    private List<TestCampaign> myCampaigns;

    public TestGame (String name, String description) {
        super(name, description);
        myCampaigns = new ArrayList<>();
    }

    public void addCampaign (TestCampaign c) {
        myCampaigns.add(c);
    }
}
