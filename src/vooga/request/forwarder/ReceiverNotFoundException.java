package vooga.request.forwarder;

/**
 * An exception encapsulating when a receiver address could not be resolved
 * to a receiver 
 */
public class ReceiverNotFoundException extends Exception {

    private static final long serialVersionUID = 2016239501767275842L;

    private String myAddress;

    /**
     * Create a new receiver not found exception
     * 
     * @param address The address that could not be resolved
     */
    public ReceiverNotFoundException(String address){
        super(address + " could not be found");
        myAddress = address;
    }

    /**
     * Get the address
     * @return The address that could not be resolved
     */
    public String address(){
        return myAddress;
    }
}
