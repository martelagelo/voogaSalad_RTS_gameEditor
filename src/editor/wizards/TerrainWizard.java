package editor.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Joshua, Nishad
 *
 */
public class TerrainWizard extends DrawableGameElementWizard {

    @FXML
    private Button image;    
    @FXML
    private ImageView spritesheet;

    /**
     * Fired when the user uploads a new picture
     * 
     * Credit for the buffered image code goes to
     * StackOverflow user mathew11
     */
    private void loadImage () {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage()); 
        addToData(WizardDataType.IMAGE, "" + file);
        try {
            Image image = new Image(new FileInputStream(file));
            spritesheet.setImage(image);
        }
        catch (IOException e) {
            System.out.println("Invalid Image");
        }
    }

    public void initialize () {
        super.initialize();
        image.setOnAction(i -> loadImage());
    }
    
    @Override
    public boolean checkCanSave () {
        return super.checkCanSave() && image != null;
    }
    
    @Override
    public void updateData() {
        super.updateData();
        setDataType(WizardDataType.TERRAIN);
    }

}
