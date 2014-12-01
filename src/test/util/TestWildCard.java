package test.util;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import util.SaveLoadUtility;

/**
 * Test class for apache's wildcard in order to identify files within a
 * particular directory
 * 
 * @author Rahul
 *
 */
public class TestWildCard {

    private static String getColor (String fileName) {
        // Color mask file of format archer_red.png
        int colorIndex = fileName.lastIndexOf("_");
        int fileExtensionIndex = fileName.lastIndexOf(".");
        return (colorIndex < fileExtensionIndex) ? fileName.substring(colorIndex + 1,
                fileExtensionIndex) : "blue";
    }

    public static void main (String[] args) throws Exception {
        Map<String, Image> map = new HashMap<>();
        File directory = new File("testspritesheet");
        FileFilter fileFilter = new WildcardFileFilter("sample" + "*");
        File[] files = directory.listFiles(fileFilter);
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
            map.put(getColor(files[i].getName()), SaveLoadUtility.loadImage(files[i].getPath()));
        }

        for (String s : map.keySet()) {
            System.out.println("Team Color: " + s + " ;;; Height: " + map.get(s).getHeight());
        }

    }

}
