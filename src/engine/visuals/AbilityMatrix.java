package engine.visuals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.exceptions.SaveLoadException;
import util.SaveLoadUtility;
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
import engine.UI.InputManager;


public class AbilityMatrix {
    private static final double GRID_SPACING = 2;
    public static final int MATRIX_NUM_ROWS = 4;
    public static final int MATRIX_NUM_COLS = 4;
    public static final double BUTTON_WIDTH = 45;

    public static final int NUM_ATTRIBUTES = MATRIX_NUM_COLS * MATRIX_NUM_ROWS;

    private GridPane buttonGrid;
    private List<Button> buttonList;
    private InputManager myInputManager;

    private static NumberBinding yPos;

    public AbilityMatrix (ReadOnlyDoubleProperty widthProperty,
                          ReadOnlyDoubleProperty heightProperty) {
        buttonList = new ArrayList<>();
        initializeButtons();

        initializeDisplay(heightProperty);
    }

    private void initializeDisplay (ReadOnlyDoubleProperty heightProperty) {
        DoubleProperty xDelta =
                new SimpleDoubleProperty(ScrollablePane.FAST_SCROLL_BOUNDARY / 2);
        DoubleProperty yDelta =
                new SimpleDoubleProperty(ScrollablePane.FAST_SCROLL_BOUNDARY / 4);
        yPos = Bindings.subtract(heightProperty, buttonGrid.heightProperty());
        yPos = Bindings.subtract(yPos, yDelta);

        buttonGrid.layoutXProperty().bind(xDelta);
        buttonGrid.layoutYProperty().bind(yPos);
    }

    private void initializeButtons () {
        buttonGrid = new GridPane();
        buttonGrid.setHgap(GRID_SPACING);
        buttonGrid.setVgap(GRID_SPACING);

        for (int i = 0; i < MATRIX_NUM_COLS; i++) {
            for (int j = 0; j < MATRIX_NUM_ROWS; j++) {
                Button b = new Button();
                buttonList.add(b);
                int id = i * MATRIX_NUM_ROWS + j + 1;
                b.setOnMouseClicked(e -> myInputManager.screenButtonClicked(id));
                b.setMinSize(BUTTON_WIDTH, BUTTON_WIDTH);
                b.setMaxSize(BUTTON_WIDTH, BUTTON_WIDTH);
                buttonGrid.add(b, j, i);
            }
        }
    }

    public Node getNode () {
        return buttonGrid;
    }

    public void attachInputManager (InputManager inputManager) {
        myInputManager = inputManager;
    }

    public void updateGridImages (Map<Integer, String> map) {
        for (Button b : buttonList) {
            b.setGraphic(new ImageView());
        }
        for (int i : map.keySet()) {
            if (!map.get(i).isEmpty()) {
                Image im = null;
                try {
                    im = SaveLoadUtility.loadImage(map.get(i));
                }
                catch (SaveLoadException e) {
                }
                if (im != null) {
                    buttonList.get(i - 1).setGraphic(new ImageView(im));
                }
            }
        }
    }
}
