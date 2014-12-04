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
public class AnimationGrid extends Group {
    private static final String FRAME_STROKE_COLOR = "black";
    private static final String STOP_COLOR = "red";
    private static final String START_COLOR = "green";
    private static final String INVALID_STOP_MESSAGE = "Stop frame must come after start frame";
    private static final String INVALID_FRAME_MESSAGE = "this is an invalid frame number";
    private double myWidth;
    private double myHeight;
    private double myFrameX;
    private double myFrameY;
    private List<List<Frame>> frameCols;
    private int start;

    public AnimationGrid (double imageWidth, double imageHeight, double frameX, double frameY) {
        myWidth = imageWidth;
        myHeight = imageHeight;
        frameCols = new ArrayList<>();
        changeSize(frameX, frameY);
        start = 0;
    }

    public void changeSize (double frameX, double frameY) {
        myFrameX = frameX;
        myFrameY = frameY;
        getChildren().clear();
        createGridLines();
    }

    public void setStart (int frameIndex) throws IndexOutOfBoundsException {
        selectFrame(frameIndex, START_COLOR);
        start = frameIndex;
    }

    public void setStop (int frameIndex) throws IndexOutOfBoundsException {
        if (frameIndex < start) { throw new IndexOutOfBoundsException(INVALID_STOP_MESSAGE); }
        selectFrame(frameIndex, STOP_COLOR);
    }

    private void selectFrame (int frameIndex, String color) throws IndexOutOfBoundsException {
        if (frameIndex < 0 || frameIndex > (frameCols.size() * frameCols.get(0).size() - 1)) {
            throw new IndexOutOfBoundsException(INVALID_FRAME_MESSAGE); 
        }
        resetExistingFill(color);
        int col = frameIndex / frameCols.get(0).size();
        int row = frameIndex % frameCols.get(0).size();
        Frame f = frameCols.get(col).get(row);
        if (f.getFill() == null) {
            f.setFill(Paint.valueOf(color));
        }
    }

    private void resetExistingFill (String primary) {
        frameCols.forEach(col -> col
                .stream()
                .filter(frame -> frame.getFill() != null &&
                                 frame.getFill().equals(Paint.valueOf(primary)))
                .forEach(frame -> frame.setFill(null)));
    }

    private void createGridLines () {
        frameCols.clear();
        createColumns();
    }

    private void createColumns () {
        int col = 0;
        for (int i = 0; i < myWidth; i += myFrameX) {
            frameCols.add(col, new ArrayList<>());
            Group column = createColumn(col);
            column.setTranslateX(i);
            getChildren().add(column);
            col++;
        }
    }

    private Group createColumn (int col) {
        Group column = new Group();
        int row = 0;
        for (int i = 0; i < myHeight; i += myFrameY) {
            Frame frame = createFrame(row, col);
            column.getChildren().add(frame);
            frame.setTranslateY(i);
            frameCols.get(col).add(row, frame);
            row++;
        }
        return column;
    }

    private Frame createFrame (int row, int col) {
        Frame frame = new Frame(myFrameX, myFrameY);
        frame.setStroke(Paint.valueOf(FRAME_STROKE_COLOR));
        frame.setFill(null);
        return frame;
    }

    public double getFrameX () {
        return myFrameX;
    }

    public double getFrameY () {
        return myFrameY;
    }
    
    public int getNumColumns() {
        return frameCols.size();
    }

    class Frame extends Rectangle {
        public boolean selected;

        public Frame (double x, double y) {
            super(x, y);
            selected = false;
        }
    }

}
