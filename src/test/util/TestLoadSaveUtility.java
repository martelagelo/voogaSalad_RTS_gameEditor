package test.util;

import java.io.IOException;
import javafx.scene.image.Image;
import org.junit.Test;
import util.SaveLoadUtility;
import util.LoadSaveUtility;


/**
 * Test class to verify LoadSave Utility's method calls loading/saving images as
 * well as loading/saving GSON<->JSON.
 * 
 * @author Rahul
 *
 */
public class TestLoadSaveUtility {
    private SaveLoadUtility myLoadSaveUtility = new SaveLoadUtility();

    @Test
    public void testLoadSaveFunction () throws Exception {
        myLoadSaveUtility.save(new TestCampaign("Campaign 1", "Campaign 1 Description"),

        "resources" + SaveLoadUtility.FILE_SEPARATOR + "game" + SaveLoadUtility.FILE_SEPARATOR
                + "campaign");
        TestCampaign campaign = myLoadSaveUtility.<TestCampaign> loadResource(TestCampaign.class,
                "resources" + SaveLoadUtility.FILE_SEPARATOR + "game"
                        + SaveLoadUtility.FILE_SEPARATOR + "campaign");
        myLoadSaveUtility.loadImage("src" + SaveLoadUtility.FILE_SEPARATOR + "resources"
                + SaveLoadUtility.FILE_SEPARATOR + "img" + SaveLoadUtility.FILE_SEPARATOR
                + "exploBig.png");
        myLoadSaveUtility.saveImage(("src/resources/img/exploBig.png"), "resources"
                + SaveLoadUtility.FILE_SEPARATOR + "img" + SaveLoadUtility.FILE_SEPARATOR
                + "exploBigCopy.png");
        Image image = myLoadSaveUtility.loadImage("resources" + SaveLoadUtility.FILE_SEPARATOR
                + "img" + SaveLoadUtility.FILE_SEPARATOR + "exploBigCopy.png");
        System.out.println(image.getHeight());
        TestCampaign loadedCampaign = campaign;
        System.out.println(loadedCampaign.myDescription);

    }
}
