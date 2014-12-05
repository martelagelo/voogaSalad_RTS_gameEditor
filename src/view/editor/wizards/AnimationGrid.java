package view.editor.wizards;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Paint;


/**
 * Grid Lines used to be displayed over a sprite sheet within the drawable game element wizard.
 *
 * @author Jonathan Tseng, Nishad Agrawal
 *
 */
public class AnimationGrid extends Grid {
    private static final String STOP_COLOR = "red";
    private static final String START_COLOR = "green";
    private static final String INVALID_STOP_MESSAGE = "Stop frame must come after start frame";
    private static final String INVALID_FRAME_MESSAGE = "this is an invalid frame number";
    private double myWidth;
    private double myHeight;
    private int start;

    public AnimationGrid (double imageWidth, double imageHeight, double frameX, double frameY) {
        super(frameX, frameY);
        myWidth = imageWidth;
        myHeight = imageHeight;
        changeSize(frameX, frameY);
    }

    public void changeSize (double frameX, double frameY) {
        myFrameX = frameX;
        myFrameY = frameY;
        getChildren().clear();
        createGrid();
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
        resetExistingFill(color, null);
        int col = frameIndex / frameCols.get(0).size();
        int row = frameIndex % frameCols.get(0).size();
        selectFrame(row, col, Paint.valueOf(color));
    }

    @Override
    public void selectFrame (int row, int col, Paint fill) {
        Frame f = frameCols.get(col).get(row);
        if (f.getFill() == null) {
            f.setFill(fill);
        }
    }

    @Override
    protected void createColumns () {
        int col = 0;
        for (int i = 0; i < myWidth; i += myFrameX) {
            frameCols.add(col, new ArrayList<>());
            Group column = createColumn(col);
            column.setTranslateX(i);
            getChildren().add(column);
            col++;
        }
    }

    @Override
    protected Group createColumn (int col) {
        Group column = new Group();
        int row = 0;
        for (int i = 0; i < myHeight; i += myFrameY) {
            Frame frame = createFrame(row, col, null);
            column.getChildren().add(frame);
            frame.setTranslateY(i);
            frameCols.get(col).add(row, frame);
            row++;
        }
        return column;
    }

    public int getNumColumns () {
        return frameCols.size();
    }

}
