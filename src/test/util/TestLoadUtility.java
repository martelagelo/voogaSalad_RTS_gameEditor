package test.util;

import java.io.IOException;

import org.junit.Test;

import util.JSONable;
import util.LoadUtility;

/**
 * Test class to verify object creation from reading JSON files.
 * @author Rahul
 *
 */
public class TestLoadUtility {
    private LoadUtility myLoadUtility = new LoadUtility();
    @Test
    public void testLoadFunction () throws IOException {
        TestCampaign campaign = myLoadUtility.<TestCampaign> loadResource("campaign.json");
        TestCampaign foo = (TestCampaign) campaign;
        System.out.println(foo.myDescription);
    }
}
