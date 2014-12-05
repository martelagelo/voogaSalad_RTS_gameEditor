package engine.visuals;

import java.util.List;
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
    private static final double X_SCALE = ScrollablePane.FIELD_WIDTH / MINIMAP_WIDTH;
    private static final double Y_SCALE = ScrollablePane.FIELD_HEIGHT / MINIMAP_HEIGHT;
    private static double MINIMAP_XPOS;
    private static double MINIMAP_YPOS;

    private ScrollablePane myScene;
    private Canvas myDisplay;
    private GraphicsContext myGraphicsContext;

    /**
     * Constructor for the MiniMap
     */
    public MiniMap (ScrollablePane SS) {
        MINIMAP_XPOS = SS.getWidth() - MINIMAP_WIDTH - ScrollablePane.FAST_SCROLL_BOUNDARY / 2;
        MINIMAP_YPOS = SS.getHeight() - MINIMAP_HEIGHT - ScrollablePane.FAST_SCROLL_BOUNDARY / 4;
        myScene = SS;
        myDisplay = new Canvas();
        myGraphicsContext = myDisplay.getGraphicsContext2D();
        initializeDisplay();
        initializeGraphicsContext();
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
        myGraphicsContext.strokeRoundRect(XPos / X_SCALE, YPos / Y_SCALE,
                                          myScene.getWidth() / X_SCALE, myScene.getHeight() /
                                                                        Y_SCALE,
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
            c = Color.valueOf(SGE.getTextualAttribute(StateTags.TEAM_COLOR));
        }
        catch (IllegalArgumentException | NullPointerException e) {
            c = Color.BLACK; // default for if color isn't defined
        }
        myGraphicsContext.setFill(c);
    }

    private void setUnitShape (SelectableGameElement SGE) {
        if (SGE.getNumericalAttribute(StateTags.MOVEMENT_SPEED).doubleValue() == 0) {
            myGraphicsContext.fillRect(SGE.getPosition().getX() / X_SCALE, SGE
                    .getPosition().getY() / Y_SCALE,
                                       MINIMAP_BUILDING_DIMENSION, MINIMAP_BUILDING_DIMENSION);
        }
        else {
            myGraphicsContext.fillOval(SGE.getPosition().getX() / X_SCALE, SGE
                    .getPosition().getY() / Y_SCALE, MINIMAP_UNIT_DIAMETER,
                                       MINIMAP_UNIT_DIAMETER);
        }
    }

    private void initializeDisplay () {
        myDisplay.setLayoutX(MINIMAP_XPOS);
        myDisplay.setLayoutY(MINIMAP_YPOS);
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
