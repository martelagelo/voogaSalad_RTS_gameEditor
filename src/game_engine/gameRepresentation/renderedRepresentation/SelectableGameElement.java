package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;


/**
 * 
 * @author Jonathan Tseng, Steve, Nishad, Rahul, John L.
 *
 */
public class SelectableGameElement extends DrawableGameElement {

    // private SelectableGameElementState myState;
    private Point2D heading;
    private boolean selected;
    // TODO temporary, should be in the attributes
    private double speed = 3;

    public SelectableGameElement (DrawableGameElementState element) {
        super(element);
        this.selected = false;
        // TODO Auto-generated constructor stub
    }

    public String getType () {
        return getState().getType();
        // return myState.getType();
    }

    public void select (boolean select) {
        selected = select;
        myAnimation.select(select);
    }

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
        // TODO Auto-generated method stub
        List<Entry<Evaluatable, Action>> applicableConditionActionPairs =
                getApplicableConditionActionPairs("ObjectiveCondition");
    }

    public void updateSelfDueToSelection () {
        // TODO Auto-generated method stub
        // update representation?
        // visualRepresentation.setSelected();
        List<Entry<Evaluatable, Action>> applicableConditionActionPairs =
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
    private List<Entry<Evaluatable, Action>> getApplicableConditionActionPairs (String conditionActionPairIdentifier) {
        // return this.myConditionActionPairs.entrySet().stream()
        // .filter(o -> o.getKey().getType().equals(conditionActionPairIdentifier))
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
