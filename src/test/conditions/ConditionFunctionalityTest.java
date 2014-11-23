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
import game_engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import game_engine.gameRepresentation.evaluatables.parameters.NumberParameter;
import game_engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;

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
	private DrawableGameElement element1;
	private DrawableGameElement element2;
	private NumericAttributeParameter numAttrParam;
	private NumberParameter numberParam;
	private GameElementParameter elementParam1;
	private GameElementParameter elementParam2;

	/**
	 * Create some test game elements for us to work with in the test
	 */
	@Before
	public void initialize() {
		DrawableGameElementState state1 = new DrawableGameElementState(0, 0);
		state1.setNumericalAttribute("Health", 50d);
		double[] bounds1 = { 0, 0, 0, 10, 10, 10, 10, 0 };
		state1.setBounds(bounds1);
		element1 = new DrawableGameElement(state1);
		DrawableGameElementState state2 = new DrawableGameElementState(0, 0);
		state2.setNumericalAttribute("Health", 20d);
		double[] bounds2 = { 0, 0, 0, 1, 1, 1, 1, 0 };
		state2.setBounds(bounds2);
		element2 = new DrawableGameElement(state2);
		numAttrParam = new NumericAttributeParameter("Health", null,
				new ActorObjectIdentifier());
		numberParam = new NumberParameter(10d);
		elementParam1 = new GameElementParameter(new ActorObjectIdentifier(),
				null);
		elementParam2 = new GameElementParameter(new ActeeObjectIdentifier(),
				null);
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
				numAttrParam, numberParam);
		assertEquals(50d,
				element1.getGameElementState().getNumericalAttribute("Health"));
		evaluator.getValue(new ElementPair(element1, element2));
		assertEquals(60d,
				element1.getGameElementState().getNumericalAttribute("Health"));

	}

	@Test
	public void testValueAssignment() {
		Evaluator<?, ?, ?> evaluator = new EqualsAssignmentEvaluator<>(
				numAttrParam, numberParam);
		evaluator.getValue(new ElementPair(element1, element2));
		assertEquals(numberParam.getValue(), element1.getGameElementState()
				.getNumericalAttribute("Health"));
	}

	@Test
	public void testCollisions() {
		Evaluator<?, ?, ?> evaluator = new CollisionEvaluator<>(elementParam1,
				elementParam2);
		assertTrue((Boolean) (evaluator.getValue(new ElementPair(element1,
				element2))));
		double[] nonIntersectingBounds = { 100, 100, 200, 100, 200, 200, 100,
				200 };
		((DrawableGameElementState) element2.getGameElementState())
				.setBounds(nonIntersectingBounds);
		assertFalse((Boolean) (evaluator.getValue(new ElementPair(element1,
				element2))));

	}
}
