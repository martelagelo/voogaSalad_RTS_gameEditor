package game_requests.receiving;

import game_requests.IRequest;


/**
 * An exception encapsulating when a sender is not recognized as known or valid
 */
public class UnknownSenderException extends Exception {

    private String mySender;
    private IRequest myRequest;

    /**
     * Create a new unknown sender exception
     * 
     * @param sender The address of the sender that is unknown
     * @param request The sender's request
     */
    public UnknownSenderException (String sender, IRequest request) {
        super(sender + " is not a recognized sender for request: " + request);
        mySender = sender;
        myRequest = request;
    }

    /**
     * Get the sender
     * 
     * @return The address of the sender
     */
    public String sender () {
        return mySender;
    }

    /**
     * Get the request
     * 
     * @return The request associated with the sender
     */
    public IRequest request () {
        return myRequest;
    }
}
