package view;

import java.awt.Dimension;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import editor.wizards.Wizard;


public class WizardUtility {

    public static Wizard loadWizard (String filePath, Dimension d) {
        GUILoadStyleUtility util = GUILoadStyleUtility.getInstance();
        Wizard wiz = (Wizard) GUILoadStyleUtility.generateGUIPane(filePath);
        Scene myScene = new Scene((Parent) wiz.getRoot(), d.getWidth(), d.getHeight());
        util.addScene(myScene);
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.setScene(myScene);
        s.show();
        wiz.setStage(s);
        return wiz;
    }

}
