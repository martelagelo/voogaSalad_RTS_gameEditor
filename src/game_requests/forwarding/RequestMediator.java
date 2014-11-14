package game_requests.forwarding;

import game_requests.IRequest;
import game_requests.receiving.IReceiver;
import game_requests.receiving.UnknownSenderException;
import java.util.HashMap;
import java.util.Map;

/**
 * The main request manager class which forwards messages between game
 * components
 */
public class RequestMediator implements IForwarder {

	private Map<String, IReceiver> myAddresses;

	public RequestMediator() {
		myAddresses = new HashMap<>();
	}

	@Override
	public void forward(IRequest request) throws InvalidAddressException,
			ReceiverNotFoundException, DeliveryException {
		checkValidAddress(request.sender());
		checkKnownAddress(request.sender());
		checkValidAddress(request.receiver());
		checkKnownAddress(request.receiver());
		try {
			myAddresses.get(request.receiver()).receive(request);
		} catch (UnknownSenderException e) {
			throw new DeliveryException(request, e.getMessage());
		}
	}

	@Override
	public void register(String address, IReceiver receiver)
			throws InvalidAddressException, AddressConflictException {
		checkValidAddress(address);
		if (isKnownAddress(address)) {
			throw new AddressConflictException(address,
					myAddresses.get(address));
		}
		myAddresses.put(address, receiver);
	}

	private void checkValidAddress(String address)
			throws InvalidAddressException {
		return;
	}

	private void checkKnownAddress(String address)
			throws ReceiverNotFoundException {
		if (!isKnownAddress(address)) {
			throw new ReceiverNotFoundException(address);
		}
	}

	private boolean isKnownAddress(String address) {
		return myAddresses.containsKey(address);
	}
}
