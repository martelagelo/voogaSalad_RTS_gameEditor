package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.visuals.ScrollableScene;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * A wrapper for game elements capable of being selected. Adds a "selected"
 * visual appearance to the appearance defined by the DrawableGameElement and
 * handles animations for actions resulting from being selected.
 *
<<<<<<< HEAD
 * @author Jonathan , Steve, Nishad, Rahul, John L, Michael D.
=======
 * @author Jonathan , Steve, Nishad, Rahul, John, Michael D., Zach
>>>>>>> engine_conditions
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
                                  Map<Evaluatable<Boolean>, Evaluatable<?>> conditionActionPairs) {
        super(element, conditionActionPairs);
        this.isSelected = false;
        headings = new LinkedList<Point2D>();

        this.getGameElementState().setNumericalAttribute(X_VEL, 0);
        this.getGameElementState().setNumericalAttribute(Y_VEL, 0);
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
        boolean randomMove = getState().getNumericalAttribute(DrawableGameElementState.RANDOM_MOVEMENT_STRING).intValue()==1;
        Random r = new Random();
        if(randomMove){
            if(headings.size()<3) headings.add(new Point2D(r.nextDouble()*ScrollableScene.FIELD_WIDTH, r.nextDouble()*ScrollableScene.FIELD_HEIGHT));
        }
        if (headings.size() == 0) {
            setAnimationDirection(getLocation(), headings.peek(), !(headings.size() == 0));
            this.updateImageLocation();
        }
        else {
            setAnimationDirection(getLocation(), headings.peek(), !(headings.size() == 0));
            if (!headings.peek().equals(getLocation())) {
                Point2D currentHeading = headings.peek();
                Point2D delta = new Point2D(currentHeading.getX()
                                            - getLocation().getX(), currentHeading.getY()
                                                                    - getLocation().getY());
                if (delta.magnitude() > speed)
                    delta = delta.normalize().multiply(speed);
                this.getGameElementState().setNumericalAttribute(X_VEL, delta.getX());
                this.getGameElementState().setNumericalAttribute(Y_VEL, delta.getY());
                this.getGameElementState().setNumericalAttribute(SelectableGameElementState.X_POS_STRING,this.getGameElementState().getNumericalAttribute(SelectableGameElementState.X_POS_STRING).doubleValue()+delta.getX());
                this.getGameElementState().setNumericalAttribute(SelectableGameElementState.Y_POS_STRING,this.getGameElementState().getNumericalAttribute(SelectableGameElementState.Y_POS_STRING).doubleValue()+delta.getY());
                this.updateImageLocation();
            }
            else {
                headings.poll();
                this.updateImageLocation();
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
                ((SelectableGameElementState) (this.getGameElementState()))
                        .getInteractingElements().get("CollidingElements");
        for (Evaluatable<Boolean> condition : getConditionActionPairs().keySet()) {
            for (DrawableGameElementState element : elementsOfInterest) {
                ElementPair elements = new ElementPair(this.getGameElementState(), element);
                if (condition.getValue(elements)) {
                    System.out
                            .println("Original X:" +
                                     this.getGameElementState()
                                             .getNumericalAttribute(DrawableGameElementState.X_POS_STRING));
                    getConditionActionPairs().get(condition).getValue(elements);
                    System.out
                            .println("New X:" +
                                     this.getGameElementState()
                                             .getNumericalAttribute(DrawableGameElementState.X_POS_STRING));
                    return;
                }
            }

        }
        System.out.println(elementsOfInterest.size());
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
        return this.isSelected;
    }

    public void setHeading(Point2D click) {
		this.headings.add(click);
	}
    
    public void clearHeadings(){
    	this.headings.clear();
    }

}
