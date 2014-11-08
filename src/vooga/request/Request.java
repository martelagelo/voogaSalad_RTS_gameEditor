package vooga.request;

import java.util.HashMap;
import java.util.Map;

public class Request implements IRequest{

    private String mySender;
    private String myReceiver;
    private Map<String, String> myMessage;

    public Request (String sender, String receiver, Map<String, String> message) {
        mySender = sender;
        myReceiver = receiver;
        myMessage = new HashMap<>(message);
    }
    @Override
    public String sender () {
        return mySender;
    }

    @Override
    public String receiver () {
        return myReceiver;
    }

    @Override
    public Map<String, String> message () {
        return new HashMap<>(myMessage);
    }

}
