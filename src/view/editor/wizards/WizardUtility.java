package view.editor.wizards;

import java.awt.Dimension;
import view.GUILoadStyleUtility;
import view.gui.GUIPanePath;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class WizardUtility {

    public static Wizard loadWizard (GUIPanePath path, Dimension d) {
        GUILoadStyleUtility util = GUILoadStyleUtility.getInstance();
        Wizard wiz = (Wizard) GUILoadStyleUtility.generateGUIPane(path.getFilePath());
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
