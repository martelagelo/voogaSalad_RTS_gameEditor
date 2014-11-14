package test.request;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import vooga.request.IRequest;
import vooga.request.Request;
import vooga.request.forwarder.AddressConflictException;
import vooga.request.forwarder.DeliveryException;
import vooga.request.forwarder.IForwarder;
import vooga.request.forwarder.InvalidAddressException;
import vooga.request.forwarder.ReceiverNotFoundException;
import vooga.request.forwarder.RequestMediator;

public class RequestMediatorTest {

    IForwarder myMediator;
    List<ReceiverTestStub> myReceivers;

    public void setup(int receiverCount, int pingPongMax) throws InvalidAddressException, AddressConflictException{
        myMediator = new RequestMediator();
        myReceivers = new ArrayList<>();
        for (int i = 0; i < receiverCount; i++){
            myReceivers.add(i, new ReceiverTestStub(myMediator, pingPongMax));
            myMediator.register(Integer.toString(i), myReceivers.get(i));
        }
    }

    /**
     * Test sending to the same receiver
     */
    @Test
    public void testSendToSelf()
            throws InvalidAddressException, AddressConflictException, ReceiverNotFoundException, DeliveryException{
        setup(1, 0);

        Map<String, String> message = new HashMap<>();
        message.put("hi", "there");
        IRequest request = new Request("0", "0", message);
        myMediator.forward(request);
        assertEquals("there", myReceivers.get(0).lastRequest().message().get("hi"));
    }

    /**
     * Test if a message can be passed back and forth between two receivers
     *  in the correct order.
     * <br><br>
     * ReceiverTestStub contains special "ping pong" logic where if the
     * special key "pingPong" is in the message, the ReceiverTestStub will
     * send back a new message to the sender, incrementing a counter until
     * it reaches a certain value.
     */
    @Test
    public void testSendPingPong() throws InvalidAddressException, AddressConflictException, ReceiverNotFoundException, DeliveryException {
        setup(2, 2);

        Map<String, String> initialMessage = new HashMap<>();
        // we pretend we're in Receiver 0; he receives a pingPong message
        // and increments the pingPong counter by 1
        initialMessage.put("pingPong", "1");
        // we send new message to Receiver 1
        IRequest startingRequest = new Request("0", "1", initialMessage);
        // Receiver 1 receives the message ("1"), and saves that message
        // as the last message
        // Receiver 1 increments the counter and sends back "2" to Receiver 0
        // Receiver 0 receives the message ("2"), and saves that message
        // as the last message
        // Receiver 0 checks and sees that the pingPong limit, 2, has been
        // reached, and so it doesn't send a message back
        myMediator.forward(startingRequest);
        // Receiver 1's last message should be the one sent by Receiver 0 the first time
        // (startingRequest)
        assertEquals("1", myReceivers.get(1).lastRequest().message().get("pingPong"));
        // Receiver 0's last message should be the one that Receiver 1 sent back in response
        // to startingRequest
        assertEquals("2", myReceivers.get(0).lastRequest().message().get("pingPong"));
    }

    /**
     * Tests that adding two identical addresses actually creates the right exception
     */
    @Test(expected=AddressConflictException.class)
    public void testAddressConflictException() throws InvalidAddressException, AddressConflictException{
        try {
            setup(0, 0);
        }
        catch (Exception e) {
        }
        try {
            myMediator.register("1", null);
        }
        catch (Exception e) {
        }
        try {
            myMediator.register("1", null);
        }
        catch (AddressConflictException e) {
            assertEquals("1", e.conflictingAddress());
            throw e;
        }
    }

    @Test(expected=ReceiverNotFoundException.class)
    public void testReceiverNotFoundException() throws InvalidAddressException, AddressConflictException, DeliveryException, ReceiverNotFoundException{
        setup(1, 0);
        try {
            myMediator.forward(new Request("1", "0", new HashMap<>()));
        }
        catch (ReceiverNotFoundException e) {
            assertEquals("1", e.address());
            throw e;
        }
    }
}
