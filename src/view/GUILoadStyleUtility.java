package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import view.dialog.DialogBoxUtility;
import view.gui.GUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


/**
 * A class for the generation and styling of GUIPanes based on FXML files
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 * @author Joshua Miller
 * 
 */
public class GUILoadStyleUtility {

    private static GUILoadStyleUtility myOnlyInstance;
    private List<String> myCSSFiles;
    private List<Scene> myScenes;

    /**
     * private constructor for the utility
     */
    private GUILoadStyleUtility () {
        myScenes = new ArrayList<>();
        myCSSFiles = new ArrayList<>();
    }

    /**
     * returns the single instance of the utility in the program
     * 
     * @return
     */
    public static synchronized GUILoadStyleUtility getInstance () {
        if (myOnlyInstance == null) {
            myOnlyInstance = new GUILoadStyleUtility();
        }
        return myOnlyInstance;
    }

    /**
     * sets the scene
     * 
     * @param scene
     */
    public void addScene (Scene ... scenes) {
        myScenes.addAll(new ArrayList<>(Arrays.asList(scenes)));
        attachStyles();
    }

    /**
     * static method to load an FXML file returns the GUIController associated
     * with the FXML file
     * 
     * @param filePath
     * @return
     */
    public static GUIController generateGUIPane (String filePath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUILoadStyleUtility.class.getResource(filePath));
        try {
            loader.load();
            GUIController controller = (GUIController) loader.getController();
            return controller;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * sets the style of the scene using
     * 
     * @param cssFiles
     */
    public void setStyle (String ... cssFiles) {
        myCSSFiles.clear();
        addStyle(cssFiles);
    }

    public void addStyle (String ... cssFiles) {
        for (String cssFile : cssFiles) {
            try {
                String css = getClass().getResource(cssFile).toExternalForm();
                myCSSFiles.add(css);
            }
            catch (Exception e) {
                DialogBoxUtility.createMessageDialog(e.getMessage());
                continue;
            }
        }
        attachStyles();
    }

    private void attachStyles () {
        myScenes.forEach( (scene) -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().addAll(myCSSFiles);
        });
    }

}
