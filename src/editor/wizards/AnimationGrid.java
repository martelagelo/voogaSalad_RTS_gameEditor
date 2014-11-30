package editor.wizards;

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
    private double myWidth;
    private double myHeight;
    private double myFrameX;
    private double myFrameY;

    public AnimationGrid (double imageWidth, double imageHeight, double frameX, double frameY) {
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
        createColumns();
    }

    private void createColumns () {
        int cols = (int) (myWidth / myFrameX);
        for (int i = 0; i <= cols; i++) {
            Group col = createColumn(i);
            col.setTranslateX(myWidth * i / cols);
            getChildren().add(col);
        }
    }

    private Group createColumn (int colNumber) {
        Group col = new Group();
        int rows = (int) (myHeight / myFrameY);
        for (int i = 0; i <= rows; i ++) {
            Rectangle frame = createFrame(myHeight * i / rows, colNumber);
            col.getChildren().add(frame);
        }        
        return col;
    }

    private Rectangle createFrame (double i, int colNumber) {
        Rectangle frame = new Rectangle(myFrameX, myFrameY);
        frame.setStroke(Paint.valueOf("black"));
        frame.setFill(null);
        frame.setTranslateY(i);
        frame.setOnMouseClicked(e -> System.out.println("clicked: " + i + "," + colNumber));
        return frame;
    }

    public double getFrameX () {
        return myFrameX;
    }

    public double getFrameY () {
        return myFrameY;
    }

}
