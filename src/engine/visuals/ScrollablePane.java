// This entire file is part of my masterpiece.
// JOHN LORENZ

package engine.visuals;

import java.io.IOException;
import java.util.function.Consumer;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import engine.UI.InputManager;


/**
 * Basically the map. JavaFX Scene node that contains the eventHandlers
 * necessary to scroll the map and register clicks.
 *
 * @author John, Michael D.
 *
 */
public class ScrollablePane extends Pane {
    public static final double SLOW_SCROLL_BOUNDARY = 120;
    public static final double FAST_SCROLL_BOUNDARY = 75;
    public static final double FAST_SPEED = 15;
    public static final double SLOW_SPEED = 7;
    public static final Color SHAPE_COLOR = Color.RED;
    public static final double PEN_WIDTH = 2;

    private ScrollableBackground myBackground;
    private SelectionShape myCurrentSelectionShape;
    private InputManager myInputManager;
    private Group myRoot;

    private double myMapWidth;
    private double myMapHeight;

    /**
     * Creates a new ScrollableScene for the map.
     *
     * @param root
     *        the group of elements to add to the Scene initially. If no
     *        objects have been created yet to add, just add an empty new
     *        Group()
     * @param width
     *        the width of the map (ideally larger than the screen width)
     * @param height
     *        the height of the map (ideally larger than the screen height)
     * @throws IOException
     */
    public ScrollablePane (Group root,
                           Shape selectionShape,
                           double fieldWidth,
                           double fieldHeight,
                           Image background) {
        myMapWidth = fieldWidth;
        myMapHeight = fieldHeight;
        myRoot = root;

        getChildren().add(root);
        myBackground =
                new ScrollableBackground(fieldWidth, fieldHeight, this.widthProperty(),
                                         this.heightProperty(), background);
        BorderPane.setAlignment(myBackground, Pos.CENTER);

        root.getChildren().addAll(myBackground, myCurrentSelectionShape.getNode());
        initializeHandlers();
    }

    /**
     * gets the background map that objects actually get added to
     *
     * @return the background of the scrollable scene
     */
    public ScrollableBackground getScrollingBackground () {
        return myBackground;
    }

    /**
     * NOTE: this was refactored from what I originally wrote, I think it looks really long and ugly
     * as it is now but won't change it for sake of simplicity - John
     * initializes the handlers that respond to JavaFX events necessary for
     * scrolling and registering clicks/ drags
     */
    private void initializeHandlers () {
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle (KeyEvent event) {
                myInputManager.keyPressed(event);
            }
        });
        
        setOnMousePressed(e -> {
            myBackground.setXScrollSpeed(0);
            myBackground.setYScrollSpeed(0);
            myCurrentSelectionShape =
                    new SelectionShape(new Rectangle(), -myBackground.getTranslateX(),
                                       -myBackground.getTranslateY(), SHAPE_COLOR, PEN_WIDTH);
            myRoot.getChildren().add(myCurrentSelectionShape.getNode());
            callCorrectMouseButtonAction(e,
                                         (MouseEvent event) -> myInputManager
                                                 .primaryClickOccurred(e,
                                                                       -myBackground
                                                                               .getTranslateX(),
                                                                       -myBackground
                                                                               .getTranslateY(),
                                                                       myCurrentSelectionShape),
                                         (MouseEvent event) -> myInputManager
                                                 .secondaryClickOccurred(e,
                                                                         -myBackground
                                                                                 .getTranslateX(),
                                                                         -myBackground
                                                                                 .getTranslateY(),
                                                                         myCurrentSelectionShape));
        });
        setOnMouseReleased(e -> {
            myCurrentSelectionShape.completeRendering();
            callCorrectMouseButtonAction(e,
                                         (MouseEvent event) -> myInputManager.primaryClickReleaseOccurred(e,
                                                                                                          -myBackground
                                                                                                                  .getTranslateX(),
                                                                                                          -myBackground
                                                                                                                  .getTranslateY(),
                                                                                                          myCurrentSelectionShape),
                                         (MouseEvent event) -> myInputManager.secondaryClickReleaseOccurred(e,
                                                                                                            -myBackground
                                                                                                                    .getTranslateX(),
                                                                                                            -myBackground
                                                                                                                    .getTranslateY(),
                                                                                                            myCurrentSelectionShape));
        });
        setOnMouseDragged(e -> {
            myCurrentSelectionShape.resize(new Point2D(e.getX(), e.getY()));
            callCorrectMouseButtonAction(e,
                                         (MouseEvent event) -> myInputManager
                                                 .primaryDragOccurred(e,
                                                                      myCurrentSelectionShape),
                                         (MouseEvent event) -> myInputManager
                                                 .secondaryDragOccurred(e,
                                                                        myCurrentSelectionShape));
        });

        setOnMouseMoved(e -> {
            myInputManager.mouseMoved(e);
        });
    }

    private void callCorrectMouseButtonAction (MouseEvent event,
                                               Consumer<MouseEvent> primaryAction,
                                               Consumer<MouseEvent> secondaryAction) {
        requestFocus();
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            primaryAction.accept(event);
        }
        else {
            secondaryAction.accept(event);
        }
    }

    public void setBackgroundScrollSpeed (double xScrollSpeed, double yScrollSpeed) {
        myBackground.setXScrollSpeed(xScrollSpeed);
        myBackground.setYScrollSpeed(yScrollSpeed);
    }

    /**
     * updates the background to scroll and draw selection boxes
     */
    public void update () {
        myBackground.update();
    }

    public void addToScene (Group g) {
        myRoot.getChildren().add(g);
    }

    public void attachInputManager (InputManager inputManager) {
        myInputManager = inputManager;

    }

    protected double getFieldWidth () {
        return myMapWidth;
    }

    protected double getFieldHeight () {
        return myMapHeight;
    }

    public void changeBackground (Image background) {
        myBackground.tileBackground(background);
    }
}
