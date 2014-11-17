package test.util;

import java.io.IOException;

import javafx.scene.image.Image;

import org.junit.Test;

import util.LoadSaveUtility;

/**
 * Test class to verify object creation from reading JSON files.
 * 
 * @author Rahul
 *
 */
public class TestLoadSaveUtility {
    private LoadSaveUtility myLoadSaveUtility = new LoadSaveUtility();

    @Test
    public void testLoadFunction () throws IOException {
        myLoadSaveUtility.save(new TestCampaign("Campaign 1", "Campaign 1 Description"), "src"
                + LoadSaveUtility.FILE_SEPARATOR + "resources" + LoadSaveUtility.FILE_SEPARATOR + "game"
                + LoadSaveUtility.FILE_SEPARATOR + "campaign.json");
        TestCampaign campaign = myLoadSaveUtility.<TestCampaign> loadResource(TestCampaign.class,
                "src" + LoadSaveUtility.FILE_SEPARATOR + "resources"
                        + LoadSaveUtility.FILE_SEPARATOR + "game" + LoadSaveUtility.FILE_SEPARATOR
                        + "campaign.json");
        myLoadSaveUtility.loadImage("exploBig.png");
        myLoadSaveUtility.saveImage(
                new Image(getClass().getResourceAsStream("/resources/img/exploBig.png")), "src"
                        + LoadSaveUtility.FILE_SEPARATOR + "resources"
                        + LoadSaveUtility.FILE_SEPARATOR + "img" + LoadSaveUtility.FILE_SEPARATOR
                        + "exploBigCopy.png");
        TestCampaign loadedCampaign = campaign;
        System.out.println(loadedCampaign.myDescription);
    }
}
