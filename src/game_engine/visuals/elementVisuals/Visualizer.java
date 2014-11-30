package game_engine.visuals.elementVisuals;

import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Updatable;
import game_engine.visuals.Displayable;
import game_engine.visuals.elementVisuals.animations.Animator;
import game_engine.visuals.elementVisuals.widgets.AttributeDisplayer;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;

public class Visualizer implements Updatable, Displayable {
    
    private Group visualRepresentation;
    private List<AttributeDisplayer> myWidgets;
    private Animator myAnimator;
    
    public Visualizer (Animator animator){
        visualRepresentation = new Group();
        myWidgets = new ArrayList<>();
        myAnimator = animator;
        myAnimator.registerNode(n -> visualRepresentation.getChildren().add(n));
    }
    
    public void addWidget(AttributeDisplayer widget){
        myWidgets.add(widget);
        widget.registerNode(n -> visualRepresentation.getChildren().add(n));
    }

    @Override
    public Node getNode () {
        return visualRepresentation;
    }

    @Override
    public boolean update () {
        updateAnimator();
        updateWidgets();
        return true;
    }

    private void updateWidgets () {
        for (AttributeDisplayer widget : myWidgets){
            widget.update();
        }
    }

    private void updateAnimator () {
        myAnimator.update();
    }
}
