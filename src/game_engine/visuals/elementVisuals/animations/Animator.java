package game_engine.visuals.elementVisuals.animations;

import game_engine.gameRepresentation.stateRepresentation.AnimationTag;
import game_engine.gameRepresentation.stateRepresentation.AnimatorState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.AttributeContainer;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Updatable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * An animation player that allows for the playing of animations using a given
 * spritesheet. This utility assumes all spritesheets are given with a
 * horizontally traversable sheet
 *
 * @author Zach, Steve
 *
 */
public class Animator implements Updatable {

    private SpriteImageContainer myImages;
    private AnimatorState myState;
    private AttributeContainer attributesOfInterest;

    private Group mySpriteDisplay;
    private ImageView mySprite;
    private ImageView mySpriteTeamOverlay;
    private Rectangle2D mySpritesheetBounds;

    private AnimationSequence myCurrentAnimation;
    private List<AnimationTag> currentDirection;

    /**
     * Initialize the player
     *
     * @param spriteSheet
     *        the image containing the spritesheet
     * @param tileSize
     *        a point2D containing the width(x) and height(y) of each frame
     *        in the spritesheet
     * @param numCols
     *        the number of columns across the spritesheet goes before
     *        moving to the next row
     */
    public Animator (SpriteImageContainer images, AnimatorState state, AttributeContainer attributes) {
        myImages = images;
        myState = state;
        attributesOfInterest = attributes;

        mySprite = new ImageView(myImages.getSpritesheet());
        String teamColor = attributesOfInterest.getTextualAttribute(StateTags.TEAM_COLOR);
        mySpriteTeamOverlay = new ImageView(myImages.getTeamColorSheet(teamColor));
        mySpriteDisplay.getChildren().add(mySprite);
        mySpriteDisplay.getChildren().add(mySpriteTeamOverlay);
        currentDirection = new ArrayList<>();
        currentDirection.add(AnimationTag.FORWARD);
    }

    public void registerNode (Consumer<Node> registerFunction) {
        registerFunction.accept(mySpriteDisplay);
    }

    private Rectangle2D getImageBounds (Image img) {
        return new Rectangle2D(0, 0, img.getWidth(), img.getHeight());

    }

    public void setAnimation (AnimationSequence animation) {
        myCurrentAnimation = animation;
    }

    /**
     * Update the animation. Increment the frame and move on to the next
     * animation if the current one is finished.
     */
    @Override
    public boolean update () {
        determineCorrectAnimation();
        Rectangle2D viewport = getViewport(myCurrentAnimation.getFrame());
        if (!mySpritesheetBounds.contains(viewport)) return false;
        mySprite.setViewport(viewport);
        mySpriteTeamOverlay.setViewport(viewport);
        return true;
    }

    private void determineCorrectAnimation () {
        List<AnimationTag> animationTag = new ArrayList<>();
        determineAnimationDirection();
        animationTag.addAll(currentDirection);
        animationTag.add(determineAnimationType());
    }

    private void determineAnimationDirection () {
        double xVelocity =
                attributesOfInterest.getNumericalAttribute(StateTags.X_VELOCITY).doubleValue();
        double yVelocity =
                attributesOfInterest.getNumericalAttribute(StateTags.Y_VELOCITY).doubleValue();
        if (xVelocity != 0.0 || yVelocity != 0.0) {
            currentDirection.clear();
            if (xVelocity != 0.0) {
                currentDirection.add(xVelocity > 0 ? AnimationTag.FORWARD : AnimationTag.BACKWARD);
            }
            if (yVelocity != 0.0) {
                currentDirection.add(xVelocity > 0 ? AnimationTag.RIGHT : AnimationTag.LEFT);
            }
        }
    }

    private AnimationTag determineAnimationType () {
        double xVelocity =
                attributesOfInterest.getNumericalAttribute(StateTags.X_VELOCITY).doubleValue();
        double yVelocity =
                attributesOfInterest.getNumericalAttribute(StateTags.Y_VELOCITY).doubleValue();
        double velocity = Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));
        String currentAction = attributesOfInterest.getTextualAttribute(StateTags.CURRENT_ACTION);
        boolean isAttacking = currentAction.equalsIgnoreCase("ATTACKING");
        boolean isDying = currentAction.equalsIgnoreCase("DYING");
        boolean isDecaying = currentAction.equalsIgnoreCase("DECAYING");

        if (velocity != 0) {
            return AnimationTag.MOVE;
        }
        else {
            if(isDecaying){
                return AnimationTag.DECAY;
            }
            else if (isDying){
                return AnimationTag.DIE;
            }
            else if (isAttacking) {
                return AnimationTag.ATTACK;
            }
            else {
                return AnimationTag.STAND;
            }
        }

    }

    /**
     * Creates and returns a viewport based on a frame number. Assumes
     * vertical traversal of frames
     */
    private Rectangle2D getViewport (int frameNumber) {
        int colNumber = frameNumber / myState.numCols;
        int rowNumber = frameNumber % myState.numCols;
        return new Rectangle2D(colNumber * myState.viewportSize.getWidth(),
                               rowNumber * myState.viewportSize.getHeight(),
                               myState.viewportSize.getWidth(), myState.viewportSize.getHeight());
    }
}
