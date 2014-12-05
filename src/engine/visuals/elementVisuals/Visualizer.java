package engine.visuals.elementVisuals;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import model.state.gameelement.traits.Updatable;
import engine.visuals.Displayable;
import engine.visuals.elementVisuals.animations.Animator;
import engine.visuals.elementVisuals.widgets.attributeDisplays.AttributeDisplayer;

/**
 * 
 * @author Steve
 *
 */
public class Visualizer implements Updatable, Displayable {

    private Group visualRepresentation;
    private List<AttributeDisplayer> myWidgets;
    private Animator myAnimator;

    public Visualizer (Animator animator) {
        visualRepresentation = new Group();
        myWidgets = new ArrayList<>();
        myAnimator = animator;
        myAnimator.registerNode(n -> visualRepresentation.getChildren().add(n));
    }

    public void addWidget (AttributeDisplayer widget) {
        myWidgets.add(widget);
        widget.registerAsComponent(n -> visualRepresentation.getChildren().add(n));
    }

    @Override
    public Node getNode () {
        return visualRepresentation;
    }

    @Override
    public boolean update () {
    	//System.out.println("Updating visualizer");
        updateAnimator();
        updateWidgets();
        return true;
    }

    private void updateWidgets () {
        for (AttributeDisplayer widget : myWidgets) {
            widget.update();
        }
    }

    private void updateAnimator () {
        myAnimator.update();
    }
}
