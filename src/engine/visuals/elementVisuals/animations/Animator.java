package engine.visuals.elementVisuals.animations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.sprite.SpriteImageContainer;
import model.state.gameelement.AttributeContainer;
import model.state.gameelement.StateTags;
import model.state.gameelement.traits.Updatable;
import engine.Engine;
import engine.visuals.Dimension;

/**
 * An animation player that allows for the playing of animations using a given
 * spritesheet. This utility assumes all spritesheets are given with a
 * horizontally traversable sheet
 *
 * @author Zach, Steve, Rahul, John L.
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
     *            the image containing the spritesheet
     * @param tileSize
     *            a point2D containing the width(x) and height(y) of each frame
     *            in the spritesheet
     * @param numCols
     *            the number of columns across the spritesheet goes before
     *            moving to the next row
     *
     * @param animationEnabled
     *            whether to keep track of the actual animation of the object or
     *            replace with the default image. JavaFX has difficulty managing
     *            large sprite sheets
     */
    public Animator (SpriteImageContainer images, AnimatorState state, AttributeContainer attributes) {

        myImages = images;
        myImages.getSpritesheet().setClip(new ImageView(myImages.getSpritesheet().getImage()));

        myState = state;
        attributesOfInterest = attributes;
        mySprite = myImages.getSpritesheet();
        mySpritesheetBounds = getImageBounds(mySprite.getImage());
        int teamColor =
                attributesOfInterest.getNumericalAttribute(StateTags.TEAM_COLOR.getValue())
                        .intValue();

        setColorMasking(teamColor);
        mySpriteTeamOverlay = myImages.getColorMask(teamColor);
        mySpriteDisplay = new Group();
        mySpriteDisplay.getChildren().add(mySpriteTeamOverlay);
        mySpriteDisplay.getChildren().add(mySprite);
        currentDirection = new ArrayList<>();
        currentDirection.add(AnimationTag.FORWARD);
        myCurrentAnimation = new NullAnimationSequence();

    }

    /**
     * Sets the hue of the unit to match the team color. Use only if animations
     * are disabled
     *
     * @param teamColor
     */
    private void setColorMasking (int teamColor) {
        ColorInput mask = new ColorInput(
                                         0,
                                         0,
                                         myImages.getSpritesheet().getImage().getWidth(),
                                         myImages.getSpritesheet().getImage().getHeight(),
                                         Color.BLACK
                );
        try {
            mask.setPaint(Engine.colorFromInt(teamColor));
            Blend blush = new Blend(
                                    BlendMode.SRC_ATOP,
                                    null,
                                    mask
                    );
            myImages.getSpritesheet().setEffect(blush);
        } catch (Exception e) {
            e.printStackTrace();
            // fail silently
        }
        if (teamColor == 0) {
            mask.setPaint(Color.TRANSPARENT);
        }
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
        myCurrentAnimation.update();
        Rectangle2D viewport = getViewport(myCurrentAnimation.getFrame());
        if (!mySpritesheetBounds.contains(viewport)) {
            return false;
        }
        mySprite.setViewport(viewport);
        mySpriteTeamOverlay.setViewport(viewport);
        return true;
    }

    private void determineCorrectAnimation () {
        List<AnimationTag> animationTag = new ArrayList<>();
        determineAnimationDirection();
        animationTag.addAll(currentDirection);
        animationTag.add(determineAnimationType());
        AnimationSequence myNextAnimation = myState.getAnimationSequence(animationTag);
        if (!myNextAnimation.equals(myCurrentAnimation)) {
            myCurrentAnimation.reset();
        }
        myCurrentAnimation = myNextAnimation;
    }

    private void determineAnimationDirection () {
        double xVelocity = attributesOfInterest.getNumericalAttribute(
                StateTags.X_VELOCITY.getValue()).doubleValue();
        double yVelocity = attributesOfInterest.getNumericalAttribute(
                StateTags.Y_VELOCITY.getValue()).doubleValue();

        if (xVelocity != 0.0 || yVelocity != 0.0) {
            currentDirection.clear();
            if (yVelocity != 0.0) {
                currentDirection.add(yVelocity > 0 ? AnimationTag.FORWARD : AnimationTag.BACKWARD);
            }
            if (xVelocity != 0.0) {
                currentDirection.add(xVelocity > 0 ? AnimationTag.RIGHT : AnimationTag.LEFT);
            }
        }
    }

    // TODO is there a better way? make this dynamic? add an Evaluatatble?
    private AnimationTag determineAnimationType () {
        double xVelocity = attributesOfInterest.getNumericalAttribute(
                StateTags.X_VELOCITY.getValue()).doubleValue();
        double yVelocity = attributesOfInterest.getNumericalAttribute(
                StateTags.Y_VELOCITY.getValue()).doubleValue();
        double velocity = Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));
        String currentAction = attributesOfInterest.getTextualAttribute(StateTags.CURRENT_ACTION
                .getValue());
        boolean isAttacking = currentAction.equalsIgnoreCase("ATTACKING");
        boolean isDying = currentAction.equalsIgnoreCase("DYING");
        boolean isDecaying = currentAction.equalsIgnoreCase("DECAYING");

        if (velocity != 0) {
            return AnimationTag.MOVE;
        } else {
            if (isDecaying) {
                return AnimationTag.DECAY;
            } else if (isDying) {
                return AnimationTag.DIE;
            } else if (isAttacking) {
                return AnimationTag.ATTACK;
            } else {
                return AnimationTag.STAND;
            }
        }
    }

    /**
     * Creates and returns a viewport based on a frame number. Assumes vertical
     * traversal of frames
     */
    private Rectangle2D getViewport (int frameNumber) {
        int colNumber = frameNumber / myState.getNumRows();
        int rowNumber = frameNumber % myState.getNumRows();
        return new Rectangle2D(colNumber * myState.getViewportSize().getWidth(), rowNumber
                * myState.getViewportSize().getHeight(), myState.getViewportSize().getWidth(),
                myState.getViewportSize().getHeight());
    }

    public Dimension getViewportSize () {
        return myState.getViewportSize();
    }
}
