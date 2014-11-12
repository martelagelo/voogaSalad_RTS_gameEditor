package test.request;

import game_requests.IRequest;
import game_requests.Request;
import game_requests.forwarding.DeliveryException;
import game_requests.forwarding.IForwarder;
import game_requests.forwarding.InvalidAddressException;
import game_requests.forwarding.ReceiverNotFoundException;
import game_requests.receiving.IReceiver;
import game_requests.receiving.UnknownSenderException;
import java.util.Map;


/**
 * Stub testing class for testing the receiver functionality
 */
public class ReceiverTestStub implements IReceiver {

    private IRequest myLastRequest;
    private IForwarder myMediator;
    private int myPingPongCountMax;
    private static final String pingPongLabel = "pingPong";

    public ReceiverTestStub (IForwarder mediator, int pingPongMax) {
        myMediator = mediator;
        myPingPongCountMax = pingPongMax;
    }

    /**
     * Receive here will pass back a new request to the sender of the original message
     * as long as the number of "pings"/"pongs" hasn't exceeded a certain value
     */
    @Override
    public void receive (IRequest request) throws UnknownSenderException {
        myLastRequest = request;
        if (request.message().containsKey(pingPongLabel)) {
            int curPingPongIteration = Integer.parseInt(request.message().get(pingPongLabel));
            if (curPingPongIteration < myPingPongCountMax) {
                Map<String, String> message = request.message();
                message.put(pingPongLabel, Integer.toString(curPingPongIteration + 1));
                IRequest newRequest = new Request(request.receiver(), request.sender(), message);
                try {
                    myMediator.forward(newRequest);
                }
                catch (InvalidAddressException | ReceiverNotFoundException | DeliveryException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public IRequest lastRequest () {
        return myLastRequest;
    }
}
