package view;

import gamemodel.MainModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.fxml.FXML;


/**
 * Superclass for any GUI pane that holds sub guipanes and needs to delegate info to its
 * subpanes. Handles updating from the model and delegating updates to its subpanes
 * 
 * @author Jonathan Tseng
 *
 */
public abstract class GUIContainer implements Observer, GUIController {

    protected MainModel myMainModel;
    private List<GUIContainer> myChildContainers;

    @Override
    @FXML
    public final void initialize () {
        myChildContainers = new ArrayList<>();
        init();
    }

    protected abstract void init ();

    public void setModel (MainModel model) {
        myMainModel = model;
        myChildContainers.forEach( (child) -> {
            child.setModel(myMainModel);
        });
    }

    protected void attachChildContainers (GUIContainer ... children) {
        myChildContainers.addAll(new ArrayList<>(Arrays.asList(children)));
        myChildContainers.forEach( (child) -> {
            child.setModel(myMainModel);
        });
    }

    protected void clearChildContainers () {
        myChildContainers.clear();
    }

    @Override
    public final void update (Observable o, Object arg) {
        update();
        // myChildContainers.forEach( (child) -> child.update(o, arg));
    }

    public abstract void update ();

}
