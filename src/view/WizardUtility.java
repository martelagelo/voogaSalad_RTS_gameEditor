package view;

import java.awt.Dimension;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import editor.wizards.Wizard;


public class WizardUtility {

    public static Wizard loadWizard (String filePath, Dimension d) {
        Wizard wiz = (Wizard) GUILoadStyleUtility.generateGUIPane(filePath);
        Scene myScene = new Scene((Parent) wiz.getRoot(), d.getWidth(), d.getHeight());
        Stage s = new Stage();
        s.setScene(myScene);
        s.show();   
        wiz.setStage(s);
        return wiz;
    }
    
    
}
