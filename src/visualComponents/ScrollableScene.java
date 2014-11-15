package visualComponents;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class ScrollableScene extends Scene {
    public static final double SLOW_SCROLL_BOUNDARY = 75;
    public static final double FAST_SCROLL_BOUNDARY = 30;
    public static final double FAST_SPEED = 15;
    public static final double SLOW_SPEED = 7;
    public static final double FIELD_WIDTH = 2000;
    public static final double FIELD_HEIGHT = 2000;

    private ScrollableBackground myBackground;
    private SelectionBox mySelectionBox;
    private double myScreenHeight;
    private double myScreenWidth;
    private double pressedX, pressedY;

    public ScrollableScene (Group root, double width, double height) {
        super(root, width, height);
        this.myScreenHeight = height;
        this.myScreenWidth = width;
        myBackground = new ScrollableBackground(width, height, FIELD_WIDTH, FIELD_HEIGHT);
        mySelectionBox = new SelectionBox();
        root.getChildren().addAll(myBackground, mySelectionBox);
        initializeHandlers();
    }

    public ScrollableBackground getBackground () {
        return myBackground;
    }

    private void initializeHandlers () {
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
                    mySelectionBox.setVisible(true);
                    mySelectionBox.setWidth(0);
                    mySelectionBox.setHeight(0);
                    mySelectionBox.setStroke(Color.RED);
                    mySelectionBox.setStrokeWidth(2);
                    mySelectionBox.setFill(Color.TRANSPARENT);

                    mySelectionBox.setX(pressedX);
                    mySelectionBox.setY(pressedY);
                }
                else {
                    // TODO call the input event for right click
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                mySelectionBox.setVisible(false);
                // do the math to determine the box's location relative to the map, rather than on
                // the screen
                double xTopLeftMap =
                        -myBackground.getTranslateX() + mySelectionBox.xProperty().doubleValue();
                double yTopLeftMap =
                        -myBackground.getTranslateY() + mySelectionBox.yProperty().doubleValue();
                double xBottomRight = xTopLeftMap + mySelectionBox.getWidth();
                double yBottomRight = yTopLeftMap + mySelectionBox.getHeight();

                mySelectionBox.released(xTopLeftMap, yTopLeftMap, xBottomRight, yBottomRight);
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            public void handle (MouseEvent event)
            {
                double newX = event.getSceneX();
                double newY = event.getSceneY();

                // stops the drag at the edge of the scene
                if (newX < 0) newX = 0;
                if (newY < 0) newY = 0;
                if (newX > myScreenWidth) newX = myScreenWidth;
                if (newY > myScreenHeight) newY = myScreenHeight;

                double difX = newX - pressedX;
                double difY = newY - pressedY;

                mySelectionBox.setWidth(Math.abs(difX));
                mySelectionBox.setHeight(Math.abs(difY));

                if (difX <= 0) mySelectionBox.setX(newX);
                if (difY <= 0) mySelectionBox.setY(newY);

                event.consume();
            }
        });
    }

    public void addObjects (Group g) {
        this.myBackground.addObjects(g);
    }

    public void update () {
        myBackground.update();
    }

}
