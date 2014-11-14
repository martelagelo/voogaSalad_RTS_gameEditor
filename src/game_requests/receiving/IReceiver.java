package game_requests.receiving;

import game_requests.IRequest;

/**
 * An interface which can receive requests
 */
public interface IReceiver {

	/**
	 * Receive a request
	 * 
	 * @param request
	 *            The request to receive
	 * @throws UnknownSenderException
	 *             If the receiver does not recognize the sender as a known and
	 *             valid sender
	 */
	public void receive(IRequest request) throws UnknownSenderException;
}
