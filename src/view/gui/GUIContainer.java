package view.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.fxml.FXML;
import model.MainModel;
import model.state.gameelement.AttributeContainer;


/**
 * Superclass for any GUI pane that holds sub guipanes and needs to delegate
 * info to its subpanes. Handles updating from the model and delegating updates
 * to its subpanes
 * 
 * @author Jonathan Tseng, Nishad Agrawal
 *
 */
public abstract class GUIContainer extends Observable implements Observer, GUIController {

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
        if (o instanceof MainModel) {
            modelUpdate();
        }
        else { // o instanceof Engine
            engineUpdate((AttributeContainer) arg);
        }
        myChildContainers.forEach( (child) -> child.update(o, arg));
    }

    public void engineUpdate (AttributeContainer attributes) {
        // default implementation: do nothing
    }

    public abstract void modelUpdate ();

}
