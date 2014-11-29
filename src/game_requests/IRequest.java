package game_requests;

import java.util.Map;

/**
 * A request that can be sent between receivers using a forwarder
 */
public interface IRequest {

    /**
     * Get the sender
     * 
     * @return The address of the sender
     */
    public String sender ();

    /**
     * Get the recipient
     * 
     * @return The address of the recipient
     */
    public String receiver ();

    /**
     * Get the message
     * 
     * @return A map of attributes to values representing the message
     */
    public Map<String, String> message ();
}
