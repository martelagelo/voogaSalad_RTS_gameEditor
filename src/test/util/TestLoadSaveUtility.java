package test.util;

import javafx.scene.image.Image;
import org.junit.Test;
import util.SaveLoadUtility;


/**
 * Test class to verify LoadSave Utility's method calls loading/saving images as
 * well as loading/saving GSON<->JSON.
 * 
 * @author Rahul
 *
 */
public class TestLoadSaveUtility {

    @Test
    public void testLoadSaveFunction () throws Exception {
        SaveLoadUtility.save(new TestCampaign("Campaign 1", "Campaign 1 Description"),

        "resources" + SaveLoadUtility.FILE_SEPARATOR + "game" + SaveLoadUtility.FILE_SEPARATOR
                + "campaign");
        TestCampaign campaign = SaveLoadUtility.<TestCampaign> loadResource(TestCampaign.class,
                "resources" + SaveLoadUtility.FILE_SEPARATOR + "game"
                        + SaveLoadUtility.FILE_SEPARATOR + "campaign.json");
        SaveLoadUtility.loadImage("src" + SaveLoadUtility.FILE_SEPARATOR + "resources"
                + SaveLoadUtility.FILE_SEPARATOR + "img" + SaveLoadUtility.FILE_SEPARATOR
                + "exploBig.png");
        SaveLoadUtility.saveImage(("src/resources/img/exploBig.png"), "resources"
                + SaveLoadUtility.FILE_SEPARATOR + "img" + SaveLoadUtility.FILE_SEPARATOR
                + "explo Big Copy.png");
        Image image = SaveLoadUtility.loadImage("resources" + SaveLoadUtility.FILE_SEPARATOR
                + "img" + SaveLoadUtility.FILE_SEPARATOR + "exploBigCopy.png");
        System.out.println(image.getHeight());
        TestCampaign loadedCampaign = campaign;
        System.out.println(loadedCampaign.myDescription);

    }
}
