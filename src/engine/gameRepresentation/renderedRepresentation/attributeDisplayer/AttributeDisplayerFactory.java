package engine.gameRepresentation.renderedRepresentation.attributeDisplayer;

import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.visuals.elementVisuals.widgets.attributeDisplays.AttributeDisplayer;

import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import model.state.gameelement.AttributeContainer;

/**
 * A class that uses the factory pattern to create attribute displayers given
 * the game element to attach to and the AttributeDisplayerState to display.
 * 
 * @author Zach, Stanley
 *
 */
public class AttributeDisplayerFactory {
	public final static String ATTRIBUTE_CLASS_LOCATIONS = "engine.visuals.elementVisuals.widgets.attributeDisplays.";

	/**
	 * Create an attribute displayer.
	 * 
	 * @param attributeDisplayerState
	 *            the state of the attribute displayer
	 * @param attachee
	 *            the game element to attach the attribute displayer to.
	 * @return
	 */
	public AttributeDisplayer createAttributeDisplayer(
			AttributeDisplayerState attributeDisplayerState,
			AttributeContainer attachee) {
		Class<?> c = null;
		try {
		    c = Class.forName(ATTRIBUTE_CLASS_LOCATIONS
					+ attributeDisplayerState.displayerTag);
		} catch (ClassNotFoundException e) {
			// fail silently
			//e.printStackTrace();
		}
		if (attributeDisplayerState.myTextValue != null) {
			return createTextualAttributeDisplayer(c, attributeDisplayerState,
					attachee);
		} else {
			return createNumericalAttributeDisplayer(c,
					attributeDisplayerState, attachee);
		}

	}

	private AttributeDisplayer createNumericalAttributeDisplayer(Class c,
			AttributeDisplayerState attributeDisplayerState,
			AttributeContainer attachee) {
		AttributeDisplayer displayer = null;
		try {
			displayer = (AttributeDisplayer) c.getDeclaredConstructor(
					AttributeContainer.class, String.class, double.class,
					double.class).newInstance(attachee,
					attributeDisplayerState.parameterTag,
					attributeDisplayerState.minAttributeValue,
					attributeDisplayerState.maxAttributeValue);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// fail silently
			e.printStackTrace();
		}
		return displayer;
	}

	private AttributeDisplayer createTextualAttributeDisplayer(Class c,
			AttributeDisplayerState attributeDisplayerState,
			AttributeContainer attachee) {
		AttributeDisplayer displayer = null;
		try {
			displayer = (AttributeDisplayer) c.getDeclaredConstructor(
					AttributeContainer.class, String.class, String.class)
					.newInstance(attachee,
							attributeDisplayerState.parameterTag,
							attributeDisplayerState.myTextValue);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// fail silently
		}
		return displayer;
	}
}
