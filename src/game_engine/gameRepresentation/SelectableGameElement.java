package game_engine.gameRepresentation;

import game_engine.computers.boundsComputers.Sighted;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class SelectableGameElement extends DrawableGameElement implements Sighted {

    protected GridPane abilityRepresentation;
    protected Node informationRepresentation;

    public SelectableGameElement (Image image, Point2D position, String name) {
        super(image, position, name);
    }

    @Override
    public Polygon getVisionPolygon () {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[] {
                                                 this.getBounds().getMinX(),
                                                 this.getBounds().getMinY(),
                                                 this.getBounds().getMinX() +
                                                         this.getBounds().getWidth(),
                                                 this.getBounds().getMinY(),
                                                 this.getBounds().getMaxX(),
                                                 this.getBounds().getMaxY(),
                                                 this.getBounds().getMinX(),
                                                 this.getBounds().getMinY() +
                                                         this.getBounds().getHeight(),
                                   });
        return polygon;
    }
    
    public void select(boolean selecting){
        if(selecting){
        
        }
        else{
        
        }
    }

}
