package game_engine.visuals;

import game_engine.visuals.UI.ClickManager;
import game_engine.visuals.UI.KeyboardManager;
import game_engine.visuals.UI.SelectionBox;
import java.util.Observer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


/**
 * Basically the map. JavaFX Scene node that contains the eventHandlers necessary
 * to scroll the map and register clicks.
 * 
 * @author John
 *
 */
public class ScrollableScene extends Scene {
    public static final double SLOW_SCROLL_BOUNDARY = 75;
    public static final double FAST_SCROLL_BOUNDARY = 30;
    public static final double FAST_SPEED = 15;
    public static final double SLOW_SPEED = 7;
    public static final double FIELD_WIDTH = 2000;
    public static final double FIELD_HEIGHT = 2000;

    private ScrollableBackground myBackground;
    private ClickManager myClickManager;
    private SelectionBox mySelectionBox;
    private KeyboardManager myKeyboardManager;
    private Rectangle myBox;
    private double myScreenHeight;
    private double myScreenWidth;
    private double pressedX, pressedY;

    /**
     * Creates a new ScrollableScene for the map.
     * 
     * @param root the group of elements to add to the Scene initially. If
     *        no objects have been created yet to add, just add an empty new Group()
     * @param width the width of the map (ideally larger than the screen width)
     * @param height the height of the map (ideally larger than the screen height)
     */
    public ScrollableScene (Group root, double width, double height) {
        super(root, width, height);
        this.myScreenHeight = height;
        this.myScreenWidth = width;
        myClickManager = new ClickManager();
        myKeyboardManager = new KeyboardManager();
        myBackground = new ScrollableBackground(width, height, FIELD_WIDTH, FIELD_HEIGHT);
        mySelectionBox = new SelectionBox();
        myBox = mySelectionBox.getBox();
        root.getChildren().addAll(myBackground, myBox);
        initializeHandlers();
    }

    /**
     * gets the background map that objects actually get added to
     * 
     * @return
     */
    public ScrollableBackground getBackground () {
        return myBackground;
    }

    /**
     * initializes the handlers that respond to JavaFX events necessary for scrolling and
     * registering clicks/ drags
     */
    private void initializeHandlers () {
        this.setOnKeyTyped(new EventHandler<KeyEvent>() {

            @Override
            public void handle (KeyEvent event) {
                myKeyboardManager.keyPressed(event);
            }

        });

        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();
                setScrollSpeed(mouseX, mouseY);
            }

            private void setScrollSpeed (double mouseX, double mouseY) {
                if (mouseX < SLOW_SCROLL_BOUNDARY) {
                    myBackground.setXScrollSpeed(-SLOW_SPEED);
                    if (mouseX < FAST_SCROLL_BOUNDARY) myBackground.setXScrollSpeed(-FAST_SPEED);
                }
                else if (mouseY < SLOW_SCROLL_BOUNDARY) {
                    myBackground.setYScrollSpeed(-SLOW_SPEED);
                    if (mouseY < FAST_SCROLL_BOUNDARY) myBackground.setYScrollSpeed(-FAST_SPEED);
                }
                else if (mouseX > myScreenWidth - SLOW_SCROLL_BOUNDARY) {
                    myBackground.setXScrollSpeed(SLOW_SPEED);
                    if (mouseX > myScreenWidth - FAST_SCROLL_BOUNDARY)
                        myBackground.setXScrollSpeed(FAST_SPEED);
                }
                else if (mouseY > myScreenHeight - SLOW_SCROLL_BOUNDARY) {
                    myBackground.setYScrollSpeed(SLOW_SPEED);
                    if (mouseY > myScreenHeight - FAST_SCROLL_BOUNDARY)
                        myBackground.setYScrollSpeed(FAST_SPEED);
                }
                else {
                    myBackground.setXScrollSpeed(0);
                    myBackground.setYScrollSpeed(0);
                }
            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>()
        {
            public void handle (MouseEvent event)
            {
                myBackground.setXScrollSpeed(0);
                myBackground.setYScrollSpeed(0);
                pressedX = event.getSceneX();
                pressedY = event.getSceneY();
                if (event.isPrimaryButtonDown()) {
                    mySelectionBox.resetBox();
                    mySelectionBox.setLocation(pressedX, pressedY);
                }

                double xLoc = -myBackground.getTranslateX() + pressedX;
                double yLoc = -myBackground.getTranslateY() + pressedY;

                myClickManager.clicked(event, xLoc, yLoc);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    myBox.setVisible(false);
                    // do the math to determine the box's location relative to the map, rather than
                    // on
                    // the screen
                    double xTopLeftMap =
                            -myBackground.getTranslateX() + myBox.xProperty().doubleValue();
                    double yTopLeftMap =
                            -myBackground.getTranslateY() + myBox.yProperty().doubleValue();
                    double xBottomRight = xTopLeftMap + myBox.getWidth();
                    double yBottomRight = yTopLeftMap + myBox.getHeight();
                    if (xTopLeftMap != xBottomRight && yTopLeftMap != yBottomRight)
                        mySelectionBox.released(xTopLeftMap, yTopLeftMap, xBottomRight,
                                                yBottomRight);
                }
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            public void handle (MouseEvent event)
            {
                if (event.isPrimaryButtonDown()) {
                    double newX = event.getSceneX();
                    double newY = event.getSceneY();

                    // stops the drag at the edge of the scene
                    if (newX < 0) newX = 0;
                    if (newY < 0) newY = 0;
                    if (newX > myScreenWidth) newX = myScreenWidth;
                    if (newY > myScreenHeight) newY = myScreenHeight;

                    double difX = newX - pressedX;
                    double difY = newY - pressedY;

                    myBox.setWidth(Math.abs(difX));
                    myBox.setHeight(Math.abs(difY));

                    if (difX <= 0) myBox.setX(newX);
                    if (difY <= 0) myBox.setY(newY);

                    event.consume();
                }
            }
        });
    }

    /**
     * updates the background to scroll and draw selection boxes
     */
    public void update () {
        myBackground.update();
    }

    /**
     * adds an observer to the selection boxes drawn on this scene
     * 
     * @param o the object that wants to be notified about the location
     *        on the map the box was drawn
     */
    public void addBoxObserver (Observer o) {
        mySelectionBox.addObserver(o);
    }

    /**
     * adds an observer to the clicks on the scene
     * 
     * @param o the object that wants to be notified about the location
     *        on the map that was clicked and which button was used
     */
    public void addClickObserver (Observer o) {
        myClickManager.addObserver(o);
    }

    /**
     * adds an observer to the keys pressed while focused on the scene
     * 
     * @param o the object that wants to be notified about the key
     *        that was pressed
     */
    public void addKeyboardObserver (Observer o) {
        myKeyboardManager.addObserver(o);
    }

}
