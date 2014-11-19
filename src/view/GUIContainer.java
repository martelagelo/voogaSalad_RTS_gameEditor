package view;

import gamemodel.MainModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Superclass for any GUI pane that holds sub guipanes and needs to delegate info to its
 * subpanes. Handles updating from the model and delegating updates to its subpanes
 * 
 * @author Jonathan Tseng
 *
 */
public abstract class GUIContainer implements Observer, GUIController {

    private MainModel myMainModel;
    private List<GUIContainer> myChildContainers;

    public void setModel (MainModel model) {
        myMainModel = model;
    }

    protected void attachChildContainers (GUIContainer ... child) {
        checkAndCreateChildContainers();
        myChildContainers.addAll(new ArrayList<>(Arrays.asList(child)));
    }

    protected void clearChildContainers () {
        checkAndCreateChildContainers();
        myChildContainers.clear();
    }

    protected MainModel getMainModel () {
        return myMainModel;
    }

    @Override
    public final void update (Observable o, Object arg) {
        update();
        checkAndCreateChildContainers();
        myChildContainers.forEach( (child) -> child.update());
    }

    public abstract void update ();
    
    private void checkAndCreateChildContainers() {
        if (myChildContainers == null) {
            myChildContainers = new ArrayList<>();
        }
    }

}
