package engine.gameRepresentation.renderedRepresentation.attributeDisplayer;

import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.visuals.elementVisuals.widgets.attributeDisplays.AttributeDisplayer;

import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import model.state.gameelement.AttributeContainer;


/**
 * A class that uses the factory pattern to create attribute displayers given the game element to
 * attach to and the AttributeDisplayerState to display.
 * 
 * @author Zach
 *
 */
public class AttributeDisplayerFactory {
    public final static String ATTRIBUTE_DISPLAYER_CLASSES =
            "resources.properties.engine.AttributeDisplayTypes";
    public final static String ATTRIBUTE_CLASS_LOCATIONS =
    		"engine.visuals.elementVisuals.widgets.attributeDisplays.";
    private ResourceBundle myAttributeDisplayerBundle;

    public AttributeDisplayerFactory () {
        myAttributeDisplayerBundle = ResourceBundle.getBundle(ATTRIBUTE_DISPLAYER_CLASSES);

    }

    /**
     * Create an attribute displayer.
     * 
     * @param attributeDisplayerState the state of the attribute displayer
     * @param attachee the game element to attach the attribute displayer to.
     * @return
     */
    public AttributeDisplayer createAttributeDisplayer (AttributeDisplayerState attributeDisplayerState,
                                                        AttributeContainer attachee) {
        AttributeDisplayer displayer = null;
        Class<?> c = null;
        try {
            c =
                    Class.forName(ATTRIBUTE_CLASS_LOCATIONS +
                                  myAttributeDisplayerBundle
                                          .getString(attributeDisplayerState.displayerTag));
            displayer =
                    (AttributeDisplayer) c.getDeclaredConstructor(AttributeContainer.class,
                                                                  String.class,
                                                                  double.class,
                                                                  double.class)
                            .newInstance(attachee, attributeDisplayerState.numericParameterTag,
                                         attributeDisplayerState.minAttributeValue,
                                         attributeDisplayerState.maxAttributeValue);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | MissingResourceException | SecurityException e) {
            // Class doesn't exist. fail silently
        	e.printStackTrace();
        }
        return displayer;

    }
}
