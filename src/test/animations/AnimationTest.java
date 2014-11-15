package test.animations;

import javafx.scene.Group;
import javafx.scene.image.Image;
import test.VisualTester;


public class AnimationTest extends VisualTester {

    @Override
    protected Group getDisplay () {
        Group myGroup = super.getDisplay();
        Image myImage = new Image("resources/img/exploBig.png");
        return myGroup;
    }
}
