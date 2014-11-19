package resources.processing;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


public class ColorMaskExtractor {

    public BufferedImage colorMask;
    public BufferedImage spritesheetColorRemoved;
    private BufferedImage originalImage;

    public ColorMaskExtractor(BufferedImage image){
        originalImage = image;
    }
    
    public static void printColorValues (BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int clr = img.getRGB(x, y);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                if (red == 255 && green == 0 && blue == 255) {
                    System.out.print("             \t");
                }
                else if (blue > (red + green)) {
                    System.out.print(String.format("%13s\t", "BLUEBLUEBLUE"));
                }
                else {
                    System.out.print(String.format("(%03d,%03d,%03d)\t", red, green, blue));
                }
            }
            System.out.println();
        }
    }

    public void extractColorMask () throws IOException {
        List<Color> acceptableBlueColors = new ArrayList<Color>();
        acceptableBlueColors.add(new Color(74, 121, 208));
        acceptableBlueColors.add(new Color(151, 206, 255));
        acceptableBlueColors.add(new Color(48, 93, 182));
        acceptableBlueColors.add(new Color(19, 49, 161));
        acceptableBlueColors.add(new Color(205, 250, 255));
        acceptableBlueColors.add(new Color(110, 166, 235));

        ImageFilter colorFilter = new RGBImageFilter() {
            public final int filterRGB (int x, int y, int rgb) {
                if (!acceptableBlueColors.contains(new Color(originalImage.getRGB(x, y)))) {
                return rgb & 0xFFFFFF;
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(originalImage.getSource(), colorFilter);
        colorMask = SpriteSheetCreationUtility.toBufferedImage(Toolkit.getDefaultToolkit()
                .createImage(ip));
        
        ImageFilter spritesheetFilter = new RGBImageFilter() {
            public final int filterRGB (int x, int y, int rgb) {
                if (acceptableBlueColors.contains(new Color(originalImage.getRGB(x, y)))) {
                return rgb & 0xFFFFFF;
                }
                return rgb;
            }
        };

        ImageProducer ip2 = new FilteredImageSource(originalImage.getSource(), spritesheetFilter);
        spritesheetColorRemoved = SpriteSheetCreationUtility.toBufferedImage(Toolkit.getDefaultToolkit()
                .createImage(ip2));
        
    }
}
