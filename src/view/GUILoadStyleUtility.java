package view;

import java.io.IOException;
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

    private Scene myScene;

    /**
     * private constructor for the utility
     */
    private GUILoadStyleUtility () {
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
    public void setScene(Scene scene) {
        myScene = scene;

    }

    /**
     * static method to load an FXML file
     * returns the GUIController associated with the FXML file
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
            throw new RuntimeException(e);
        }
    }

    /**
     * sets the style of the scene using
     * 
     * @param cssFiles
     */
    public void setStyle (String ... cssFiles) {
        myScene.getStylesheets().clear();
        addStyle(cssFiles);
    }

    public void addStyle (String ... cssFiles) {
        try {
            for (String cssFile : cssFiles) {
                myScene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            }
        }
        catch (Exception e) {
            System.out.println("css file not found (or some other error)");
            e.printStackTrace();
        }
    }

}
