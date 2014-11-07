package vooga.request.forwarder;

import vooga.request.IRequest;

/**
 * An exception capturing when an error occurred during delivery of an request
 * to a known receiver
 */
public class DeliveryException extends Exception {

    private static final long serialVersionUID = 7788838511248429280L;

    private IRequest myRequest;
    private String myReason;
    /**
     * Create a new delivery exception
     * 
     * @param request The request that failed to deliver
     * @param reason The reason the delivery failed
     */
    public DeliveryException(IRequest request, String reason){
        super("Delivery of " + request + " failed, because: " + reason);
        myRequest = request;
        myReason = reason;
    }
    /**
     * Get the request
     * 
     * @return The request that failed to deliver
     */
    public IRequest request(){
        return myRequest;
    }
    /**
     * Get the reason
     * 
     * @return The reason the delivery failed
     */
    public String reason(){
        return myReason;
    }
}
