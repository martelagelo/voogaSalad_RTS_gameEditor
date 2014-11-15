package game_engine.elementFactories;

/**
 * 
 * Very simple class that holds location information to hide the manner in which Grids arrange their elements
 * 
 * @author Steve
 *
 */
public class Location{
    public int row;
    public int col;

    public Location(int r, int c){
        row = r;
        col = c;
    }
    
    public boolean equals(Object o){
        if(!(o instanceof Location)){
                return false;
        }
        else {
                Location otherLoc = (Location) o;
                if(this.row == otherLoc.row){
                        if(this.col == otherLoc.col){
                                return true;
                        }
                }
        }
        return false;
    }
}
