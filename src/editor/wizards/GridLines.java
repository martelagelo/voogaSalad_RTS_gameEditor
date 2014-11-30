package editor.wizards;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;


/**
 * Grid Lines used to be displayed over a sprite sheet within the drawable game element wizard.
 *
 * @author Jonathan Tseng, Nishad Agrawal
 *
 */
public class GridLines extends Group {
    private double myWidth;
    private double myHeight;
    private double myFrameX;
    private double myFrameY;

    public GridLines (double imageWidth, double imageHeight, double frameX, double frameY) {
        myWidth = imageWidth;
        myHeight = imageHeight;
        changeSize(frameX, frameY);
    }

    public void changeSize (double frameX, double frameY) {
        myFrameX = frameX;
        myFrameY = frameY;
        getChildren().clear();
        createGridLines();
    }

    private void createGridLines () {
        createRows();
        createColumns();
    }

    private void createRows () {
        int rows = (int) (myWidth / myFrameX);
        for (int i = 0; i <= rows; i++) {
            Line line = createRow();
            line.setTranslateY(myHeight * i / rows);
            getChildren().add(line);
        }
    }

    private Line createRow () {
        Line line = new Line();
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(myWidth);
        line.setEndY(0);
        return line;
    }

    private void createColumns () {
        int cols = (int) (myHeight / myFrameY);
        for (int i = 0; i <= cols; i++) {
            Line line = createColumn();
            line.setTranslateX(myWidth * i / cols);
            getChildren().add(line);
        }
    }

    private Line createColumn () {
        Line line = new Line();
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(0);
        line.setEndY(myHeight);
        return line;
    }

    public double getFrameX () {
        return myFrameX;
    }

    public double getFrameY () {
        return myFrameY;
    }

}
