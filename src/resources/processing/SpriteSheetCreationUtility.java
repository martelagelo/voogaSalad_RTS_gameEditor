package resources.processing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;


/**
 * This utility creates spritesheets with transparent backgrounds from single sprite images.
 * 
 * @author Steve
 *
 */
public class SpriteSheetCreationUtility {

    public static void main (String[] args) throws IOException {
        SpriteSheetCreationUtility processor = new SpriteSheetCreationUtility();
        String baseDirectoryPath = "src/resources/processing/archer/";
        List<String> inputDirectoryPaths = new ArrayList<String>();
        inputDirectoryPaths.add(baseDirectoryPath + "attack/");
        inputDirectoryPaths.add(baseDirectoryPath + "move/");
        inputDirectoryPaths.add(baseDirectoryPath + "stand/");
        inputDirectoryPaths.add(baseDirectoryPath + "die/");
        inputDirectoryPaths.add(baseDirectoryPath + "decay/");
        int[] uniqueStateIndicies = new int[] { 0, 1, 2, 3, 4 };
        List<int[]> allUniqueStateIndicies = new ArrayList<int[]>();
        allUniqueStateIndicies.add(uniqueStateIndicies);
        allUniqueStateIndicies.add(uniqueStateIndicies);
        allUniqueStateIndicies.add(uniqueStateIndicies);
        allUniqueStateIndicies.add(uniqueStateIndicies);
        allUniqueStateIndicies.add(uniqueStateIndicies);
        int[] extrapolatedStateIndicies = new int[] { 0, 1, 2, 3, 4, 3, 2, 1 };
        List<int[]> allExtrapolatedStateIndicies = new ArrayList<int[]>();
        allExtrapolatedStateIndicies.add(extrapolatedStateIndicies);
        allExtrapolatedStateIndicies.add(extrapolatedStateIndicies);
        allExtrapolatedStateIndicies.add(extrapolatedStateIndicies);
        allExtrapolatedStateIndicies.add(extrapolatedStateIndicies);
        allExtrapolatedStateIndicies.add(extrapolatedStateIndicies);
        boolean[] extrapolatedStateMirrorFlags = new boolean[] { false, false, false,
                                                                false, false, true, true, true };
        List<boolean[]> allExtrapolatedStateMirrorFlags = new ArrayList<boolean[]>();
        allExtrapolatedStateMirrorFlags.add(extrapolatedStateMirrorFlags);
        allExtrapolatedStateMirrorFlags.add(extrapolatedStateMirrorFlags);
        allExtrapolatedStateMirrorFlags.add(extrapolatedStateMirrorFlags);
        allExtrapolatedStateMirrorFlags.add(extrapolatedStateMirrorFlags);
        allExtrapolatedStateMirrorFlags.add(extrapolatedStateMirrorFlags);

        processor.createSpriteSheet(inputDirectoryPaths,
                                    "src/resources/processing/archer/", "spritesheet",
                                    allUniqueStateIndicies,
                                    allExtrapolatedStateIndicies,
                                    allExtrapolatedStateMirrorFlags);
        
        processor.doThing();
    }
    
    private void doThing() throws IOException{
        ImageIO.write(toBufferedImage(pinkToTransparency(loadFilesInDirectory(new File("src/resources/img/graphics/interface/")).get(0))), "PNG",
                      new File("src/resources/img/graphics/interface/interface.png"));
    }

    /**
     * Creates a sprite sheet from a batch of bitmaps with pink (0xFFFF00FF) pixel masks.
     * 
     * @param inputDirectoryPath - path to directory holding single sprite bitmaps
     * @param outputDirectoryPath - path to directory holding output file
     * @param outputFileName - name to give to output
     * @param uniqueSpriteIndicies - incidies of states in input
     * @param extrapolatedStateIndicies - order of states in output
     * @param extrapolatedStateMirrorFlags - which states to mirror horizontally in output
     */
    public void createSpriteSheet (List<String> inputDirectoryPaths,
                                   String outputDirectoryPath,
                                   String outputFileName,
                                   List<int[]> uniqueStateIndicies,
                                   List<int[]> extrapolatedStateIndicies,
                                   List<boolean[]> extrapolatedStateMirrorFlags) {
        if (inputDirectoryPaths.size() != uniqueStateIndicies.size() ||
            inputDirectoryPaths.size() != extrapolatedStateIndicies.size() ||
            inputDirectoryPaths.size() != extrapolatedStateMirrorFlags.size()) { return; }
        try {
            List<ArrayList<BufferedImage>> allSpriteLists =
                    new ArrayList<ArrayList<BufferedImage>>();
            for (int i = 0; i < inputDirectoryPaths.size(); i++) {
                File baseDir = new File(inputDirectoryPaths.get(i));
                List<BufferedImage> loadedImages = loadFilesInDirectory(baseDir);
                ArrayList<BufferedImage> extrapolatedImages =
                        (ArrayList<BufferedImage>) extrapolateSprites(loadedImages,
                                                                      uniqueStateIndicies.get(i),
                                                                      extrapolatedStateIndicies
                                                                              .get(i),
                                                                      extrapolatedStateMirrorFlags
                                                                              .get(i));
                allSpriteLists.add(extrapolatedImages);
            }
            List<BufferedImage> allSprites = new ArrayList<BufferedImage>();
            allSpriteLists.stream().forEach(l -> allSprites.addAll(l));
            int frameWidth = findMaximumImageWidth(allSprites);
            int frameHeight = findMaximumImageHeight(allSprites);

            List<BufferedImage> spritesheets = new ArrayList<BufferedImage>();
            for (int i = 0; i < inputDirectoryPaths.size(); i++) {
                BufferedImage protoSpritesheet =
                        tileImagesIntoColumns(allSpriteLists.get(i),
                                              extrapolatedStateIndicies.get(i).length,
                                              frameWidth, frameHeight);
                BufferedImage spritesheet = pinkToTransparency(protoSpritesheet);
                spritesheets.add(spritesheet);
            }

            BufferedImage finalSpritesheet =
                    tileSpritesheets(spritesheets, frameWidth, frameHeight);
            ImageIO.write(finalSpritesheet, "PNG",
                          new File(outputDirectoryPath + File.separator + outputFileName + ".png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage tileSpritesheets (List<BufferedImage> spritesheets,
                                            int frameWidth,
                                            int frameHeight) {
        int width = 0;
        int height = 0;
        for (BufferedImage spritesheet : spritesheets) {
            width += spritesheet.getWidth();
            if (spritesheet.getHeight() > height) {
                height = spritesheet.getHeight();
            }
        }

        int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage finalSpritesheet =
                new BufferedImage(width, height, type);
        Graphics2D spritesheetGraphics = finalSpritesheet.createGraphics();
        int currentWidth = 0;
        for (BufferedImage spritesheet : spritesheets) {
            spritesheetGraphics.drawImage(spritesheet, currentWidth, 0, null);
            currentWidth += spritesheet.getWidth();
        }

        return finalSpritesheet;
    }

    /**
     * This method is able to extrapolate all sprite states necessary from a base set of unique
     * sprite states.
     * 
     * @param uniqueSprites - BufferedImage list of all unique sprites
     * @param uniqueStateIndicies - array like [0, 1, 2, 3, 4] (for a five-directional spritesheet)
     * @param extrapolatedStateIndicies - array like [0, 1, 2, 3, 4, 3, 2, 1] (to make an
     *        eight-directional spritesheet from a five-directional sheet)
     * @param extrapolatedStateMirrorFlags - flags to mirror state images
     * @return
     */
    private List<BufferedImage> extrapolateSprites (List<BufferedImage> uniqueSprites,
                                                    int[] uniqueStateIndicies,
                                                    int[] extrapolatedStateIndicies,
                                                    boolean[] extrapolatedStateMirrorFlags) {
        int numSpritesPerUniqueState =
                (int) Math.ceil((double) uniqueSprites.size() / uniqueStateIndicies.length);
        List<ArrayList<BufferedImage>> uniqueStates = new ArrayList<ArrayList<BufferedImage>>();
        for (int i = 0; i < uniqueStateIndicies.length; i++) {
            ArrayList<BufferedImage> singleUniqueState = new ArrayList<BufferedImage>();
            for (int j = 0; j < numSpritesPerUniqueState; j++) {
                if ((i * numSpritesPerUniqueState + j + 1) < uniqueSprites.size()) {
                    singleUniqueState.add(uniqueSprites.get(i * numSpritesPerUniqueState + j));
                }
            }
            uniqueStates.add(singleUniqueState);
        }
        Map<Integer, ArrayList<BufferedImage>> uniqueStatesIndexed =
                new HashMap<Integer, ArrayList<BufferedImage>>();
        for (int i = 0; i < uniqueStateIndicies.length; i++) {
            uniqueStatesIndexed.put(uniqueStateIndicies[i], uniqueStates.get(i));
        }
        
        // duplicate last frame of backwards animation as they are all lacking one
        ArrayList<BufferedImage> backwardsSprites = uniqueStatesIndexed.get(4);
        backwardsSprites.add(backwardsSprites.get(backwardsSprites.size()-1));
        uniqueStatesIndexed.put(4,backwardsSprites);
        
        List<BufferedImage> extrapolatedSprites = new ArrayList<BufferedImage>();
        for (int i = 0; i < extrapolatedStateIndicies.length; i++) {
            int index = extrapolatedStateIndicies[i];
            List<BufferedImage> stateInQuestion = uniqueStatesIndexed.get(index);
            if (extrapolatedStateMirrorFlags[i]) {
                for (BufferedImage sprite : stateInQuestion) {
                    extrapolatedSprites.add(mirrorImageHorizontally(sprite));
                }
            }
            else {
                extrapolatedSprites.addAll(stateInQuestion);
            }
        }
        return extrapolatedSprites;
    }

    /**
     * Mirrors a BufferedImage horizontally.
     * 
     * @param input image
     * @return mirrored input
     */
    private BufferedImage mirrorImageHorizontally (BufferedImage input) {
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-input.getWidth(null), 0);
        int type = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
        AffineTransformOp operation = new AffineTransformOp(transform, type);
        return operation.filter(input, null);
    }

    /**
     * Tiles many images into columns in a spritesheet.
     * 
     * @param images - source sprites
     * @param numColumns - number of output columns
     * @return - finished spritesheet with pink background
     */
    private BufferedImage tileImagesIntoColumns (List<BufferedImage> images,
                                                 int numColumns,
                                                 int frameWidth,
                                                 int frameHeight) {
        int numRows = (int) Math.ceil((double) images.size() / numColumns);
        Location[][] locations = new Location[numColumns][numRows];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColumns; c++) {
                locations[c][r] = new Location((r + 0.5) * frameHeight, (c + 0.5) * frameWidth);
            }
        }
        int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage spritesheet =
                new BufferedImage(numColumns * frameWidth, numRows * frameHeight, type);
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

    static final String[] EXTENSIONS = new String[] { "bmp" }; // acceptable image file extensions

    /**
     * Filters for acceptable images.
     */
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

    /**
     * Changes all pink pixels to transparent pixels.
     * 
     * @param input image
     * @return - image with all pink (0xFFFF00FF) pixels now transparent
     * @throws IOException
     */
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
