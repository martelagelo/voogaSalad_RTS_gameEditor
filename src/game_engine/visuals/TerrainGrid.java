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
    
    public static final double GRASS_X_DIMENSION = 96;
    public static final double GRASS_Y_DIMENSION = 48;
    
    int[][] grid;
    // 0 for grass, 1 for road, -1 for nothing
    // default is 0
    
    public int[][] generateDefaultGrid(double mapWidth, double mapHeight){
        int[][] g = new int[(int) (mapWidth/GRASS_X_DIMENSION) + 2][(int) (2*mapHeight/GRASS_Y_DIMENSION + 3)];
        for(int i = 0; i<g.length; i++){
            for(int j = 0; j<g[0].length; j++){
                g[i][j] = 0;
            }
        }
        return g;
    }
    
    public List<DrawableGameElementState> renderTerrain (double mapWidth, double mapHeight) {
        Random r = new Random();
        List<DrawableGameElementState> list = new ArrayList<>();
        this.grid = generateDefaultGrid(mapWidth, mapHeight);
        for (int i = 0; i < grid.length; i++) {
            for(int j = 0; j<grid[0].length; j++){
                double deltaX = j%2==1?GRASS_X_DIMENSION/2:0;
                DrawableGameElementState grass = 
                        new DrawableGameElementState(-GRASS_X_DIMENSION+i*GRASS_X_DIMENSION+deltaX,
                                                     -GRASS_Y_DIMENSION+j*GRASS_Y_DIMENSION/2);
                grass.setSpritesheet(new Spritesheet("resources/img/graphics/terrain/grass/"+(r.nextInt(99)+1)+".png",
                                                           new Dimension(96, 48), 1));
                list.add(grass);
            }
        }
        return list;
    }
    
    public static void main(String[] args){
        TerrainGrid g = new TerrainGrid();
        g.renderTerrain(2000, 500);
    }
}
