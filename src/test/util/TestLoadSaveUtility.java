package test.util;

import java.io.IOException;
import javafx.scene.image.Image;
import org.junit.Test;
import util.LoadSaveUtility;


/**
 * Test class to verify LoadSave Utility's method calls loading/saving images as well as
 * loading/saving GSON<->JSON.
 * 
 * @author Rahul
 *
 */
public class TestLoadSaveUtility {
    private LoadSaveUtility myLoadSaveUtility = new LoadSaveUtility();

    @Test
    public void testLoadFunction () throws IOException {
        myLoadSaveUtility.save(new TestCampaign("Campaign 1", "Campaign 1 Description"),
                               "resources" + LoadSaveUtility.FILE_SEPARATOR + "game"
                                       + LoadSaveUtility.FILE_SEPARATOR + "campaign.json");
        TestCampaign campaign =
                myLoadSaveUtility
                        .<TestCampaign> loadResource(TestCampaign.class,
                                                     "resources" + LoadSaveUtility.FILE_SEPARATOR +
                                                             "game"
                                                             + LoadSaveUtility.FILE_SEPARATOR +
                                                             "campaign.json");
        myLoadSaveUtility.loadImage("src" + LoadSaveUtility.FILE_SEPARATOR + "resources"
                                    + LoadSaveUtility.FILE_SEPARATOR + "img" +
                                    LoadSaveUtility.FILE_SEPARATOR
                                    + "exploBig.png");
        String savedImage =
                myLoadSaveUtility
                        .saveImage(
                                   new Image(getClass()
                                           .getResourceAsStream("/resources/img/exploBig.png")),
                                   "resources" + LoadSaveUtility.FILE_SEPARATOR + "img"
                                           + LoadSaveUtility.FILE_SEPARATOR + "exploBigCopy.png");
        myLoadSaveUtility.loadImage(savedImage);
        TestCampaign loadedCampaign = campaign;
        System.out.println(loadedCampaign.myDescription);
    }
}
