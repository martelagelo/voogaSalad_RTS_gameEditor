package engine.visuals;

import engine.UI.InputManager;
import engine.UI.RunnerInputManager;
import java.io.IOException;
import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
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
    public ScrollablePane (Group root, double width, double height) {
        // super(root, width, height);
        this.setWidth(width);
        this.setHeight(height);
        this.setMinWidth(width);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMaxHeight(height);
        // myInputManager = new NullInputManager();
        this.root = root;
        Pane stackPane = new Pane();
        BorderPane guiBP = null;
        try {
            // TODO: add a click listener for each of the buttons in each of these squares
            guiBP =
                    (BorderPane) FXMLLoader.load(getClass().getClassLoader()
                            .getResource("engine/visuals/guipanes/runner.fxml"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.getChildren().add(root);
        myBackground = new ScrollableBackground(width, height, FIELD_WIDTH, FIELD_HEIGHT);
        mySelectionBox = new SelectionBox();
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
        setOnKeyTyped(e -> myInputManager.keyPressed(e));
        setOnMousePressed(e -> {
            myBackground.setXScrollSpeed(0);
            myBackground.setYScrollSpeed(0);
            callCorrectMouseButtonAction(e,
                                         (MouseEvent event) -> myInputManager
                                                 .primaryClickOccurred(e, -myBackground
                                                         .getTranslateX(),
                                                                       -myBackground
                                                                               .getTranslateY(),
                                                                       mySelectionBox),
                                         (MouseEvent event) -> myInputManager
                                                 .secondaryClickOccurred(e, -myBackground
                                                         .getTranslateX(),
                                                                         -myBackground
                                                                                 .getTranslateY(),
                                                                         mySelectionBox));
        });
        setOnMouseReleased(e -> {
            callCorrectMouseButtonAction(e,
                                         (MouseEvent event) -> myInputManager
                                                 .primaryClickReleaseOccurred(e,
                                                                              -myBackground
                                                                                      .getTranslateX(),
                                                                              -myBackground
                                                                                      .getTranslateY(),
                                                                              mySelectionBox),
                                         (MouseEvent event) -> myInputManager
                                                 .secondaryClickReleaseOccurred(e,
                                                                                -myBackground
                                                                                        .getTranslateX(),
                                                                                -myBackground
                                                                                        .getTranslateY(),
                                                                                mySelectionBox));
        });
        setOnMouseDragged(e -> callCorrectMouseButtonAction(e,
                                                            (MouseEvent event) -> myInputManager
                                                                    .primaryDragOccurred(e,
                                                                                         mySelectionBox),
                                                            (MouseEvent event) -> myInputManager
                                                                    .secondaryDragOccurred(e,
                                                                                           mySelectionBox)));
        setOnMouseMoved(e -> mouseMoved(e));
    }

    private void callCorrectMouseButtonAction (MouseEvent event,
                                               Consumer<MouseEvent> primaryAction,
                                               Consumer<MouseEvent> secondaryAction) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            primaryAction.accept(event);
        }
        else {
            secondaryAction.accept(event);
        }
    }

    public void mouseMoved (MouseEvent event) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        // doesn't make sense to route this through the inputManager, since it would just go
        // right back here again
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

    public void addToScene (Group g) {
        root.getChildren().add(g);
    }

    public void attachInputManager (RunnerInputManager inputManager) {
        myInputManager = inputManager;

    }
}
