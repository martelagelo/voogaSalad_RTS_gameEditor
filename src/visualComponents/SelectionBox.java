package visualComponents;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.shape.Rectangle;


public class SelectionBox extends Rectangle implements Observable {
    
    public SelectionBox(){
        super();
    }
    
    public void released(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY){
        // send this information to the 
        
        System.out.println("("+topLeftX+", "+topLeftY+") , ("+bottomRightX+", "+bottomRightY+")");
    }

    @Override
    public void addListener (InvalidationListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeListener (InvalidationListener listener) {
        // TODO Auto-generated method stub
        
    }    
}
