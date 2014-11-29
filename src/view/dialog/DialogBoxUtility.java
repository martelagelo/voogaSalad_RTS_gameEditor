package view.dialog;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.GUILoadStyleUtility;

public class DialogBoxUtility {
    
    private static final Dimension SIZE = new Dimension(400, 200);
    
    public static DialogBoxController createDialogBox(String message, DialogChoice ... choices) {
        return createDialogBox(message, new ArrayList<>(Arrays.asList(choices)));
    }

    public static DialogBoxController createDialogBox(String message, Collection<DialogChoice> choices) {
        GUILoadStyleUtility util = GUILoadStyleUtility.getInstance();
        Stage stage = new Stage();
        DialogBoxController controller = (DialogBoxController) GUILoadStyleUtility.generateGUIPane("/view/dialog/DialogBox.fxml");
        Scene scene = new Scene((Parent) controller.getRoot(), SIZE.getWidth(), SIZE.getHeight());
        controller.attachInfo(stage, message, choices);
        util.addScene(scene);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return controller;
    }
    
}
