package engine.visuals;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.state.gameelement.StateTags;
import engine.UI.ParticipantManager;

/**
 * Class for displaying player statistics on the screen
 *
 * @author John Lorenz
 *
 */
public class StatisticsBox extends VBox implements VisualDisplay {
    // TODO: actually implement this properly
    public StatisticsBox (ParticipantManager manager) {
        super();
        setLayoutX(xLayout);
        setLayoutY(yLayout);
        HBox resourcesBox = new HBox();

        Text t = new Text();
        t.setFill(Color.WHITE);
        StringExpression s = Bindings.concat(StateTags.RESOURCES + ": ",
                manager.getPlayerResourceProperty());
        t.textProperty().bind(s);

        resourcesBox.getChildren().add(t);
        getChildren().add(resourcesBox);
    }

    @Override
    public Node getNode () {
        return this;
    }

}
