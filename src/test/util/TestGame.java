package test.util;

import java.util.ArrayList;
import java.util.List;

public class TestGame extends TestDescribable {
    private List<TestCampaign> myCampaigns;
    public TestGame (String name, String description) {
        super(name, description);
        myCampaigns = new ArrayList<>();
    }
    
    
    public void addCampaign(TestCampaign c) {
        myCampaigns.add(c);
    }
}
