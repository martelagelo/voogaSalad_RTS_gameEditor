package engine.visuals;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import engine.UI.ParticipantManager;

/**
 * Class for displaying player statistics on the screen
 * @author John Lorenz
 *
 */
public class StatisticsBox extends VBox{
    // TODO: actually implement this properly
    public StatisticsBox(double xLayout, double yLayout, ParticipantManager manager){
        super();
        this.setLayoutX(xLayout);
        this.setLayoutY(yLayout);
        HBox resourcesBox = new HBox();
        
        Text t = new Text();
        t.setFill(Color.BLACK);
        t.textProperty().bind(manager.getPlayerResourceProperty());
        
        resourcesBox.getChildren().add(t);
        this.getChildren().add(resourcesBox);
    }

}