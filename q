[1mdiff --git a/src/gamemodel/SpriteImageLoader.java b/src/gamemodel/SpriteImageLoader.java[m
[1mindex ccde876..3fa327e 100644[m
[1m--- a/src/gamemodel/SpriteImageLoader.java[m
[1m+++ b/src/gamemodel/SpriteImageLoader.java[m
[36m@@ -4,28 +4,34 @@[m [mimport java.io.File;[m
 import java.io.FileFilter;[m
 import java.util.HashMap;[m
 import java.util.Map;[m
[32m+[m
 import javafx.scene.image.Image;[m
[32m+[m
 import org.apache.commons.io.filefilter.WildcardFileFilter;[m
[32m+[m
 import util.GameSaveLoadMediator;[m
 import util.SaveLoadUtility;[m
 [m
[31m-[m
 /**[m
  * [m
  * @author Rahul[m
  *[m
  */[m
 public class SpriteImageLoader {[m
[32m+[m[32m    private static final String DOT = ".";[m
[32m+[m[32m    private static final String RELATIVE_PATH_DELIMITER = "/";[m
     public static final String TAG_DELIMITER = "|";[m
     public static final String ESCAPE_SEQ = "\\";[m
     public static final String COLORMASKS = "colormasks";[m
     public static final String SPRITESHEETS = "spritesheets";[m
[32m+[m[32m    public static final String DEFAULT_COLOR = "BLUE";[m
[32m+[m
 [m
     public static Map<String, Image> loadTeamColorMasks (String imageTag) throws Exception {[m
         Map<String, Image> colorMasks = new HashMap<>();[m
         File directory = new File(getColorMasksLocation(imageTag));[m
[31m-        FileFilter fileFilter =[m
[31m-                new WildcardFileFilter(GameSaveLoadMediator.WILDCARD + GameSaveLoadMediator.PNG_EXT);[m
[32m+[m[32m        FileFilter fileFilter = new WildcardFileFilter(getImageName(imageTag)[m
[32m+[m[32m                + GameSaveLoadMediator.WILDCARD + GameSaveLoadMediator.PNG_EXT);[m
         // TODO: need to determine how to get the color from the file[m
         File[] files = directory.listFiles(fileFilter);[m
         if (files != null) {[m
[36m@@ -50,23 +56,33 @@[m [mpublic class SpriteImageLoader {[m
         // based on operating system[m
         String processedTag = processImageTag(imageTag);[m
         String colorMaskLocation = processedTag.replace(SPRITESHEETS, COLORMASKS);[m
[31m-        int colorMaskDirectory = colorMaskLocation.lastIndexOf("/");[m
[32m+[m[32m        int colorMaskDirectory = colorMaskLocation.lastIndexOf(RELATIVE_PATH_DELIMITER);[m
         // TODO make the return be a default location[m
         return (colorMaskDirectory > 0) ? colorMaskLocation.substring(0, colorMaskDirectory) : "";[m
     }[m
 [m
[31m-    private static String processImageTag (String tags) {[m
[32m+[m[32m    private static String processImageTag (String imageTag) {[m
         // Making a copy of the string as a precaution[m
[31m-        String copy = new String(tags);[m
[32m+[m[32m        String copy = new String(imageTag);[m
         // Regex limitation in strings[m
[31m-        return copy.replaceAll(ESCAPE_SEQ + TAG_DELIMITER, ESCAPE_SEQ + File.separator);[m
[32m+[m[32m        return copy.replaceAll(ESCAPE_SEQ + TAG_DELIMITER, ESCAPE_SEQ + RELATIVE_PATH_DELIMITER);[m
     }[m
 [m
     private static String getColor (String fileName) {[m
         // Color mask file of format archer_red.png[m
         int colorIndex = fileName.lastIndexOf("_");[m
[31m-        int fileExtensionIndex = fileName.lastIndexOf(".");[m
[32m+[m[32m        int fileExtensionIndex = fileName.lastIndexOf(DOT);[m
         String color = fileName.substring(colorIndex + 1, fileExtensionIndex);[m
[31m-        return (colorIndex < fileExtensionIndex) ? color : GameSaveLoadMediator.DEFAULT_COLOR;[m
[32m+[m[32m        return (colorIndex < fileExtensionIndex) ? color : DEFAULT_COLOR;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private static String getImageName (String imageTag) {[m
[32m+[m[32m        // TODO return default image name in case of bad image tag[m
[32m+[m[32m        String processedTag = processImageTag(imageTag);[m
[32m+[m[32m        int imageNameIndex = processedTag.lastIndexOf(RELATIVE_PATH_DELIMITER);[m
[32m+[m[32m        int fileDotIndex = processedTag.lastIndexOf(DOT);[m
[32m+[m[32m        return ((fileDotIndex > (imageNameIndex + 1)) && (imageNameIndex > 0)) ? processedTag[m
[32m+[m[32m                .substring(imageNameIndex + 1, fileDotIndex) : "";[m
[32m+[m
     }[m
 }[m
[1mdiff --git a/src/util/GameSaveLoadMediator.java b/src/util/GameSaveLoadMediator.java[m
[1mindex a448180..11b97b3 100644[m
[1m--- a/src/util/GameSaveLoadMediator.java[m
[1m+++ b/src/util/GameSaveLoadMediator.java[m
[36m@@ -30,7 +30,6 @@[m [mpublic class GameSaveLoadMediator {[m
     public static final String WILDCARD = "*";[m
     public static final String ANIMATOR_STATE = "animatorstate";[m
     public static final String GAME_DIRECTORY = "myGames";[m
[31m-    public static final String DEFAULT_COLOR = "blue";[m
     public static final String RESOURCES = "resources";[m
     public static final String PNG_EXT = ".png";[m
     public static final String JSON_EXT = ".json";[m
warning: LF will be replaced by CRLF in myGames/testGame/testGame.json.
The file will have its original line endings in your working directory.
