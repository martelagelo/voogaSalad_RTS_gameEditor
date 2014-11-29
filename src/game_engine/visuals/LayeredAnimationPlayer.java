package game_engine.visuals;

import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LayeredAnimationPlayer {

	private List<AnimationSequence> myCurrentAnimations;
	private Dimension myTileSize;
	private int myNumCols;
	private List<ImageView> myDisplay;
	private List<Rectangle2D> myImageBounds;

	public LayeredAnimationPlayer(List<Image> spriteSheets, Dimension tileSize, int numCols) {
		myTileSize = tileSize;
		myNumCols = numCols;
		myDisplay = new ArrayList<ImageView>();
		myImageBounds = new ArrayList<Rectangle2D>();
		for (int i = 0; i < spriteSheets.size(); i++) {
			ImageView iv = myDisplay.get(i);
			iv = new ImageView(spriteSheets.get(i));
			iv.setViewport(new Rectangle2D(0, 0, tileSize.getWidth(),
					tileSize.getHeight()));
			Rectangle2D imageBound = myImageBounds.get(i);
			imageBound = getImageBounds(spriteSheets.get(i));
		}

	}

	private Rectangle2D getImageBounds (Image img) {
		return new Rectangle2D(0, 0, img.getWidth(), img.getHeight());
	}

	public void setAnimations (List<AnimationSequence> animations) {
		myCurrentAnimations = animations;
	}

	public void setNextAnimations (@NotNull List<AnimationSequence> animations) {
		for (int i = 0; i < animations.size(); i++) {
			myCurrentAnimations.get(i).setNextAnimation(animations.get(i));
		}
	}

	public List<ImageView> getNode() {
		return myDisplay;
	}

	public boolean update() {
		// If the current animation is finished, start the next one
		if (myCurrentAnimations.get(0).update()) {
			for(AnimationSequence AS: myCurrentAnimations) {
				AS = AS.getNextAnimation();
			}
		}
		// Get a viewport and make sure it fits
		Rectangle2D viewport = getViewport(myCurrentAnimations.get(0).getFrame());
		if (!myImageBounds.contains(viewport))
			return false;
		// Set the display viewport to the new viewport
		myDisplay.get(0).setViewport(viewport);
		return true;
	}

	private Rectangle2D getViewport (int frameNumber) {
		int colNumber = frameNumber / myNumCols; // TODO changed by John to
		// vertical traversal of
		// frames to match our spritesheets
		int rowNumber = frameNumber % myNumCols;
		return new Rectangle2D(colNumber * myTileSize.getWidth(), rowNumber
				* myTileSize.getHeight(),
				myTileSize.getWidth(),
				myTileSize.getHeight());
	}

	public Dimension getDimension () {
		return myTileSize;
	}

}
