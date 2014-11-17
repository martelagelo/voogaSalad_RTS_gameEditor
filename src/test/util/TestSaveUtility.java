package test.util;

import java.io.IOException;
import org.junit.Test;
import util.SaveUtility;

/**
 * Test class to verify JSON-rendered object output to the file.
 * @author Rahul Harikrishnan
 *
 */
public class TestSaveUtility {
    private SaveUtility mySaveUtility = new SaveUtility();
    @Test
    public void testSaveFunction () throws IOException {
        TestCampaign c = new TestCampaign("Game", "Description");
        c.addLevel(new TestLevel("Level 1", "Defeat evil minions"));
        c.addLevel(new TestLevel("Level 2", "Defeat second level minions"));
        c.addLevel(new TestLevel("Level 3", "Defeat boss"));
        mySaveUtility.save(c, "campaign.json");
        
        
    }

}
