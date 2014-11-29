package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Boundable;
import game_engine.visuals.AnimationPlayer;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Displayable;
import game_engine.visuals.NullAnimationSequence;
import game_engine.visuals.Spritesheet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


/**
 * A game element that is capable of being drawn. Combines a game element state
 * as well as an animation player. Essentially wraps a savable game element
 * state with a drawable wrapper to avoid JavaFX serialization problems.
 * 
 * @author Zach, Jonathan, Nishad, Rahul, John L., Michael D.
 *
 */
public class DrawableGameElement extends GameElement implements Displayable, Boundable {
    // TODO this must be removed...
    final static String SELECT_ARROW_URL = "resources/img/Red_Arrow_Down.png";
    private DrawableGameElementState drawableState;
    protected AnimationPlayer myAnimation;
    protected Group myDisplay;
    protected VBox myDisplayVBox;
    private Image mySelectedImage;
    private ImageView mySelectedImageView;
    private AnimationSequence myCurrentAnimation;

    /**
     * Create a drawable game element from the given state
     * 
     * @param state
     *        the state of the drawable element
     * @param actions the actions map for the game element (Created by a factory to be passed into
     *        the element)
     */
    public DrawableGameElement (DrawableGameElementState state,
                                Map<String, List<Evaluatable<?>>> actions,
                                ResourceBundle actionTypes) {
        super(state, actions, actionTypes);
        myCurrentAnimation = new NullAnimationSequence();
        drawableState = state;
        Spritesheet spritesheet = state.getSpritesheet();
        try {
            // TODO remove this. testing only
            myAnimation =

                    new AnimationPlayer(new Image(spritesheet.imageTag),
                                        spritesheet.frameDimensions, spritesheet.numCols);
        }
        catch (Exception e) {
            // System.out.println("No spritesheet set for game element. Testing?");
        }
        myDisplay = new Group();
        myDisplayVBox = new VBox(1);
        // TODO undo for testing only
        try {
            mySelectedImage = new Image(SELECT_ARROW_URL);
            mySelectedImageView = new ImageView(mySelectedImage);
            initializeDisplay();
        }
        catch (Exception e) {
            // do nothing
        }

    }

    /**
     * Update the drawable game element and its visual appearance.
     */
    @Override
    public void update () {
        super.update();
        myAnimation.setAnimation(myCurrentAnimation);
        myAnimation.update();
    }

    /**
     * Update the element's image location based on its x and y position
     */
    protected void updateImageLocation () {
        myDisplay.setLayoutX(getNumericalAttribute(StateTags.X_POS_STRING).doubleValue());
        myDisplay.setLayoutY(getNumericalAttribute(StateTags.Y_POS_STRING).doubleValue());
    }

    /**
     * Set the element's animation to a given sequence
     * 
     * @param animation
     *        the animation sequence to set
     */
    public void setAnimation (AnimationSequence animation) {
        myAnimation.setAnimation(animation);
    }

    /**
     * @return the animation that is currently being displayed by the element
     */
    public AnimationSequence getAnimation () {
        return myCurrentAnimation;
    }

    /**
     * Set the animation currently being played by the drawable game element to the animation with
     * the given name
     * 
     * @param animationName the name of the animation to set the current animation to
     */
    public void setAnimation (String animationName) {
        myCurrentAnimation = drawableState.getAnimation(animationName);
    }

    /**
     * Gets the node that is being displayed on the scene
     * 
     * @return The game element
     */
    @Override
    public Node getNode () {
        return myDisplay;
    }

    /**
     * Gets the VBox of stats of the game element
     * 
     * @return The VBox of the game element
     */
    public VBox getDisplayVBox () {
        return myDisplayVBox;
    }

    /**
     * Get the bound array of the object
     * 
     * @return the object's bounds
     */
    public double[] getBounds () {
        return drawableState.getBounds();
    }

    // TODO from here down, remove this crap
    // TODO: Fix. Move logic into group

    public Point2D getLocation () {
        // TODO move positions and fix
        Point2D p = new Point2D(getNode().getLayoutX(), getNode().getLayoutY());
        return p;
    }

    /**
     * Initializes the display for each game element
     */
    private void initializeDisplay () {
        myDisplay.getChildren().add(myDisplayVBox);
        myDisplayVBox.setAlignment(Pos.TOP_CENTER);
        myDisplayVBox.getChildren().add(mySelectedImageView);
        mySelectedImageView.setOpacity(0.0);
        myDisplayVBox.getChildren().add(myAnimation.getNode());
        myDisplay.setLayoutX(myState.myAttributes.getNumericalAttribute(StateTags.X_POS_STRING).doubleValue());
        myDisplay.setLayoutY(myState.myAttributes.getNumericalAttribute(StateTags.Y_POS_STRING).doubleValue());
        myDisplay.setTranslateX(-myAnimation.getDimension().getWidth() / 2);
        myDisplay.setTranslateY(-myAnimation.getDimension().getHeight() / 2);
    }
}
