package test.util;

import java.io.IOException;

import javafx.scene.image.Image;

import org.junit.Test;

import util.JSONable;
import util.LoadSaveUtility;

/**
 * Test class to verify object creation from reading JSON files.
 * @author Rahul
 *
 */
public class TestLoadSaveUtility {
    private LoadSaveUtility myLoadSaveUtility = new LoadSaveUtility();
    @Test
    public void testLoadFunction () throws IOException {
         myLoadSaveUtility.save(new TestCampaign("hola", "howdy"), "campaign.json");
        TestCampaign campaign = myLoadSaveUtility.<TestCampaign> loadResource("campaign.json");
      myLoadSaveUtility.loadImage("exploBig.png");
    //   myLoadSaveUtility.saveImage(new Image(getClass().getResourceAsStream("/resources/img/poop.png")), "exploBigCopy.png");
        TestCampaign foo = (TestCampaign) campaign;
        System.out.println(foo.myDescription);
    }
}
