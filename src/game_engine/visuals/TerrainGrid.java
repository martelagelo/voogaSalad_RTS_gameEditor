package game_engine.visuals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;

/**
 * Grid for storing and creating the terrain. Will allow for a simpler method of storing
 * the tiled terrain (not mountains or obstacles like that) and later recreating it 
 * than just saving all of the tile objects if its deemed necessary
 * @author John
 *
 */
public class TerrainGrid {
    
    public static final double TILE_X_DIMENSION = 96;
    public static final double TILE_Y_DIMENSION = 48;
    
    int[][] grid;
    
    public TerrainGrid(double mapWidth, double mapHeight){
        this.grid = generateDefaultGrid(mapWidth, mapHeight);
    }
    
    /**
     * Generates a grid with size determined by the tile dimensions and the total
     * map size. Contains a 0 in every element, representing the default terrain type
     * @param mapWidth the number of pixels for the entire map width
     * @param mapHeight the number of pixels for the entire map height
     * @return a two dimensional array with zeros
     */
    public int[][] generateDefaultGrid(double mapWidth, double mapHeight){
        int[][] g = new int[(int) (mapWidth/TILE_X_DIMENSION) + 2][(int) (2*mapHeight/TILE_Y_DIMENSION + 3)];
        for(int i = 0; i<g.length; i++){
            for(int j = 0; j<g[0].length; j++){
                g[i][j] = 0;
            }
        }
        return g;
    }
    
    /**
     * Creates a new DrawableGameElementState with settings for the proper location
     * @param mapWidth the number of pixels for the entire map width
     * @param mapHeight the number of pixels for the entire map height
     * @return the list of DrawableGameElementStates representing all the terrain objects
     */
    public List<DrawableGameElementState> renderTerrain (int[][] terrainArray) {
        Random r = new Random();
        List<DrawableGameElementState> list = new ArrayList<>();
        for (int i = 0; i < terrainArray.length; i++) {
            for(int j = 0; j<terrainArray[0].length; j++){
                double deltaX = j%2==1?TILE_X_DIMENSION/2:0;
                DrawableGameElementState grass = 
                        new DrawableGameElementState(-TILE_X_DIMENSION+i*TILE_X_DIMENSION+deltaX,
                                                     -TILE_Y_DIMENSION+j*TILE_Y_DIMENSION/2);
                grass.setSpritesheet(new Spritesheet("resources/img/graphics/terrain/grass/"+(r.nextInt(99)+1)+".png",
                                                           new Dimension(96, 48), 1));
                list.add(grass);
            }
        }
        return list;
    }
    
    public List<DrawableGameElementState> renderTerrain(){
        return renderTerrain(this.grid);
    }
}
