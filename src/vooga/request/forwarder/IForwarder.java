package vooga.request.forwarder;

import vooga.request.IRequest;
import vooga.request.receiver.IReceiver;

/**
 * An interface which forwards IRequests between registered IReceivers.
 */
public interface IForwarder {
    /**
     * Forward a request to a recipient based on the details of that request
     * 
     * @param request The request to forward
     * @throws ReceiverNotFoundException If the receiver is not registered with this forwarder
     * @throws DeliveryException If a problem occurs during delivery of the message to a receiver
     */
    public void forward(IRequest request) throws ReceiverNotFoundException, DeliveryException;
    /**
     * Register a receiver that can receive messages via forward()
     * 
     * @param address The address where the receiver can receive requests
     * @param receiver The receiver associated with the address
     * @throws InvalidAddressException If the format of the address is not allowed by the forwarder
     * @throws AddressConflictException If the address is already used by another receiver
     */
    public void register(String address, IReceiver receiver) throws InvalidAddressException, AddressConflictException;
}
