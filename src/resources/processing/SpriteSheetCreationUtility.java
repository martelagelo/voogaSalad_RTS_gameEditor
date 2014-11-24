package resources.processing;

import game_engine.visuals.Dimension;
import game_engine.visuals.Spritesheet;
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
import util.JSONable;
import util.LoadSaveUtility;


/**
 * This utility creates spritesheets with transparent backgrounds from single sprite images.
 * 
 * @author Steve
 *
 */
public class SpriteSheetCreationUtility {

    public static void main (String[] args) throws IOException {
        SpriteSheetCreationUtility processor = new SpriteSheetCreationUtility();
        // String baseDirectoryPath = "src/resources/img/graphics/units/";
        // int[] uniqueStateIndicies = new int[] { 0, 1, 2, 3, 4 };
        // int[] extrapolatedStateIndicies = new int[] { 0, 1, 2, 3, 4, 3, 2, 1 };
        // boolean[] extrapolatedStateMirrorFlags = new boolean[] { false, false, false,
        // false, false, true, true, true };
        //
        // processor.createSpriteSheet(baseDirectoryPath,
        // uniqueStateIndicies,
        // extrapolatedStateIndicies,
        // extrapolatedStateMirrorFlags,
        // new Color(0xFFFF00FF));

        // processor.doThing();

    }

    private void doThing () throws IOException {
        List<BufferedImage> grassTiles =
                loadFilesInDirectory(new File("src/resources/img/graphics/terrain/grass/"));
        List<BufferedImage> betterTiles = new ArrayList<BufferedImage>();
        for (BufferedImage image : grassTiles) {
            betterTiles.add(colorToTransparency(image, new Color(0xFFFF00FF)));
        }
        int i = 1;
        for (BufferedImage image : betterTiles) {
            ImageIO.write(image, "PNG", new File("src/resources/img/graphics/terrain/grass/" + i +
                                                 ".png"));
            i++;
        }
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
    public void createSpriteSheet (String baseInputDirectoryPath,
                                   int[] uniqueStateIndicies,
                                   int[] extrapolatedStateIndicies,
                                   boolean[] extrapolatedStateMirrorFlags,
                                   Color maskColor) {
        try {
            File baseDirectory = new File(baseInputDirectoryPath);
            if (!baseDirectory.isDirectory()) { return; }
            for (File unitDirectory : baseDirectory.listFiles()) {
                if (!unitDirectory.isDirectory()) {
                    continue;
                }
                String unitName = unitDirectory.getName();
                List<ArrayList<BufferedImage>> allSpriteLists =
                        new ArrayList<ArrayList<BufferedImage>>();
                extractAllSpriteLists(uniqueStateIndicies, extrapolatedStateIndicies,
                                      extrapolatedStateMirrorFlags, unitDirectory, allSpriteLists);

                List<BufferedImage> allSprites = new ArrayList<BufferedImage>();
                allSpriteLists.stream().forEach(l -> allSprites.addAll(l));
                int frameWidth = findMaximumImageWidth(allSprites);
                int frameHeight = findMaximumImageHeight(allSprites);

                List<BufferedImage> stateSpritesheets = new ArrayList<BufferedImage>();
                createStateSpritesheets(extrapolatedStateIndicies, maskColor, allSpriteLists,
                                        stateSpritesheets, frameWidth, frameHeight);

                BufferedImage finalSpritesheet =
                        tileSpritesheets(stateSpritesheets, frameWidth, frameHeight);
                ColorMaskExtractor extractor = new ColorMaskExtractor(finalSpritesheet);
                extractor.extractColorMask();
                BufferedImage finalSpritesheetColorRemoved = extractor.spritesheetColorRemoved;
                BufferedImage colorMask = extractor.colorMask;
                String filePathForSpritesheet =
                        baseDirectory.getAbsolutePath() + File.separator + unitName;
                String filePathForColorMask = filePathForSpritesheet + "ColorMask";
                ImageIO.write(finalSpritesheetColorRemoved, "PNG", new File(filePathForSpritesheet +
                                                                            ".png"));
                ImageIO.write(colorMask, "PNG", new File(filePathForColorMask + ".png"));
                int numTotalColumns = stateSpritesheets.size() * 8;
                Spritesheet spritesheetObject =
                        new Spritesheet(unitName, new Dimension(frameWidth, frameHeight),
                                        numTotalColumns);

                LoadSaveUtility saveUtil = new LoadSaveUtility();
                saveUtil.save((JSONable) spritesheetObject, filePathForSpritesheet + ".json");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extractAllSpriteLists (int[] uniqueStateIndicies,
                                        int[] extrapolatedStateIndicies,
                                        boolean[] extrapolatedStateMirrorFlags,
                                        File unitDirectory,
                                        List<ArrayList<BufferedImage>> allSpriteLists) {
        for (File directory : unitDirectory.listFiles()) {
            if (!directory.isDirectory()) {
                continue;
            }
            List<BufferedImage> loadedImages = loadFilesInDirectory(directory);
            ArrayList<BufferedImage> extrapolatedImages =
                    (ArrayList<BufferedImage>) extrapolateSprites(loadedImages,
                                                                  uniqueStateIndicies,
                                                                  extrapolatedStateIndicies,
                                                                  extrapolatedStateMirrorFlags);
            allSpriteLists.add(extrapolatedImages);
        }
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
        backwardsSprites.add(backwardsSprites.get(backwardsSprites.size() - 1));
        uniqueStatesIndexed.put(4, backwardsSprites);

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

    private void createStateSpritesheets (int[] extrapolatedStateIndicies,
                                          Color maskColor,
                                          List<ArrayList<BufferedImage>> allSpriteLists,
                                          List<BufferedImage> spritesheets,
                                          int frameWidth,
                                          int frameHeight) throws IOException {
        for (ArrayList<BufferedImage> spriteList : allSpriteLists) {
            BufferedImage protoSpritesheet =
                    tileImagesIntoColumns(spriteList,
                                          extrapolatedStateIndicies.length,
                                          frameWidth, frameHeight, maskColor);
            BufferedImage spritesheet = colorToTransparency(protoSpritesheet, maskColor);
            spritesheets.add(spritesheet);
        }
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
                                                 int frameHeight,
                                                 Color maskColor) {
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
        spritesheetGraphics.setColor(maskColor);
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
    private BufferedImage colorToTransparency (BufferedImage input, Color maskColor)
                                                                                    throws IOException {
        ImageFilter filter = new RGBImageFilter()
        {
            public final int filterRGB (int x, int y, int rgb)
            {
                if (rgb == maskColor.getRGB()) {
                return rgb & 0xFFFFFF;
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(input.getSource(), filter);
        return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
    }

    static BufferedImage toBufferedImage (Image src) {
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
