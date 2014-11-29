package game_engine.visuals;

import game_engine.UI.InputManager;
import java.io.IOException;
import java.util.Observer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


/**
 * Basically the map. JavaFX Scene node that contains the eventHandlers necessary
 * to scroll the map and register clicks.
 * 
 * @author John, Michael D.
 *
 */
public class ScrollablePane extends Pane {
    public static final double SLOW_SCROLL_BOUNDARY = 120;
    public static final double FAST_SCROLL_BOUNDARY = 75;
    public static final double FAST_SPEED = 15;
    public static final double SLOW_SPEED = 7;
    public static final double FIELD_WIDTH = 2000;
    public static final double FIELD_HEIGHT = 2000;

    private ScrollableBackground myBackground;
    private SelectionBox mySelectionBox;
    private InputManager myInputManager;
    private Group root;

    /**
     * Creates a new ScrollableScene for the map.
     * 
     * @param root the group of elements to add to the Scene initially. If
     *        no objects have been created yet to add, just add an empty new Group()
     * @param width the width of the map (ideally larger than the screen width)
     * @param height the height of the map (ideally larger than the screen height)
     * @throws IOException
     */
    public ScrollablePane (Group root, InputManager inputManager, double width, double height) {
        // super(root, width, height);
        this.setWidth(width);
        this.setHeight(height);
        this.setMinWidth(width);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMaxHeight(height);
        myInputManager = inputManager;
        this.root = root;
        Pane stackPane = new Pane();
        BorderPane guiBP = null;
        try {
            guiBP =
                    (BorderPane) FXMLLoader.load(getClass().getClassLoader()
                            .getResource("game_engine/visuals/guipanes/runner.fxml"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.getChildren().add(root);
        myBackground = new ScrollableBackground(width, height, FIELD_WIDTH, FIELD_HEIGHT);
        mySelectionBox = new SelectionBox();
        myInputManager.addClickObserver(mySelectionBox);
        myInputManager.addMouseDragObserver(mySelectionBox);
        guiBP.setCenter(myBackground);
        guiBP.setMinHeight(height);
        guiBP.setMinWidth(width);
        BorderPane.setAlignment(myBackground, Pos.CENTER);
        stackPane.getChildren().addAll(myBackground, mySelectionBox.getBox(), guiBP);
        root.getChildren().add(stackPane);
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
     * initializes the handlers that respond to JavaFX events necessary for scrolling and
     * registering clicks/ drags
     */
    private void initializeHandlers () {

        setOnKeyTyped(e -> myInputManager.inputOccurred(e));
        setOnMousePressed(e -> mousePressed(e));
        setOnMouseReleased(e -> myInputManager.inputOccurred(e, -myBackground.getTranslateX(),
                                                             -myBackground.getTranslateY(), false));
        // setOnMouseDragged(e -> myInputManager.inputOccurred(e, -myBackground.getTranslateX(),
        // -myBackground.getTranslateY(), true));
        setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle (MouseEvent event) {
                myInputManager.inputOccurred(event, -myBackground.getTranslateX(),
                                             -myBackground.getTranslateY(), true);
                System.out.println("BOX: " + mySelectionBox.getBox().getX() + ", " +
                                   mySelectionBox.getBox().getY());
            }

        });

        setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event) {
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();

                // doesn't make sense to route this through the inputManager, since it would just go
                // right back here again
                setScrollSpeed(mouseX, mouseY);
            }
        });
    }

    public void mousePressed (MouseEvent event) {
        myBackground.setXScrollSpeed(0);
        myBackground.setYScrollSpeed(0);

        myInputManager.inputOccurred(event, -myBackground.getTranslateX(),
                                     -myBackground.getTranslateY(), false);
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
        else if (mouseX > getWidth() - SLOW_SCROLL_BOUNDARY) {
            myBackground.setXScrollSpeed(SLOW_SPEED);
            if (mouseX > getWidth() - FAST_SCROLL_BOUNDARY)
                myBackground.setXScrollSpeed(FAST_SPEED);
        }
        else if (mouseY > getHeight() - SLOW_SCROLL_BOUNDARY) {
            myBackground.setYScrollSpeed(SLOW_SPEED);
            if (mouseY > getHeight() - FAST_SCROLL_BOUNDARY)
                myBackground.setYScrollSpeed(FAST_SPEED);
        }
        else {
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

    /**
     * adds an observer to the selection boxes drawn on this scene
     * 
     * @param o the object that wants to be notified about the location
     *        on the map the box was drawn
     */
    public void addBoxObserver (Observer o) {
        mySelectionBox.addObserver(o);
    }

    public void addToScene (Group g) {
        root.getChildren().add(g);
    }
}
