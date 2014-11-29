package test.conditions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game_engine.gameRepresentation.evaluatables.Action;
import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.evaluators.AddEvaluatableEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.AdditionAssignmentEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.CollisionEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.EqualsAssignmentEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.LessThanEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.RemoveEvaluatableEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.SubtractionAssignmentEvaluator;
import game_engine.gameRepresentation.evaluatables.parameters.ActionParameter;
import game_engine.gameRepresentation.evaluatables.parameters.EvaluatableIDParameter;
import game_engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import game_engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import game_engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import game_engine.visuals.Dimension;
import game_engine.visuals.Spritesheet;
import java.util.HashMap;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;


/**
 * A class used to test the construction and functionality of conditions and
 * actions
 * 
 * @author Zach
 *
 */
public class ConditionFunctionalityTest {
    private DrawableGameElement myElement1;
    private DrawableGameElement myElement2;
    private NumericAttributeParameter myNumAttrParam;
    private NumberParameter myNumberParam;
    private GameElementParameter myElementParam1;
    private GameElementParameter myElementParam2;
    private ElementPair myElementPair;

    /**
     * Create some test game elements for us to work with in the test
     */
    @Before
    public void initialize () {
        // Make a spritesheet to prevent error throwing
        Spritesheet spritesheet =
                new Spritesheet("resources/img/Red_Arrow_Down.png", new Dimension(50, 50), 1);
        // Create game elements
        DrawableGameElementState state1 = new DrawableGameElementState(0, 0);
        state1.setSpritesheet(spritesheet);
        state1.setNumericalAttribute("Health", 50d);
        double[] bounds1 = { 0, 0, 0, 10, 10, 10, 10, 0 };
        state1.setBounds(bounds1);
        myElement1 = new DrawableGameElement(state1, new HashMap<>());
        DrawableGameElementState state2 = new DrawableGameElementState(0, 0);
        state2.setNumericalAttribute("Health", 20d);
        double[] bounds2 = { 0, 0, 0, 1, 1, 1, 1, 0 };
        state2.setSpritesheet(spritesheet);
        state2.setBounds(bounds2);
        myElement2 = new DrawableGameElement(state2, new HashMap<>());
        myNumAttrParam = new NumericAttributeParameter("", "Health", null,
                                                       new ActorObjectIdentifier());
        myNumberParam = new NumberParameter("", 10d);
        myElementParam1 = new GameElementParameter("", new ActorObjectIdentifier(),
                                                   null);
        myElementParam2 = new GameElementParameter("", new ActeeObjectIdentifier(),
                                                   null);
        myElementPair = new ElementPair(myElement1, myElement2);
    }

    /**
     * Test the ability to make an evaluatable based on the types of its
     * parameter. Test the workaround of generic's inability to identify type.
     */
    @Test
    public void testGenericTypeIdentification () {
        NumberParameter testParam = new NumberParameter("", Double.valueOf(49));
        NumberParameter testParam2 = new NumberParameter("", Double.valueOf(50));
        Evaluator<?, ?, ?> evaluator = new LessThanEvaluator<>("", testParam,
                                                               testParam2);
        assertTrue((Boolean) evaluator.evaluate());
        testParam.setValue(52d);
        assertFalse((Boolean) evaluator.evaluate());
    }

    /**
     * Test basic actions that set an attribute value in the evaluatable
     */
    @Test
    public void testAttributeIncrimenting () {
        Evaluator<?, ?, ?> evaluator =
                new AdditionAssignmentEvaluator<>("",
                                                  myNumAttrParam, myNumberParam);
        assertEquals(50d, myElement1
                .getNumericalAttribute("Health"));
        evaluator.evaluate(myElementPair);
        assertEquals(60d, myElement1
                .getNumericalAttribute("Health"));
        Evaluator<?, ?, ?> evaluator2 =
                new SubtractionAssignmentEvaluator<>("", myNumAttrParam, myNumAttrParam);
        evaluator2.evaluate(myElementPair);
        assertEquals(0d, myElement1
                .getNumericalAttribute("Health"));

    }

    @Test
    public void testValueAssignment () {
        Evaluator<?, ?, ?> evaluator =
                new EqualsAssignmentEvaluator<>("",
                                                myNumAttrParam, myNumberParam);
        evaluator.evaluate(myElementPair);
        assertEquals(myNumberParam.evaluate(), myElement1
                .getNumericalAttribute("Health"));
    }

    @Test
    public void testCollisions () {
        Evaluator<?, ?, ?> evaluator = new CollisionEvaluator<>("",
                                                                myElementParam1, myElementParam2);
        // First make sure the pre-set collision boxes collide
        assertTrue((Boolean) (evaluator.evaluate(myElementPair)));
        // Now move the game element to the a far away location and make sure
        // they don't intersect
        myElement1.setNumericalAttribute(
                                       StateTags.X_POS_STRING, 100);
        myElement1.setNumericalAttribute(
                                       StateTags.Y_POS_STRING, 100);
        assertFalse((Boolean) (evaluator.evaluate(myElementPair)));

    }

    /**
     * Test the addition of an action to an element then a removal of said element
     */
    @Test
    public void testRemoveAction () {
        // Add and remove an element
        NumberParameter testParam = new NumberParameter("asdasdf", Double.valueOf(49));
        NumberParameter testParam2 = new NumberParameter("asdasdf", Double.valueOf(50));
        Evaluator<?, ?, ?> evaluator = new LessThanEvaluator<>("asdasdf", testParam,
                                                               testParam2);
        myElement1.addAction("test", evaluator);
        assertEquals(1, countIteratorElements(myElement1.getActionsOfType("test")));
        EvaluatableIDParameter param = new EvaluatableIDParameter("asdasdf");
        Evaluator<?, ?, ?> removeAction =
                new RemoveEvaluatableEvaluator<>("otherRandomString", myElementParam1, param);
        removeAction.evaluate(myElementPair);
        assertEquals(0, countIteratorElements(myElement1.getActionsOfType("test")));
        // Add an element and try to remove an element that doesn't exist
        myElement1.addAction("test", evaluator);
        EvaluatableIDParameter param2 = new EvaluatableIDParameter("hello");
        Evaluator<?, ?, ?> removeAction2 =
                new RemoveEvaluatableEvaluator<>("", myElementParam1, param2);
        removeAction2.evaluate(myElementPair);
        assertEquals(1, countIteratorElements(myElement1.getActionsOfType("test")));

    }

    /**
     * Test the addition of an action to an object then the removal of that action
     */
    @Test
    public void testAddAction () {
        // Make sure our test element has no attributes
        assertEquals(0, countIteratorElements(myElement1.getActionsOfType("test")));
        // Create an evaluator that on execution adds an evaluator to remove itself.
        // Evaluatorception??
        EvaluatableIDParameter param = new EvaluatableIDParameter("tag");
        Evaluator<?, ?, ?> removeAction =
                new RemoveEvaluatableEvaluator<>("tag", myElementParam1, param);
        ActionParameter actionParam = new ActionParameter("tag", new Action("test", removeAction));
        Evaluator<?, ?, ?> addAction =
                new AddEvaluatableEvaluator<>("tag", myElementParam1, actionParam);
        // Evaluate the add action evaluatable and ensure the action is added
        addAction.evaluate(myElementPair);
        assertEquals(1, countIteratorElements(myElement1.getActionsOfType("test")));
        // Execute the added action (which itself is a remove action and ensure it properly removes
        // the action
        myElement1.getActionsOfType("test").next().evaluate(myElementPair);
        assertEquals(0,countIteratorElements(myElement1.getActionsOfType("test")));

    }

    /**
     * Count the number of elements in a given iterator
     */
    private int countIteratorElements (Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }
}
