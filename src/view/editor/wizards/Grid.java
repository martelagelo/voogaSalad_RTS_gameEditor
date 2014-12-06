package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


/**
 * Grid Lines used to be displayed over a sprite sheet within the drawable game element wizard.
 *
 * @author Jonathan Tseng, Nishad Agrawal
 *
 */
public abstract class Grid extends Group {
    protected static final String FRAME_STROKE_COLOR = "black";
    protected double myFrameX;
    protected double myFrameY;
    protected List<List<Frame>> frameCols;

    public Grid (double frameX, double frameY) {
        frameCols = new ArrayList<>();
        myFrameX = frameX;
        myFrameY = frameY;
        getChildren().clear();
        createGrid();
    }

    public abstract void selectFrame (int row, int col, Paint fill);

    protected void resetExistingFill (String primary, Paint reset) {
        frameCols.forEach(col -> col
                .stream()
                .filter(frame -> frame.getFill() != null &&
                                 frame.getFill().equals(Paint.valueOf(primary)))
                .forEach(frame -> frame.setFill(reset)));
    }

    protected void createGrid () {
        frameCols.clear();
        createColumns();
    }

    protected abstract void createColumns ();

    protected abstract Group createColumn (int col);

    protected Frame createFrame (int row, int col, Paint fill) {
        Frame frame = new Frame(myFrameX, myFrameY);
        frame.setStroke(Paint.valueOf(FRAME_STROKE_COLOR));
        frame.setFill(fill);
        return frame;
    }
    
    public double getFrameX() {
        return myFrameX;
    }
    
    public double getFrameY() {
        return myFrameY;
    }

    protected class Frame extends Rectangle {
        public boolean selected;

        public Frame (double x, double y) {
            super(x, y);
            selected = false;
        }
    }

}
