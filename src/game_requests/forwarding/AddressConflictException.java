package game_requests.forwarding;

import game_requests.receiving.IReceiver;


/**
 * An exception capturing when an address conflicts with a pre-existing address
 */
public class AddressConflictException extends Exception {

    private static final long serialVersionUID = -3572949740426821224L;
    private String myConflictingAddress;
    private IReceiver myConflictingReceiver;

    /**
     * Create a new address conflict exception
     * 
     * @param conflictingAddress
     *        The address that conflicts with a pre-existing address
     * @param conflictingReceiver
     *        The receiver that already possesses that address
     */
    public AddressConflictException (String conflictingAddress,
                                     IReceiver conflictingReceiver) {
        super(conflictingAddress + " is already in use by "
              + conflictingReceiver);
        myConflictingAddress = conflictingAddress;
        myConflictingReceiver = conflictingReceiver;
    }

    /**
     * The conflicting address
     * 
     * @return The address that conflicts with a pre-existing address
     */
    public String conflictingAddress () {
        return myConflictingAddress;
    }

    /**
     * The conflicting receiver
     * 
     * @return The receiver that already possesses that address
     */
    public IReceiver conflictingReceiver () {
        return myConflictingReceiver;
    }
}
