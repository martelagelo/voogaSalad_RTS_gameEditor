package player;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;


public class ScrollableBackground extends StackPane
{
    private double myXScrollSpeed, myYScrollSpeed;
    private double myXBoundary, myYBoundary;

    private double myWidth, myHeight;

    public ScrollableBackground (double screenWidth,
                                 double screenHeight,
                                 double xBoundary,
                                 double yBoundary)
    {
        this.myWidth = screenWidth;
        this.myHeight = screenHeight;
        this.myXBoundary = xBoundary;
        this.myYBoundary = yBoundary;
        this.setMinSize(xBoundary, yBoundary);

        setStyle("-fx-border-color: blue;"); // used for testing to see the edge of the map

    }

    public void addObjects (Group g) {
        this.getChildren().add(g);
    }

    public void update () {
        if ((getTranslateX() >= 0 && myXScrollSpeed < 0) ||
            (-getTranslateX() >= (myXBoundary - myWidth) && myXScrollSpeed > 0))
            setXScrollSpeed(0);
        if ((getTranslateY() >= 0 && myYScrollSpeed < 0) ||
            (-getTranslateY() >= (myYBoundary - myHeight) && myYScrollSpeed > 0))
            setYScrollSpeed(0);
        setTranslateX(getTranslateX() - myXScrollSpeed);
        setTranslateY(getTranslateY() - myYScrollSpeed);
        retractOutOfBoundsScroll();
    }

    private void retractOutOfBoundsScroll () {
        if (-getTranslateX() >= myXBoundary - myWidth) setTranslateX(-(myXBoundary - myWidth));
        if (-getTranslateY() >= myYBoundary - myHeight) setTranslateY(-(myYBoundary - myHeight));
        if (getTranslateX() >= 0) setTranslateX(0);
        if (getTranslateY() >= 0) setTranslateY(0);
    }

    public void setXScrollSpeed (double speed) {
        myXScrollSpeed = speed;
    }

    public void setYScrollSpeed (double speed) {
        myYScrollSpeed = speed;
    }

}
