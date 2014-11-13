package test.util;

import java.io.IOException;
import org.junit.Test;
import util.LoadUtility;

/**
 * Test class to verify object creation from reading JSON files.
 * @author Rahul Harikrishnan
 *
 */
public class TestLoadUtility {
    private LoadUtility myLoadUtility = new LoadUtility();
    @Test
    public void testLoadFunction () throws IOException {
        TestDescribable campaign = myLoadUtility.loadResource("campaign.json", TestCampaign.class);
        System.out.println(campaign.myDescription);
    }
}
