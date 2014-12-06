package util;

import java.util.ArrayList;

/**
 * This class template was written for the ability to jsonify a List
 * using our existing SaveLoadUtility. It is currently used to store
 * and load the existing games created by the user within our project.
 * 
 * @author Nishad Agrawal (nna6)
 *
 * @param <T>
 */
public class JSONableList<T> extends ArrayList<T> implements JSONable{

    /**
     * 
     */
    private static final long serialVersionUID = 3844376686884183253L;

}
