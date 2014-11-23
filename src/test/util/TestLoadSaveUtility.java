package test.util;

import java.io.IOException;
import javafx.scene.image.Image;
import org.junit.Test;
import util.SaveLoadUtility;
import util.LoadSaveUtility;


/**
<<<<<<< HEAD
 * Test class to verify LoadSave Utility's method calls loading/saving images as
 * well as loading/saving GSON<->JSON.
=======
 * Test class to verify LoadSave Utility's method calls loading/saving images as well as
 * loading/saving GSON<->JSON.
>>>>>>> shittymaster
 * 
 * @author Rahul
 *
 */
public class TestLoadSaveUtility {
    private SaveLoadUtility myLoadSaveUtility = new SaveLoadUtility();

    @Test
    public void testLoadSaveFunction () throws Exception {
       try {
           myLoadSaveUtility.save(new TestCampaign("Campaign 1", "Campaign 1 Description"),
       
                "resources" + SaveLoadUtility.FILE_SEPARATOR + "game"
                        + SaveLoadUtility.FILE_SEPARATOR + "campaign");
        TestCampaign campaign = myLoadSaveUtility.<TestCampaign> loadResource(TestCampaign.class,
                "resources" + SaveLoadUtility.FILE_SEPARATOR + "game"
                        + SaveLoadUtility.FILE_SEPARATOR + "campaign2.json");
        myLoadSaveUtility.loadImage("src" + SaveLoadUtility.FILE_SEPARATOR + "resources"
                + SaveLoadUtility.FILE_SEPARATOR + "img" + SaveLoadUtility.FILE_SEPARATOR
                + "exploBig.png");
        myLoadSaveUtility.saveImage(
                ("src/resources/img/exploBig.png"),
                "resources" + SaveLoadUtility.FILE_SEPARATOR + "img"
                        + SaveLoadUtility.FILE_SEPARATOR + "exploBigCopy.png");
       Image image =  myLoadSaveUtility.loadImage("resources" + SaveLoadUtility.FILE_SEPARATOR + "img"
                        + SaveLoadUtility.FILE_SEPARATOR + "exploBigCopy.png");
       System.out.println(image.getHeight());

        TestCampaign loadedCampaign = campaign;
        System.out.println(loadedCampaign.myDescription);
       }
       catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }
}
