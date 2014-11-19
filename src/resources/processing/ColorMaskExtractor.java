package resources.processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ColorMaskExtractor {
    public static void main(String[] args){
        File f = new File("src/resources/processing/archer/attack/0001.bmp");
        BufferedImage img = null;
        try {
            img = ImageIO.read(f);
            printColorValues(img);
            Color maskLowerLimit = new Color(0);
            Color maskUpperLimit = new Color(0);
            BufferedImage colorMask = extractColorMask(maskLowerLimit, maskUpperLimit, img);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static void printColorValues (BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int  clr   = img.getRGB(x, y); 
                int  red   = (clr & 0x00ff0000) >> 16;
                int  green = (clr & 0x0000ff00) >> 8;
                int  blue  =  clr & 0x000000ff;
                if(red==255 && green == 0 && blue == 255){
                    System.out.print("             \t");
                }
                else{
                    System.out.print(String.format("(%03d,%03d,%03d)\t",red,green,blue));
                }
            }
            System.out.println();
        }
    }

    private static BufferedImage extractColorMask (Color maskLowerLimit,
                                                   Color maskUpperLimit,
                                                   BufferedImage img) {
        // TODO Auto-generated method stub
        return null;
    }
}      
