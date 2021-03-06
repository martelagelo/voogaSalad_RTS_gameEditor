package engine.visuals;

import java.io.IOException;
import java.util.function.Consumer;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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

    private ScrollableBackground myBackground;
    private SelectionBox mySelectionBox;
    private InputManager myInputManager;
    private Group myRoot;

    private double myMapWidth;
    private double myMapHeight;

    /**
     * Creates a new ScrollableScene for the map.
     *
     * @param root
     *            the group of elements to add to the Scene initially. If no
     *            objects have been created yet to add, just add an empty new
     *            Group()
     * @param width
     *            the width of the map (ideally larger than the screen width)
     * @param height
     *            the height of the map (ideally larger than the screen height)
     * @throws IOException
     */
    public ScrollablePane (Group root, double fieldWidth, double fieldHeight, String backgroundURI) {
        // super(root, width, height);
        // setStyle("-fx-border-color: red;");
        myMapWidth = fieldWidth;
        myMapHeight = fieldHeight;
        myRoot = root;
        Pane stackPane = new Pane();

        getChildren().add(root);
        myBackground = new ScrollableBackground(fieldWidth, fieldHeight, this, backgroundURI);
        mySelectionBox = new SelectionBox();
        BorderPane.setAlignment(myBackground, Pos.CENTER);
        stackPane.getChildren().addAll(myBackground, mySelectionBox.getBox());
        root.getChildren().add(stackPane);
        initializeHandlers();
    }

    public void bindSize (ReadOnlyDoubleProperty widthProperty,
            ReadOnlyDoubleProperty heightProperty) {
        prefHeightProperty().bind(heightProperty);
        prefWidthProperty().bind(widthProperty);
        setWidth(widthProperty.doubleValue());
        setHeight(heightProperty.doubleValue());
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
            callCorrectMouseButtonAction(e,
                    (MouseEvent event) -> myInputManager.primaryClickOccurred(e,
                            -myBackground.getTranslateX(), -myBackground.getTranslateY(),
                            mySelectionBox),
                    (MouseEvent event) -> myInputManager.secondaryClickOccurred(e,
                            -myBackground.getTranslateX(), -myBackground.getTranslateY(),
                            mySelectionBox));
        });
        setOnMouseReleased(e -> {
            callCorrectMouseButtonAction(e,
                    (MouseEvent event) -> myInputManager.primaryClickReleaseOccurred(e,
                            -myBackground.getTranslateX(), -myBackground.getTranslateY(),
                            mySelectionBox),
                    (MouseEvent event) -> myInputManager.secondaryClickReleaseOccurred(e,
                            -myBackground.getTranslateX(), -myBackground.getTranslateY(),
                            mySelectionBox));
        });
        setOnMouseDragged(e -> callCorrectMouseButtonAction(e,
                (MouseEvent event) -> myInputManager.primaryDragOccurred(e, mySelectionBox),
                (MouseEvent event) -> myInputManager.secondaryDragOccurred(e, mySelectionBox)));
        setOnMouseMoved(e -> mouseMoved(e));
        hoverProperty().addListener( (observable, oldValue, newValue) -> {
            if (!newValue) {
                setFocused(false);
                myBackground.setXScrollSpeed(0);
                myBackground.setYScrollSpeed(0);
            } else {
                requestFocus();
            }
        });
    }

    private void callCorrectMouseButtonAction (MouseEvent event,
            Consumer<MouseEvent> primaryAction, Consumer<MouseEvent> secondaryAction) {
        requestFocus();
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            primaryAction.accept(event);
        } else {
            secondaryAction.accept(event);
        }
    }

    public void mouseMoved (MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        // doesn't make sense to route this through the inputManager, since it
        // would just go
        // right back here again
        setScrollSpeed(mouseX, mouseY);
    }

    private void setScrollSpeed (double mouseX, double mouseY) {
        if (mouseX < SLOW_SCROLL_BOUNDARY) {
            myBackground.setXScrollSpeed(-SLOW_SPEED);
            if (mouseX < FAST_SCROLL_BOUNDARY) {
                myBackground.setXScrollSpeed(-FAST_SPEED);
            }
        } else if (mouseY < SLOW_SCROLL_BOUNDARY) {
            myBackground.setYScrollSpeed(-SLOW_SPEED);
            if (mouseY < FAST_SCROLL_BOUNDARY) {
                myBackground.setYScrollSpeed(-FAST_SPEED);
            }
        } else if (mouseX > getWidth() - SLOW_SCROLL_BOUNDARY) {
            myBackground.setXScrollSpeed(SLOW_SPEED);
            if (mouseX > getWidth() - FAST_SCROLL_BOUNDARY) {
                myBackground.setXScrollSpeed(FAST_SPEED);
            }
        } else if (mouseY > getHeight() - SLOW_SCROLL_BOUNDARY) {
            myBackground.setYScrollSpeed(SLOW_SPEED);
            if (mouseY > getHeight() - FAST_SCROLL_BOUNDARY) {
                myBackground.setYScrollSpeed(FAST_SPEED);
            }
        } else {
            myBackground.setXScrollSpeed(0);
            myBackground.setYScrollSpeed(0);
        }
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

    public double getFieldWidth () {
        return myMapWidth;
    }

    public double getFieldHeight () {
        return myMapHeight;
    }

    public void changeBackground (String backgroundURI) {
        myBackground.tileBackground(backgroundURI);
    }
}
