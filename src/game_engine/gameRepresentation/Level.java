package game_engine.gameRepresentation;

import java.util.List;


public class Level implements Describable {

    private String name;
    private String description;
    private List<GameElement> terrain;
    private List<GameElement> units;
    private List<GameElement> goal;

    @Override
    public String getName () {
        return name;
    }

    @Override
    public String getDescription () {
        return description;
    }
    
    public List<GameElement> getTerrain() {
    	return terrain;
    }
    
    public List<GameElement> getUnits() {
    	return units;
    }
    
    public List<GameElement> getGoal() {
    	return goal;
    }

}
