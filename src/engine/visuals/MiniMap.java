package engine.visuals;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.state.gameelement.StateTags;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;


/**
 * The minimap for the application
 * 
 * @author Michael D., John L.
 *
 */
public class MiniMap {

    private static final int CONTEXT_RECT_LINE_WIDTH = 2;
    private static final int CONTEXT_RECT_ARC_WIDTH = 10;
    private static final int MINIMAP_UNIT_DIAMETER = 3;
    private static final int MINIMAP_BUILDING_DIMENSION = 8;
    private static final int MINIMAP_LINE_WIDTH = 5;
    private static final int MINIMAP_ARC_WIDTH = 40;
    private static final double MINIMIAP_OPACITY = 0.8;
    private static final double MINIMAP_WIDTH = 320;
    private static final double MINIMAP_HEIGHT = 160;
    
    private double xScale;
    private double yScale;

    private static NumberBinding xPos;
    private static NumberBinding yPos;

    private ScrollablePane myScene;
    private Canvas myDisplay;
    private GraphicsContext myGraphicsContext;

    /**
     * Constructor for the MiniMap
     */
    public MiniMap (ScrollablePane SS) {
        DoubleProperty xDelta =
                new SimpleDoubleProperty(MINIMAP_WIDTH + ScrollablePane.FAST_SCROLL_BOUNDARY / 2);
        DoubleProperty yDelta =
                new SimpleDoubleProperty(MINIMAP_HEIGHT + ScrollablePane.FAST_SCROLL_BOUNDARY / 4);
        xPos = Bindings.subtract(SS.prefWidthProperty(), xDelta);
        yPos = Bindings.subtract(SS.prefHeightProperty(), yDelta);

        myScene = SS;
        myDisplay = new Canvas();
        myGraphicsContext = myDisplay.getGraphicsContext2D();
        initializeDisplay();
        initializeGraphicsContext();
        xScale = SS.getFieldWidth() / MINIMAP_WIDTH;
        yScale = SS.getFieldHeight() / MINIMAP_HEIGHT;
    }

    /**
     * Gets the canvas for the minimap
     * 
     * @return The canvas representing the minimap
     */
    public Canvas getDisplay () {
        return myDisplay;
    }

    /**
     * Updates the drawings and points on the minimap
     */
    public void updateMiniMap (List<SelectableGameElement> unitList) {
        initializeGraphicsContext();
        moveSceneBox();
        moveUnits(unitList);
    }

    private void moveSceneBox () {
        double XPos = -1 * myScene.getScrollingBackground().getTranslateX();
        double YPos = -1 * myScene.getScrollingBackground().getTranslateY();
        myGraphicsContext.setLineWidth(CONTEXT_RECT_LINE_WIDTH);
        myGraphicsContext.setStroke(Color.BLUE);
        myGraphicsContext.strokeRoundRect(XPos / xScale, YPos / yScale,
                                          myScene.getWidth() / xScale, myScene.getHeight() /
                                                                        yScale,
                                          CONTEXT_RECT_ARC_WIDTH, CONTEXT_RECT_ARC_WIDTH);
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
            c = Color.valueOf(SGE.getTextualAttribute(StateTags.TEAM_COLOR.getValue()));
        }
        catch (IllegalArgumentException | NullPointerException e) {
            c = Color.BLACK; // default for if color isn't defined
        }
        myGraphicsContext.setFill(c);
    }

    private void setUnitShape (SelectableGameElement SGE) {
        if (SGE.getNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue()).doubleValue() == 0) {
            myGraphicsContext.fillRect(SGE.getPosition().getX() / xScale, SGE
                    .getPosition().getY() / yScale,
                                       MINIMAP_BUILDING_DIMENSION, MINIMAP_BUILDING_DIMENSION);
        }
        else {
            myGraphicsContext.fillOval(SGE.getPosition().getX() / xScale, SGE
                    .getPosition().getY() / yScale, MINIMAP_UNIT_DIAMETER,
                                       MINIMAP_UNIT_DIAMETER);
        }
    }

    private void initializeDisplay () {
        myDisplay.layoutXProperty().bind(xPos);
        myDisplay.layoutYProperty().bind(yPos);
        myDisplay.setWidth(MINIMAP_WIDTH);
        myDisplay.setHeight(MINIMAP_HEIGHT);
        myDisplay.setOpacity(MINIMIAP_OPACITY);
    }

    private void initializeGraphicsContext () {
        myGraphicsContext.setFill(Color.WHITE);
        myGraphicsContext.setStroke(Color.BLACK);
        myGraphicsContext.setLineWidth(MINIMAP_LINE_WIDTH);
        myGraphicsContext.fillRoundRect(0, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT,
                                        MINIMAP_ARC_WIDTH, MINIMAP_ARC_WIDTH);
        myGraphicsContext.strokeRoundRect(0, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT,
                                          MINIMAP_ARC_WIDTH, MINIMAP_ARC_WIDTH);
    }

}
