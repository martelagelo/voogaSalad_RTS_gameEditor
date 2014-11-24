package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * A wrapper for game elements capable of being selected. Adds a "selected"
 * visual appearance to the appearance defined by the DrawableGameElement and
 * handles animations for actions resulting from being selected.
 *
 * @author Jonathan , Steve, Nishad, Rahul, John L, Michael D.
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

    private Queue<Point2D> headings;

    /**
     * @see DrawableGameElementState
     */
    // public SelectableGameElement (DrawableGameElementState element) {
    // super(element);
    // this.isSelected = false;
    public SelectableGameElement (DrawableGameElementState element,
                                  Map<Evaluatable<Boolean>, Evaluatable<?>> conditionActionPairs) {
        super(element, conditionActionPairs);
        this.isSelected = false;
        headings = new LinkedList<Point2D>();
        // TODO: remove once attributes are loaded from files
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

    /**
     * Update the object due to internal influences then update the object due
     * to collisions, visible objects, and the current objective of the object
     */
    @Override
    public void update () {
        super.update();
        updateSelfDueToCollisions();
        updateSelfDueToVisions();
        updateSelfDueToCurrentObjective();
        move();
    }

    private DIRECTION getDirection (Point2D loc, Point2D destination) {
        double angle =
                Math.atan2(-(destination.getY() - loc.getY()), destination.getX() - loc.getX());
        double pi = Math.PI;
        if ((-pi / 4.) < angle && angle <= (pi / 4.))
            return DIRECTION.RIGHT;
        else if ((pi / 8) < angle && angle <= (3 * pi / 8))
            return DIRECTION.BK_RIGHT;
        else if (3 * pi / 8 < angle && angle <= 5 * pi / 8)
            return DIRECTION.BK;
        else if (5 * pi / 8 < angle && angle < 7 * pi / 8)
            return DIRECTION.BK_LEFT;
        else if (7 * pi / 8 <= Math.abs(angle))
            return DIRECTION.LEFT;
        else if (-7 * pi / 8 < angle && angle <= -5 * pi / 8)
            return DIRECTION.FWD_LEFT;
        else if (-5 * pi / 8 < angle && angle <= -3 * pi / 8)
            return DIRECTION.FWD;
        else return DIRECTION.FWD_RIGHT;
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
        this.getState().setAnimation(animationString);
    }

    private void move () {
        boolean canMove = getState().getNumericalAttribute(DrawableGameElementState.CAN_MOVE_STRING).intValue()==1;
        if(!canMove) return;
        
        if (headings.size() == 0) {
            setAnimationDirection(getLocation(), headings.peek(), !(headings.size()==0));
            this.setLocation(getLocation());
        }
        else {
            setAnimationDirection(getLocation(), headings.peek(), !(headings.size()==0));
            if (!headings.peek().equals(getLocation())) {
                Point2D currentHeading = headings.peek();
                Point2D delta = new Point2D(currentHeading.getX()
                                            - getLocation().getX(), currentHeading.getY()
                                                                    - getLocation().getY());
                if (delta.magnitude() > speed)
                    delta = delta.normalize().multiply(speed);
                this.setLocation(getLocation().add(delta));
            }
            else {
                headings.poll();
                this.setLocation(getLocation());
            }
        }
    }

    private void updateSelfDueToCurrentObjective () {
        getApplicableConditionActionPairs("ObjectiveCondition");
    }

    public void updateSelfDueToSelection () {
        getApplicableConditionActionPairs("SelfCondition");
    }

    private void updateSelfDueToVisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset(
                                                                "VisionCondition",
                                                                "VisibleElements");
    }

    private void updateSelfDueToCollisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset(
                                                                "CollisionCondition",
                                                                "CollidingElements");
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

    // TODO FIX THIS SHIT
    private List<Entry<Evaluatable<Boolean>, Evaluatable<?>>> getApplicableConditionActionPairs (String conditionActionPairIdentifier) {

        // return this.myConditionActionPairs.entrySet().stream()

        // .collect(Collectors.toList());
        return null;
    }
    public boolean isSelected () {
        return this.isSelected;
    }

    public void setHeading(Point2D click) {
		this.headings.add(click);
	}
    
    public void clearHeadings(){
    	this.headings.clear();
    }

}
