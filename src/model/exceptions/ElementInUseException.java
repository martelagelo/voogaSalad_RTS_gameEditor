package model.exceptions;

public class ElementInUseException extends Exception {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = -5810777800689652783L;

    private static final String MESSAGE_FORMAT = "Element, %s, currently exists in some level.";
    
    public ElementInUseException (String elementName) {
        super(String.format(MESSAGE_FORMAT, elementName));
    }

}
