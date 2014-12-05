package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import engine.visual.animation.AnimationTag;


/**
 * Grid Lines used to be displayed over a sprite sheet within the drawable game element wizard.
 *
 * @author Jonathan Tseng, Nishad Agrawal
 *
 */
//TODO: refector Grids into hierarchy
public class DirectionGrid extends Group {
    private static final String SELECTED_COLOR = "red";
    private static final String FRAME_STROKE_COLOR = "black";
    private static final String FILL_COLOR = "white";
    private double myFrameX;
    private double myFrameY;
    private List<List<Frame>> frameCols;
    private List<AnimationTag> directions;

    public DirectionGrid (double frameX, double frameY) {
        frameCols = new ArrayList<>();
        directions = new ArrayList<>();
        myFrameX = frameX;
        myFrameY = frameY;
        getChildren().clear();
        createGrid();
    }

    public void selectFrame (int row, int col) {
        directions.clear();
        resetExistingFill(SELECTED_COLOR);
        Frame f = frameCols.get(col).get(row);
        f.setFill(Paint.valueOf(SELECTED_COLOR));  
        // I realize this is horrible code, but it is the simplest
        // most efficient way of directly mapping x,y coor to animation tags        
        if (row == 0 && col == 0) directions.add(AnimationTag.HERE);
        if (row > 1) directions.add(AnimationTag.FORWARD);
        else if (row < 1) directions.add(AnimationTag.BACKWARD);
        
        if (col > 1) directions.add(AnimationTag.RIGHT);        
        else if (col < 1) directions.add(AnimationTag.LEFT);
    }
    
    public List<AnimationTag> getDirections() {
        return directions;
    }

    private void resetExistingFill (String primary) {
        frameCols.forEach(col -> col
                .stream()
                .filter(frame -> frame.getFill() != null &&
                                 frame.getFill().equals(Paint.valueOf(primary)))
                .forEach(frame -> frame.setFill(Paint.valueOf(FILL_COLOR))));
    }

    private void createGrid () {
        frameCols.clear();
        createColumns();
    }

    private void createColumns () {
        for (int i = 0; i < 3; i++) {
            frameCols.add(i, new ArrayList<>());
            Group column = createColumn(i);
            column.setTranslateX(i * myFrameX);
            getChildren().add(column);
        }
    }

    private Group createColumn (int col) {
        Group column = new Group();
        for (int i = 0; i < 3; i++) {
            Frame frame = createFrame(i, col);
            column.getChildren().add(frame);
            frame.setTranslateY(i * myFrameY);
            frameCols.get(col).add(i, frame);
        }
        return column;
    }

    private Frame createFrame (int row, int col) {
        Frame frame = new Frame(myFrameX, myFrameY);
        frame.setStroke(Paint.valueOf(FRAME_STROKE_COLOR));
        frame.setFill(Paint.valueOf(FILL_COLOR));
        frame.setOnMouseClicked(e -> selectFrame(row, col));
        return frame;
    }

    class Frame extends Rectangle {
        public boolean selected;

        public Frame (double x, double y) {
            super(x, y);
            selected = false;
        }
    }

}
