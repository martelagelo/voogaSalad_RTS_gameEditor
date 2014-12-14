package engine.visuals.elementVisuals;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import model.state.gameelement.AttributeContainer;
import model.state.gameelement.StateTags;
import model.state.gameelement.traits.Updatable;
import engine.visuals.Displayable;
import engine.visuals.elementVisuals.animations.Animator;
import engine.visuals.elementVisuals.animations.NullAnimationSequence;
import engine.visuals.elementVisuals.widgets.attributeDisplays.AttributeDisplayer;

/**
 * 
 * @author Steve
 *
 */
public class Visualizer implements Updatable, Displayable {

    private Group visualRepresentation;
    private VBox myVBox;
    private List<AttributeDisplayer> myWidgets;
    private Animator myAnimator;
    private AttributeContainer attributesOfInterest;
    private SimpleBooleanProperty enabled;

    public Visualizer (Animator animator, AttributeContainer attributes) {
        enabled = new SimpleBooleanProperty();
        visualRepresentation = new Group();
        myVBox = new VBox(1);
        myVBox.setLayoutX(45);
        myVBox.setLayoutY(-20);
        myVBox.setAlignment(Pos.CENTER);
        visualRepresentation.getChildren().add(myVBox);
        myWidgets = new ArrayList<>();
        myAnimator = animator;
        myAnimator.registerNode(n -> visualRepresentation.getChildren().add(n));
        attributesOfInterest = attributes;
    }
    
    public void setAnimateEnableProperty(SimpleBooleanProperty e){
        enabled = e;
    }

    public void addWidget (AttributeDisplayer widget) {
        myWidgets.add(widget);
        widget.registerAsComponent(n -> myVBox.getChildren().add(n));
    }

    @Override
    public Node getNode () {
        return visualRepresentation;
    }
    
    public Point2D getNodeLocation () {
        return new Point2D(visualRepresentation.getLayoutX(), visualRepresentation.getLayoutY());
    }

    @Override
    public boolean update () {
        if(enabled.get())
            updateAnimator();
        updateWidgets();
        updateNodeLocation();
        return true;
    }
    
    public void initializeDisplay () {
    	double xPosition = attributesOfInterest.getNumericalAttribute(StateTags.X_POSITION.getValue()).doubleValue();
    	double yPosition = attributesOfInterest.getNumericalAttribute(StateTags.Y_POSITION.getValue()).doubleValue();
        getNode().setLayoutX(xPosition);
        getNode().setLayoutY(yPosition);
        getNode().setTranslateX(-myAnimator.getViewportSize().getWidth() / 2);
        getNode().setTranslateY(-myAnimator.getViewportSize().getHeight() / 2);
        myAnimator.setAnimation(new NullAnimationSequence());
        myAnimator.update();
    }

    private void updateNodeLocation() {
    	double xPos = attributesOfInterest.getNumericalAttribute(StateTags.X_POSITION.getValue()).doubleValue();
    	visualRepresentation.setLayoutX(xPos);
    	double yPos = attributesOfInterest.getNumericalAttribute(StateTags.Y_POSITION.getValue()).doubleValue();
    	visualRepresentation.setLayoutY(yPos);
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
