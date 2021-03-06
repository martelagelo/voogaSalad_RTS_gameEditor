package engine.visuals.elementVisuals;

import java.util.ArrayList;
import java.util.List;

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
 * The class in charge of handling the visuals for each selectable game element
 *
 * @author Steve, Michael D.
 *
 */
public class Visualizer implements Updatable, Displayable {

    private Group visualRepresentation;
    private VBox myVBox;
    private List<AttributeDisplayer> myWidgets;
    private Animator myAnimator;
    private AttributeContainer attributesOfInterest;

    /**
     * Constructor
     * 
     * @param animator
     *            An animation player that allows for the playing of animations
     *            using a given spritesheet.
     * @param attributes
     *            A list of attributes in a container
     */
    public Visualizer (Animator animator, AttributeContainer attributes) {
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

    /**
     * Adds a widget to a particular unit
     * 
     * @param widget
     *            The widget
     */
    public void addWidget (AttributeDisplayer widget) {
        myWidgets.add(widget);
        widget.registerAsComponent(n -> myVBox.getChildren().add(n));
    }

    /**
     * Gets the group that contains all of the physical elements of a game
     * element
     */
    @Override
    public Node getNode () {
        return visualRepresentation;
    }

    /**
     * Gets the current location of a drawable game element
     * 
     * @return The current point2D
     */
    public Point2D getNodeLocation () {
        return new Point2D(visualRepresentation.getLayoutX(), visualRepresentation.getLayoutY());
    }

    @Override
    public boolean update () {
        updateAnimator();
        updateWidgets();
        updateNodeLocation();
        return true;
    }

    /**
     * Initializes the display of the physical game element
     */
    public void initializeDisplay () {
        double xPosition = attributesOfInterest.getNumericalAttribute(
                StateTags.X_POSITION.getValue()).doubleValue();
        double yPosition = attributesOfInterest.getNumericalAttribute(
                StateTags.Y_POSITION.getValue()).doubleValue();
        getNode().setLayoutX(xPosition);
        getNode().setLayoutY(yPosition);
        getNode().setTranslateX(-myAnimator.getViewportSize().getWidth() / 2);
        getNode().setTranslateY(-myAnimator.getViewportSize().getHeight() / 2);
        myAnimator.setAnimation(new NullAnimationSequence());
        myAnimator.update();
    }

    private void updateNodeLocation () {
        double xPos = attributesOfInterest.getNumericalAttribute(StateTags.X_POSITION.getValue())
                .doubleValue();
        visualRepresentation.setLayoutX(xPos);
        double yPos = attributesOfInterest.getNumericalAttribute(StateTags.Y_POSITION.getValue())
                .doubleValue();
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
