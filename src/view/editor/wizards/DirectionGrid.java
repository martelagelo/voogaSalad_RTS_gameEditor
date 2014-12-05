package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import engine.visual.animation.AnimationTag;


/**
 * Grid Lines used to be displayed over a sprite sheet within the drawable game element wizard.
 *
 * @author Jonathan Tseng, Nishad Agrawal
 *
 */
// TODO: refector Grids into hierarchy
public class DirectionGrid extends Grid {
    private static final String SELECTED_COLOR = "red";
    private static final Paint FILL_COLOR = Paint.valueOf("white");
    private List<AnimationTag> directions;

    public DirectionGrid (double frameX, double frameY) {
        super(frameX, frameY);
        directions = new ArrayList<>();
    }

    public void selectFrame (int row, int col) {
        selectFrame(row, col, Paint.valueOf(SELECTED_COLOR));
    }

    @Override
    public void selectFrame (int row, int col, Paint fill) {
        directions.clear();
        resetExistingFill(SELECTED_COLOR, FILL_COLOR);
        Frame f = frameCols.get(col).get(row);
        f.setFill(Paint.valueOf(SELECTED_COLOR));
        // I realize this is horrible code, but it is the simplest
        // most efficient way of directly mapping x,y coor to animation tags
        if (row == 1 && col == 1) directions.add(AnimationTag.HERE);
        if (row > 1)
            directions.add(AnimationTag.FORWARD);
        else if (row < 1) directions.add(AnimationTag.BACKWARD);

        if (col > 1)
            directions.add(AnimationTag.RIGHT);
        else if (col < 1) directions.add(AnimationTag.LEFT);
    }

    public List<AnimationTag> getDirections () {
        return directions;
    }

    @Override
    protected void createColumns () {
        for (int i = 0; i < 3; i++) {
            frameCols.add(i, new ArrayList<>());
            Group column = createColumn(i);
            column.setTranslateX(i * myFrameX);
            getChildren().add(column);
        }
    }

    @Override
    protected Group createColumn (int col) {
        Group column = new Group();
        for (int i = 0; i < 3; i++) {
            // this was added because lambdas need arguments that appear final
            int row = i;
            Frame frame = createFrame(i, col, Paint.valueOf("white"));
            frame.setOnMouseClicked(e -> selectFrame(row, col));
            column.getChildren().add(frame);
            frame.setTranslateY(i * myFrameY);
            frameCols.get(col).add(i, frame);
        }
        return column;
    }

}
