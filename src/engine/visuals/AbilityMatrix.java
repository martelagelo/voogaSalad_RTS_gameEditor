package engine.visuals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.SaveLoadUtility;
import util.exceptions.SaveLoadException;
import engine.UI.InputManager;

public class AbilityMatrix implements VisualDisplay {
    private static final double GRID_SPACING = 2;
    public static final int MATRIX_NUM_ROWS = 4;
    public static final int MATRIX_NUM_COLS = 4;
    public static final double BUTTON_WIDTH = 45;
    public static final int NUM_ATTRIBUTES = MATRIX_NUM_COLS * MATRIX_NUM_ROWS;

    private GridPane myButtonGrid;
    private List<Button> myButtonList;
    private InputManager myInputManager;
    private NumberBinding myYPosition;

    public AbilityMatrix (ReadOnlyDoubleProperty widthProperty,
            ReadOnlyDoubleProperty heightProperty) {
        myButtonList = new ArrayList<>();
        initializeButtons();

        initializeDisplay(heightProperty);
    }

    private void initializeDisplay (ReadOnlyDoubleProperty heightProperty) {
        DoubleProperty xDelta = new SimpleDoubleProperty(ScrollablePane.FAST_SCROLL_BOUNDARY / 2);
        DoubleProperty yDelta = new SimpleDoubleProperty(ScrollablePane.FAST_SCROLL_BOUNDARY / 4);
        myYPosition = Bindings.subtract(heightProperty, myButtonGrid.heightProperty());
        myYPosition = Bindings.subtract(myYPosition, yDelta);

        myButtonGrid.layoutXProperty().bind(xDelta);
        myButtonGrid.layoutYProperty().bind(myYPosition);
    }

    private void initializeButtons () {
        myButtonGrid = new GridPane();
        myButtonGrid.setHgap(GRID_SPACING);
        myButtonGrid.setVgap(GRID_SPACING);

        for (int i = 0; i < MATRIX_NUM_COLS; i++) {
            for (int j = 0; j < MATRIX_NUM_ROWS; j++) {
                Button b = new Button();
                myButtonList.add(b);
                int id = i * MATRIX_NUM_ROWS + j + 1;
                b.setOnMouseClicked(e -> myInputManager.screenButtonClicked(id));
                b.setMinSize(BUTTON_WIDTH, BUTTON_WIDTH);
                b.setMaxSize(BUTTON_WIDTH, BUTTON_WIDTH);
                myButtonGrid.add(b, j, i);
            }
        }
    }

    public Node getNode () {
        return myButtonGrid;
    }

    public void attachInputManager (InputManager inputManager) {
        myInputManager = inputManager;
    }

    public void updateGridImages (Map<Integer, String> map, Map<String, Long> timerMap) {
        for (Button b : myButtonList) {
            b.setGraphic(new ImageView());
        }
        for (int i : map.keySet()) {
            if (!map.get(i).isEmpty()) {
                Image im = null;
                try {
                    im = SaveLoadUtility.loadImage(map.get(i));
                } catch (SaveLoadException e) {
                }
                if (im != null) {
                    StackPane p = new StackPane();
                    p.getChildren().add(new ImageView(im));
                    if (timerMap.containsKey(i + "")) {
                        Text t = new Text(timerMap.get(i + "") + "");
                        p.getChildren().add(t);
                        t.toFront();
                        t.setFill(Color.WHITE);
                    }
                    myButtonList.get(i - 1).setGraphic(p);
                }
            }
        }
    }
}
