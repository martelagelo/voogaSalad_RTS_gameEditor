package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.visuals.AnimationPlayer;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Displayable;
import game_engine.visuals.Spritesheet;
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
public class DrawableGameElement extends GameElement implements Displayable {

	final static String SELECT_ARROW_URL = "resources/img/Red_Arrow_Down.png";
	private DrawableGameElementState myState;
	protected AnimationPlayer myAnimation;
	protected Group myDisplay;
	protected VBox myDisplayVBox;
	private Image mySelectedImage;
	private ImageView mySelectedImageView;

	/**
	 * Create a drawable game element from the given state
	 * 
	 * @param element
	 *            the state of the drawable element
	 */
	public DrawableGameElement(DrawableGameElementState element) {
		super(element);
		myState = element;
		Spritesheet spritesheet = element.getSpritesheet();
		myAnimation = new AnimationPlayer(new Image(spritesheet.imageTag),
				spritesheet.frameDimensions, spritesheet.numCols);
		myDisplay = new Group();
		myDisplayVBox = new VBox(1);
		mySelectedImage = new Image(SELECT_ARROW_URL);
		mySelectedImageView = new ImageView(mySelectedImage);
		initializeDisplay();

	}

	/**
	 * Update the drawable game element and its visual appearance.
	 */
	@Override
	public void update() {
		super.update();
		// state.update();
		// Use polling because java.util.observable requires inheritance
		// and javafx.beans.observable isn't serializable.
		myAnimation.setAnimation(myState.getAnimation());
		myAnimation.update();
	}

	/**
	 * Set the element's animation to a given sequence
	 * 
	 * @param animation
	 *            the animation sequence to set
	 */
	public void setAnimation(AnimationSequence animation) {
		myAnimation.setAnimation(animation);
	}

	/**
	 * @return the current state of the element
	 */
	public DrawableGameElementState getState() {
		return myState;
	}

	/**
	 * Gets the node that is being displayed on the scene
	 * 
	 * @return The game element
	 */
	@Override
	public Node getNode() {
		return myDisplay;
	}

	/**
	 * Gets the VBox of stats of the game element
	 * 
	 * @return The VBox of the game element
	 */
	public VBox getDisplayVBox() {
		return myDisplayVBox;
	}

	/**
	 * Get the bound array of the object
	 * 
	 * @return the object's bounds
	 */
	public double[] getBounds() {
		return myState.getBounds();
	}

	// TODO: Fix. Move logic into group
	public void setLocation(Point2D location) {
		myDisplay.setLayoutX(location.getX());
		myState.setNumericalAttribute(DrawableGameElementState.X_POS_STRING,
				location.getX());
		myDisplay.setLayoutY(location.getY());
		myState.setNumericalAttribute(DrawableGameElementState.Y_POS_STRING,
				location.getY());
	}

	public Point2D getLocation() {
		Point2D p = new Point2D(getNode().getLayoutX(), getNode().getLayoutY());
		return p;
	}

	/**
	 * Initializes the display for each game element
	 */
	private void initializeDisplay() {
		myDisplay.getChildren().add(myDisplayVBox);
		myDisplayVBox.setAlignment(Pos.TOP_CENTER);
		myDisplayVBox.getChildren().add(mySelectedImageView);
		mySelectedImageView.setOpacity(0.0);
		myDisplayVBox.getChildren().add(myAnimation.getNode());
		myDisplay.setLayoutX(myState.getNumericalAttribute(
				DrawableGameElementState.X_POS_STRING).doubleValue());
		myDisplay.setLayoutY(myState.getNumericalAttribute(
				DrawableGameElementState.Y_POS_STRING).doubleValue());
		myDisplay.setTranslateX(-myAnimation.getDimension().getWidth() / 2);
		myDisplay.setTranslateY(-myAnimation.getDimension().getHeight() / 2);
	}
}
