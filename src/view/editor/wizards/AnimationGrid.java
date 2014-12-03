package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javafx.event.Event;
import javafx.event.EventHandler;
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
    private List<List<Frame>> rectangleCols;
    private BiConsumer<Integer, Integer> onClick;

    public AnimationGrid (double imageWidth, double imageHeight, double frameX, double frameY,
                          BiConsumer<Integer, Integer> consumer) {
        myWidth = imageWidth;
        myHeight = imageHeight;
        rectangleCols = new ArrayList<>();
        onClick = consumer;        
        changeSize(frameX, frameY);
    }

    public void changeSize (double frameX, double frameY) {
        myFrameX = frameX;
        myFrameY = frameY;
        getChildren().clear();
        createGridLines();
    }

    private void createGridLines () {
        rectangleCols.clear();
        createColumns();
    }

    private void createColumns () {
        int col = 0;
        for (int i = 0; i <= myWidth; i += myFrameX) {
            rectangleCols.add(col, new ArrayList<>());
            Group column = createColumn(col);
            column.setTranslateX(i);
            getChildren().add(column);
            col++;
        }
    }

    private Group createColumn (int col) {
        Group column = new Group();
        int row = 0;
        for (int i = 0; i <= myHeight; i += myFrameY) {
            Frame frame = createFrame(row, col);
            column.getChildren().add(frame);
            frame.setTranslateY(i);
            rectangleCols.get(col).add(row, frame);
            row++;
        }
        return column;
    }

    private Frame createFrame (int row, int col) {
        Frame frame = new Frame(myFrameX, myFrameY);
        frame.setStroke(Paint.valueOf("black"));
        frame.setFill(null);
        frame.setOnMouseClicked(e -> System.out.println(e.getScreenX()));
//            onClick.accept(row, col);
//            frame.selected = !frame.selected;
//            if (frame.selected) {
//                frame.setFill(Paint.valueOf("blue"));
//                frame.setOpacity(0.5);
//            }
//            else {
//                frame.setFill(null);
//                frame.setOpacity(0.0);
//            }
        return frame;
    }

    public double getFrameX () {
        return myFrameX;
    }

    public double getFrameY () {
        return myFrameY;
    }
    
    class Frame extends Rectangle {
        public boolean selected;
        public Frame (double x, double y) {
            super(x,y);
            selected = false;
        }        
    }

}
