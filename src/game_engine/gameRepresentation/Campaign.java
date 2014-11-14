package game_engine.gameRepresentation;

import java.util.List;


public class Campaign {

    public String name;
    public String description;
    private List<Level> levels;

    public List<Level> getLevels () {
        return levels;
    }

}
