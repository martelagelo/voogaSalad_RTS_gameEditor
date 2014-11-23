package test.conditions;

import game_engine.gameRepresentation.conditions.NullElementPair;
import game_engine.gameRepresentation.conditions.evaluators.LessThanEvaluator;
import game_engine.gameRepresentation.conditions.parameters.NumberParameter;

import org.junit.Test;

/**
 * A class used to test the construction and functionality of conditions and actions
 * @author Zach
 *
 */
public class ConditionFunctionalityTest {
	/**
	 * Test the ability to make an evaluatable based on the types of its parameter. Test the workaround of generic's inability to identify type.
	 */
	@Test
	public void testGenericTypeIdentification(){
		NumberParameter testParam = new NumberParameter(Double.valueOf(45));
		NumberParameter testParam2 = new NumberParameter(Double.valueOf(50));
		LessThanEvaluator<Double,Double> evaluator = new LessThanEvaluator<>(testParam,testParam2);
		System.out.println(evaluator.evaluate(new NullElementPair(), Double.class,Double.class,testParam, testParam2));

		

	}

}
