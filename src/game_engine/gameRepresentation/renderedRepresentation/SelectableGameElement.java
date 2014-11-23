package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;

import java.util.List;
import java.util.Map.Entry;

import javafx.geometry.Point2D;



/**
 * A wrapper for game elements capable of being selected. Adds a "selected" visual appearance to the
 * appearance defined by the DrawableGameElement and handles animations for actions resulting from
 * being selected.
 *
 * @author Jonathan , Steve, Nishad, Rahul, John
 *
 */
public class SelectableGameElement extends DrawableGameElement {


    private boolean isSelected;
//    private SelectableGameElementState myState;
    private Point2D heading;
    private boolean selected;
    // TODO temporary, should be in the attributes
    private double speed = 3;

    /**
     * @see DrawableGameElementState
     */
    public SelectableGameElement (DrawableGameElementState element) {
        super(element);

        this.isSelected = false;
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
        myAnimation.select(select);
    }

    /**
     * Update the object due to internal influences then update the object due to collisions,
     * visible objects, and the current objective of the object
     */
    @Override
    public void update () {
        super.update();
        updateSelfDueToCollisions();
        updateSelfDueToVisions();
        updateSelfDueToCurrentObjective();
        move();
    }

    private void move () {
        if (heading == null) heading = getLocation();
        if (!heading.equals(getLocation())) {
            Point2D delta =
                    new Point2D(heading.getX() - getLocation().getX(), heading.getY() -
                                                                       getLocation().getY());
            if (delta.magnitude() > speed) delta = delta.normalize().multiply(speed);
            this.setLocation(getLocation().add(delta));
        }
    }

    private Point2D getLoc () {
        return new Point2D(getState().getNumericalAttribute(DrawableGameElementState.X_POS_STRING)
                .doubleValue(), getState()
                .getNumericalAttribute(DrawableGameElementState.Y_POS_STRING)
                .doubleValue());
    }

    private void setLoc (Point2D location) {
        getState().setNumericalAttribute(DrawableGameElementState.X_POS_STRING, location.getX());
        getState().setNumericalAttribute(DrawableGameElementState.Y_POS_STRING, location.getY());

    }

    private void updateSelfDueToCurrentObjective () {
        getApplicableConditionActionPairs("ObjectiveCondition");
    }

    public void updateSelfDueToSelection () {
        getApplicableConditionActionPairs("SelfCondition");
    }

    private void updateSelfDueToVisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset("VisionCondition",
                "VisibleElements");
    }

    private void updateSelfDueToCollisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset("CollisionCondition",
                "CollidingElements");
    }

    // TODO FIX THIS SHIT
    private void evaluateConditionActionPairsOnInteractingElementsSubset (String conditionActionPairIdentifier,
                                                                          String elementIdentifier) {

        // List<Entry<Evaluatable, Action>> applicableConditionActionPairs =
        // getApplicableConditionActionPairs(conditionActionPairIdentifier);
        // if (myState.getInteractingElements().containsKey(elementIdentifier)) {
        // for (DrawableGameElementState element : myState.getInteractingElements()
        // .get(elementIdentifier)) {
        // List<GameElementState> immediatelyInteractingElements =
        // new ArrayList<GameElementState>();
        // for (Entry<Evaluatable, Action> conditionActionPair : applicableConditionActionPairs) {
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
        return this.selected;
    }

    public void setHeading (Point2D click) {
        this.heading = click;
    }

}
