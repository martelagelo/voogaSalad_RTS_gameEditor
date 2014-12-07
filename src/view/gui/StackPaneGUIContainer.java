package view.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class StackPaneGUIContainer extends GUIContainer {

    @FXML
    protected StackPane stackPane;
    @FXML
    protected Button sizedButton;
    
    protected void setFront (Node child) {
        stackPane.getChildren().remove(sizedButton);
        stackPane.getChildren().add(sizedButton);
        stackPane.getChildren().remove(child);
        stackPane.getChildren().add(child);
    }
    
    protected void bindPaneSize(Pane pane) {
        pane.prefHeightProperty().bind(sizedButton.heightProperty());
        pane.prefWidthProperty().bind(sizedButton.widthProperty());
    }

}
