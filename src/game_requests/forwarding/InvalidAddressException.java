package game_requests.forwarding;

/**
 * An exception capturing when an invalid address has been used
 */
public class InvalidAddressException extends Exception {

    private static final long serialVersionUID = 7207103882222096620L;
    private String myAddress;
    private String myReason;

    /**
     * Create a new invalid address exception
     * 
     * @param address The invalid address
     * @param reason The reason the address is invalid
     */
    public InvalidAddressException (String address, String reason) {
        super(address + " is invalid: " + reason);
        myAddress = address;
        myReason = reason;
    }

    /**
     * Get the invalid address
     * 
     * @return The invalid address
     */
    public String address () {
        return myAddress;
    }

    /**
     * Get the reason the address is invalid
     * 
     * @return The reason the address is invalid
     */
    public String reason () {
        return myReason;
    }
}
