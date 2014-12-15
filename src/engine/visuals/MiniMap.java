package engine.visuals;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.state.gameelement.StateTags;
import engine.Engine;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;

/**
 * The minimap for the application
 *
 * @author Michael D., John L.
 *
 */
public class MiniMap implements VisualDisplay {

    public static final int CONTEXT_RECT_LINE_WIDTH = 2;
    public static final int CONTEXT_RECT_ARC_WIDTH = 10;
    public static final int MINIMAP_UNIT_DIAMETER = 3;
    public static final int MINIMAP_BUILDING_DIMENSION = 8;
    public static final int MINIMAP_LINE_WIDTH = 5;
    public static final int MINIMAP_ARC_WIDTH = 40;
    public static final double MINIMIAP_OPACITY = 0.8;
    public static final double MINIMAP_WIDTH = 320;
    public static final double MINIMAP_HEIGHT = 160;

    private double myXScale;
    private double myYScale;

    private NumberBinding myXPosition;
    private NumberBinding myYPosition;

    private ScrollablePane myScene;
    private Canvas myDisplay;
    private GraphicsContext myGraphicsContext;

    /**
     * Constructor for the MiniMap
     */
    public MiniMap (ScrollablePane SS) {
        DoubleProperty xDelta = new SimpleDoubleProperty(MINIMAP_WIDTH
                + ScrollablePane.FAST_SCROLL_BOUNDARY / 2);
        DoubleProperty yDelta = new SimpleDoubleProperty(MINIMAP_HEIGHT
                + ScrollablePane.FAST_SCROLL_BOUNDARY / 4);
        myXPosition = Bindings.subtract(SS.prefWidthProperty(), xDelta);
        myYPosition = Bindings.subtract(SS.prefHeightProperty(), yDelta);

        myScene = SS;
        myDisplay = new Canvas();
        myGraphicsContext = myDisplay.getGraphicsContext2D();
        initializeDisplay();
        initializeGraphicsContext();
        myXScale = SS.getFieldWidth() / MINIMAP_WIDTH;
        myYScale = SS.getFieldHeight() / MINIMAP_HEIGHT;
    }

    /**
     * Gets the canvas for the minimap
     *
     * @return The canvas representing the minimap
     */
    public Node getNode () {
        return myDisplay;
    }

    /**
     * Updates the drawings and points on the minimap
     */
    @Override
    public void update (List<SelectableGameElement> unitList) {
        initializeGraphicsContext();
        moveSceneBox();
        moveUnits(unitList);
    }

    private void moveSceneBox () {
        double XPos = -1 * myScene.getScrollingBackground().getTranslateX();
        double YPos = -1 * myScene.getScrollingBackground().getTranslateY();
        myGraphicsContext.setLineWidth(CONTEXT_RECT_LINE_WIDTH);
        myGraphicsContext.setStroke(Color.BLUE);
        myGraphicsContext.strokeRoundRect(XPos / myXScale, YPos / myYScale, myScene.getWidth()
                / myXScale, myScene.getHeight() / myYScale, CONTEXT_RECT_ARC_WIDTH,
                CONTEXT_RECT_ARC_WIDTH);
    }

    private void moveUnits (List<SelectableGameElement> gameUnits) {
        for (SelectableGameElement SGE : gameUnits) {
            setUnitColor(SGE);
            setUnitShape(SGE);
        }
    }

    private void setUnitColor (SelectableGameElement SGE) {
        Color c;
        try {
            c = Engine.colorFromInt(SGE.getNumericalAttribute(StateTags.TEAM_COLOR.getValue()).intValue());
        }
        catch (IllegalArgumentException | NullPointerException e) {
            c = Color.BLACK; // default for if color isn't defined
        }
        myGraphicsContext.setFill(c);
    }

    private void setUnitShape (SelectableGameElement SGE) {
        if (SGE.getNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue()).doubleValue() == 0) {
            myGraphicsContext.fillRect(SGE.getPosition().getX() / myXScale, SGE.getPosition()
                    .getY() / myYScale, MINIMAP_BUILDING_DIMENSION, MINIMAP_BUILDING_DIMENSION);
        } else {
            myGraphicsContext.fillOval(SGE.getPosition().getX() / myXScale, SGE.getPosition()
                    .getY() / myYScale, MINIMAP_UNIT_DIAMETER, MINIMAP_UNIT_DIAMETER);
        }
    }

    private void initializeDisplay () {
        myDisplay.layoutXProperty().bind(myXPosition);
        myDisplay.layoutYProperty().bind(myYPosition);
        myDisplay.setWidth(MINIMAP_WIDTH);
        myDisplay.setHeight(MINIMAP_HEIGHT);
        myDisplay.setOpacity(MINIMIAP_OPACITY);
    }

    private void initializeGraphicsContext () {
        myGraphicsContext.setFill(Color.WHITE);
        myGraphicsContext.setStroke(Color.BLACK);
        myGraphicsContext.setLineWidth(MINIMAP_LINE_WIDTH);
        myGraphicsContext.fillRoundRect(0, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT, MINIMAP_ARC_WIDTH,
                MINIMAP_ARC_WIDTH);
        myGraphicsContext.strokeRoundRect(0, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT, MINIMAP_ARC_WIDTH,
                MINIMAP_ARC_WIDTH);
    }

}
