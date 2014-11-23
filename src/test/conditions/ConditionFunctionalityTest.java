package test.conditions;

import static org.junit.Assert.*;
import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.evaluators.AdditionAssignmentEvaluator;
import game_engine.gameRepresentation.conditions.evaluators.Evaluator;
import game_engine.gameRepresentation.conditions.evaluators.LessThanEvaluator;
import game_engine.gameRepresentation.conditions.parameters.NumberParameter;
import game_engine.gameRepresentation.conditions.parameters.NumericalAttributeParameter;
import game_engine.gameRepresentation.conditions.parameters.objectIdentifiers.ActorObjectIdentifier;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;

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
	private GameElementState element1;
	private GameElementState element2;
	private NumericalAttributeParameter numAttrParam;
	private NumberParameter numberParam;

	/**
	 * Create some test game elements for us to work with in the test
	 */
	@Before
	public void initialize() {
		element1 = new GameElementState();
		element1.setNumericalAttribute("Health", 50);
		element2 = new GameElementState();
		numAttrParam = new NumericalAttributeParameter("Health", null,
				new ActorObjectIdentifier());
		numberParam = new NumberParameter(10d);
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

	@Test
	public void testAttributeIncrimenting() {
		Evaluator<?, ?, ?> evaluator = new AdditionAssignmentEvaluator<>(
				numAttrParam, numberParam);
		assertEquals(element1.getNumericalAttribute("Health"), 50);
		evaluator.getValue(new ElementPair(element1, element2));
		assertEquals(60d,element1.getNumericalAttribute("Health"));

	}

}
