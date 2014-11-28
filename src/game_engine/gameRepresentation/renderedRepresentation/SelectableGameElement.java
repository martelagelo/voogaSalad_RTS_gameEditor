package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import game_engine.visuals.ScrollablePane;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


/**
 * A wrapper for game elements capable of being selected. Adds a "selected"
 * visual appearance to the appearance defined by the DrawableGameElement and
 * handles animations for actions resulting from being selected.
 *
 * @author Jonathan , Steve, Nishad, Rahul, John, Michael D., Zach
 *
 */
public class SelectableGameElement extends DrawableGameElement {

    private boolean isSelected;
    // private SelectableGameElementState myState;
    // private Point2D heading;
    // private boolean selected;

    // TODO temporary, should be in the attributes
    private double speed = 3;

    private DIRECTION myDirection = DIRECTION.FWD;

    private enum DIRECTION {
        FWD, FWD_LEFT, LEFT, BK_LEFT, BK, BK_RIGHT, RIGHT, FWD_RIGHT
    }

    public final static String X_VEL = "xVelocity";
    public final static String Y_VEL = "yVelocity";

    private Queue<Point2D> headings;

    /**
     * @see DrawableGameElementState
     */
    // public SelectableGameElement (DrawableGameElementState element) {
    // super(element);
    // this.isSelected = false;
    public SelectableGameElement (DrawableGameElementState element,
                                  Map<String, List<Evaluatable<?>>> conditionActionPairs) {
        super(element, conditionActionPairs);
        isSelected = false;
        headings = new LinkedList<Point2D>();

        getState().setNumericalAttribute(X_VEL, 0);
        getState().setNumericalAttribute(Y_VEL, 0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the type of the element
     */
    public String getType () {
        return getState().getType();
        // return myState.getType();
    }

    /**
     * Select the element
     *
     * @param select a boolean indicating whether the object should be selected
     */
    public void select (boolean select) {
        isSelected = select;
        updateSelectedIndicator();
        // myAnimation.select(select);
    }

    public List<Line> getLines () {
        if (headings.size() != 0 && isSelected) {
            Queue<Point2D> copyOfHeadings = new LinkedList<Point2D>();
            List<Line> lineList = new ArrayList<Line>();
            while (headings.size() >= 2) {
                Point2D point1 = headings.poll();
                Point2D point2 = headings.peek();
                Line line = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
                line.setStroke(Color.YELLOW);
                line.getStrokeDashArray().addAll(25d, 10d);
                lineList.add(line);
                copyOfHeadings.add(point1);
            }
            copyOfHeadings.add(headings.poll());
            headings = copyOfHeadings;
            return lineList;
        }
        else {
            return new ArrayList<Line>();
        }
    }

    /**
     * Update the object due to internal influences then update the object due
     * to collisions, visible objects, and the current objective of the object
     */
    @Override
    public void update () {
        super.update();
        move();
        updateSelfDueToCollisions();
        updateSelfDueToVisions();
        updateSelfDueToCurrentObjective();

    }

    // private void move () {
    // if (heading == null)
    // heading = getLocation();
    //
    // setAnimationDirection(getLocation(), heading, !heading.equals(getLocation()));
    //
    // if (!heading.equals(getLocation())) {
    // Point2D delta = new Point2D(heading.getX() - getLocation().getX(),
    // heading.getY() - getLocation().getY());
    // if (delta.magnitude() > speed)
    // delta = delta.normalize().multiply(speed);
    // this.setLocation(getLocation().add(delta));
    // }
    // }

    private DIRECTION getDirection (Point2D loc, Point2D destination) {
        double angle =
                Math.atan2(-(destination.getY() - loc.getY()), destination.getX() - loc.getX());
        double pi = Math.PI;
        if ((-pi / 4.) < angle && angle <= (pi / 4.)) {
            return DIRECTION.RIGHT;
        }
        else if ((pi / 8) < angle && angle <= (3 * pi / 8)) {
            return DIRECTION.BK_RIGHT;
        }
        else if (3 * pi / 8 < angle && angle <= 5 * pi / 8) {
            return DIRECTION.BK;
        }
        else if (5 * pi / 8 < angle && angle < 7 * pi / 8) {
            return DIRECTION.BK_LEFT;
        }
        else if (7 * pi / 8 <= Math.abs(angle)) {
            return DIRECTION.LEFT;
        }
        else if (-7 * pi / 8 < angle && angle <= -5 * pi / 8) {
            return DIRECTION.FWD_LEFT;
        }
        else if (-5 * pi / 8 < angle && angle <= -3 * pi / 8) {
            return DIRECTION.FWD;
        }
        else {
            return DIRECTION.FWD_RIGHT;
        }
    }

    private void setAnimationDirection (Point2D loc, Point2D destination, boolean isMoving) {
        String animationString;
        if (isMoving && !(destination == null)) {
            myDirection = getDirection(loc, destination);
            animationString =
                    ("walk_" + myDirection.toString()).toLowerCase();
        }
        else {
            animationString = ("stand_" + myDirection.toString()).toLowerCase();
        }
        setAnimation(animationString);
    }

    private void move () {
        boolean canMove =
                getState().getNumericalAttribute(StateTags.CAN_MOVE_STRING)
                        .intValue() == 1;
        if (!canMove) { return; }
        boolean randomMove =
                getState().getNumericalAttribute(StateTags.RANDOM_MOVEMENT_STRING)
                        .intValue() == 1;
        Random r = new Random();
        if (randomMove) {
            if (headings.size() < 3) {
                headings.add(new Point2D(r.nextDouble() * ScrollablePane.FIELD_WIDTH, r
                        .nextDouble() * ScrollablePane.FIELD_HEIGHT));
            }
        }
        if (headings.size() == 0) {
            setAnimationDirection(getLocation(), headings.peek(), !(headings.size() == 0));
            updateImageLocation();
        }
        else {
            setAnimationDirection(getLocation(), headings.peek(), !(headings.size() == 0));
            if (!headings.peek().equals(getLocation())) {
                Point2D currentHeading = headings.peek();
                Point2D delta = new Point2D(currentHeading.getX()
                                            - getLocation().getX(), currentHeading.getY()
                                                                    - getLocation().getY());
                if (delta.magnitude() > speed) {
                    delta = delta.normalize().multiply(speed);
                }
                getState().setNumericalAttribute(X_VEL, delta.getX());
                getState().setNumericalAttribute(Y_VEL, delta.getY());
                getState()
                        .setNumericalAttribute(StateTags.X_POS_STRING,
                                               getState()
                                                       .getNumericalAttribute(StateTags.X_POS_STRING)
                                                       .doubleValue() +
                                                       delta.getX());
                getState()
                        .setNumericalAttribute(StateTags.Y_POS_STRING,
                                               getState()
                                                       .getNumericalAttribute(StateTags.Y_POS_STRING)
                                                       .doubleValue() +
                                                       delta.getY());
                updateImageLocation();
            }
            else {
                headings.poll();
                updateImageLocation();
            }
        }
    }

    private void updateSelfDueToCurrentObjective () {
        // getApplicableConditionActionPairs("ObjectiveCondition");
    }

    public void updateSelfDueToSelection () {
        // getApplicableConditionActionPairs("SelfCondition");
    }

    private void updateSelfDueToVisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset(
                                                                "VisionCondition",
                                                                "VisibleElements");
    }

    private void updateSelfDueToCollisions () {
        // System.out.println("Updating due to colliding objects");
        // TODO fix string literal
        Set<DrawableGameElementState> elementsOfInterest =
                ((SelectableGameElementState) (getState()))
                        .getInteractingElements().get("CollidingElements");
        getActionsOfType("collision").forEachRemaining(action -> {
            for (DrawableGameElementState element : elementsOfInterest) {
                ElementPair elements = new ElementPair(getState(), element);
                if ((Boolean) action.getValue(elements)) {
                                                       return;
                                                       }
                                                   }
                                               });

    }

    private void updateSelectedIndicator () {
        VBox myDisplayVBox = super.getDisplayVBox();
        ImageView iv = (ImageView) myDisplayVBox.getChildren().get(0);
        if (isSelected) {
            iv.setOpacity(1.0);
        }
        else {
            iv.setOpacity(0.0);
        }
    }

    // TODO FIX THIS SHIT
    private void evaluateConditionActionPairsOnInteractingElementsSubset (
                                                                          String conditionActionPairIdentifier,
                                                                          String elementIdentifier) {

        // List<Entry<Evaluatable, Action>> applicableConditionActionPairs =
        // getApplicableConditionActionPairs(conditionActionPairIdentifier);
        // if (myState.getInteractingElements().containsKey(elementIdentifier))
        // {
        // for (DrawableGameElementState element :
        // myState.getInteractingElements()
        // .get(elementIdentifier)) {
        // List<GameElementState> immediatelyInteractingElements =
        // new ArrayList<GameElementState>();
        // for (Entry<Evaluatable, Action> conditionActionPair :
        // applicableConditionActionPairs) {
        // if (conditionActionPair.getKey().evaluate(this, element)) {
        // conditionActionPair.getValue().doAction(immediatelyInteractingElements);
        // }
        // }
        // }
        // }
    }

    public boolean isSelected () {
        return isSelected;
    }

    public void setHeading (Point2D click) {
        headings.add(click);
    }

    public void clearHeadings () {
        headings.clear();
    }

}
