package editor.wizards;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
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
public class TerrainWizard extends GameElementWizard {

    @FXML
    private Button image;
    @FXML
    private ImageView imageView;

    /**
     * Fired when the user uploads a new picture
     * 
     * Credit for the buffered image code goes to
     * StackOverflow user mathew11
     */
    private void loadImage () {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        addToData("Image", "" + file);
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            this.imageView.setImage(image);
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
        setDataName("Terrain");
    }

}
