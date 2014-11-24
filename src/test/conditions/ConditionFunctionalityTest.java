package test.conditions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.evaluators.AdditionAssignmentEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.CollisionEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.EqualsAssignmentEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.LessThanEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.SubtractionAssignmentEvaluator;
import game_engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import game_engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import game_engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import java.util.HashMap;
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
	public void initialize() {
		DrawableGameElementState state1 = new DrawableGameElementState(0, 0);
		state1.setNumericalAttribute("Health", 50d);
		double[] bounds1 = { 0, 0, 0, 10, 10, 10, 10, 0 };
		state1.setBounds(bounds1);
		myElement1 = new DrawableGameElement(state1,new HashMap<>());
		DrawableGameElementState state2 = new DrawableGameElementState(0, 0);
		state2.setNumericalAttribute("Health", 20d);
		double[] bounds2 = { 0, 0, 0, 1, 1, 1, 1, 0 };
		state2.setBounds(bounds2);
		myElement2 = new DrawableGameElement(state2,new HashMap<>());
		myNumAttrParam = new NumericAttributeParameter("Health", null,
				new ActorObjectIdentifier());
		myNumberParam = new NumberParameter(10d);
		myElementParam1 = new GameElementParameter(new ActorObjectIdentifier(),
				null);
		myElementParam2 = new GameElementParameter(new ActeeObjectIdentifier(),
				null);
		myElementPair = new ElementPair(myElement1.getState(), myElement2.getState());
	}

	/**
	 * Test the ability to make an evaluatable based on the types of its
	 * parameter. Test the workaround of generic's inability to identify type.
	 */
	@Test
	public void testGenericTypeIdentification() {
		NumberParameter testParam = new NumberParameter(Double.valueOf(49));
		NumberParameter testParam2 = new NumberParameter(Double.valueOf(50));
		Evaluator<?, ?, ?> evaluator = new LessThanEvaluator<>(testParam,
				testParam2);
		assertTrue((Boolean) evaluator.getValue());
		testParam.setValue(52d);
		assertFalse((Boolean) evaluator.getValue());
	}

	/**
	 * Test basic actions that set an attribute value in the evaluatable
	 */
	@Test
	public void testAttributeIncrimenting() {
		Evaluator<?, ?, ?> evaluator = new AdditionAssignmentEvaluator<>(
				myNumAttrParam, myNumberParam);
		assertEquals(50d, myElement1.getState()
				.getNumericalAttribute("Health"));
		evaluator.getValue(myElementPair);
		assertEquals(60d, myElement1.getState()
				.getNumericalAttribute("Health"));
		Evaluator<?,?,?> evaluator2 = new SubtractionAssignmentEvaluator<>(myNumAttrParam,myNumAttrParam);
		evaluator2.getValue(myElementPair);
		assertEquals(0d, myElement1.getState()
                             .getNumericalAttribute("Health"));

	}

	@Test
	public void testValueAssignment() {
		Evaluator<?, ?, ?> evaluator = new EqualsAssignmentEvaluator<>(
				myNumAttrParam, myNumberParam);
		evaluator.getValue(myElementPair);
		assertEquals(myNumberParam.getValue(), myElement1.getState()
				.getNumericalAttribute("Health"));
	}

	@Test
	public void testCollisions() {
		Evaluator<?, ?, ?> evaluator = new CollisionEvaluator<>(
				myElementParam1, myElementParam2);
		// First make sure the pre-set collision boxes collide
		assertTrue((Boolean) (evaluator.getValue(myElementPair)));
		// Now make sure if we give far away bounds, they don't intersect
		double[] nonIntersectingBounds = { 100, 100, 200, 100, 200, 200, 100,
				200 };
		((DrawableGameElementState) myElement2.getState())
				.setBounds(nonIntersectingBounds);
		assertFalse((Boolean) (evaluator.getValue(myElementPair)));
		// Now move the game element to the other bounds location and make sure
		// they intersect
		myElement1.getState().setNumericalAttribute(
				DrawableGameElementState.X_POS_STRING, 100);
		myElement1.getState().setNumericalAttribute(
				DrawableGameElementState.Y_POS_STRING, 100);
		assertTrue((Boolean) (evaluator.getValue(myElementPair)));

	}
}
