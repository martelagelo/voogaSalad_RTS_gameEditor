package util;

import javafx.scene.image.Image;

public interface IMedia {
    
    public default Image loadImage () {
        Image image = new Image(getClass().getResourceAsStream("/resources/img/splashpagebackground.png"));
        System.out.println("Height of Image: " + image.getHeight());
        return image;
            
        
    }

}
