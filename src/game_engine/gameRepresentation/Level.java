package game_engine.gameRepresentation;

import java.util.List;
import java.util.stream.Collectors;


public class Level implements Describable {

    private String name;
    private String description;
    private List<GameElement> terrain;
    private List<GameElement> units;
    private List<GameElement> goal;

    public Level(String name, String description, List<GameElement> terrain, List<GameElement> units, List<GameElement> goal){
        this.name = name;
        this.description = description;
        this.terrain = terrain;
        this.units = units;
        this.goal = goal;
    }
    
    @Override
    public String getName () {
        return name;
    }

    @Override
    public String getDescription () {
        return description;
    }
    
    public List<GameElement> getSelectableElements(){
        return units.stream().filter(e->e instanceof SelectableGameElement).collect(Collectors.toList());
    }

}
