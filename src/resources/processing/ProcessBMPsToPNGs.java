package resources.processing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;


public class ProcessBMPsToPNGs {

    public static void main (String[] args) throws IOException {
        ProcessBMPsToPNGs processor = new ProcessBMPsToPNGs();
        File baseDir = new File("src/resources/processing/archer/");
        BufferedImage spritesheet =
                processor.pinkToTransparency(processor.tileImagesIntoFiveColumns(processor
                        .loadFilesInDirectory(baseDir)));
        ImageIO.write(spritesheet, "PNG",
                      new File("src/resources/processing/archer/spritesheet.png"));
    }

    private BufferedImage tileImagesIntoFiveColumns (List<BufferedImage> images) throws IOException {
        int frameWidth = findMaximumImageWidth(images);
        int frameHeight = findMaximumImageHeight(images);
        int numRows = (int) Math.ceil(images.size() / 5.0);
        Location[][] locations = new Location[5][numRows];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < 5; c++) {
                locations[c][r] = new Location((r + 0.5) * frameHeight, (c + 0.5) * frameWidth);
            }
        }
        int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage spritesheet = new BufferedImage(5 * frameWidth, numRows * frameHeight, type);
        Graphics2D spritesheetGraphics = spritesheet.createGraphics();
        spritesheetGraphics.setColor(new Color(0xFFFF00FF));
        spritesheetGraphics.fillRect(0, 0, spritesheet.getWidth(), spritesheet.getHeight());

        for (int i = 0; i < images.size(); i++) {
            BufferedImage image = images.get(i);
            Location location = locations[i / numRows][i % numRows];
            spritesheetGraphics.drawImage(image,
                                          new Double(location.y - image.getHeight() / 2.0)
                                                  .intValue(),
                                          new Double(location.x - image.getWidth() / 2.0)
                                                  .intValue(), null);
        }
        return spritesheet;
    }

    private int findMaximumImageHeight (List<BufferedImage> images) {
        int max = 0;
        for (BufferedImage image : images) {
            if (image.getHeight() > max) {
                max = image.getHeight();
            }
        }
        return max;
    }

    private int findMaximumImageWidth (List<BufferedImage> images) {
        int max = 0;
        for (BufferedImage image : images) {
            if (image.getWidth() > max) {
                max = image.getWidth();
            }
        }
        return max;
    }

    static final String[] EXTENSIONS = new String[] { "bmp" };

    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept (final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                return (true);
                }
            }
            return (false);
        }
    };

    private List<BufferedImage> loadFilesInDirectory (File directory) {
        List<BufferedImage> images = new ArrayList<BufferedImage>();

        if (directory.isDirectory()) {
            for (final File f : directory.listFiles(IMAGE_FILTER)) {
                BufferedImage img = null;
                try {
                    img = ImageIO.read(f);
                    images.add(img);
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return images;
    }

    private BufferedImage pinkToTransparency (BufferedImage input) throws IOException {
        ImageFilter filter = new RGBImageFilter()
        {
            public final int filterRGB (int x, int y, int rgb)
            {
                if (rgb == 0xFFFF00FF) {
                return rgb & 0xFFFFFF;
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(input.getSource(), filter);
        return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
    }

    private static BufferedImage toBufferedImage (Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_ARGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

    public class Location {
        public double x;
        public double y;

        public Location (double X, double Y) {
            x = X;
            y = Y;
        }

        @Override
        public String toString () {
            return "(" + x + ", " + y + ")";
        }
    }

}
