package game_engine.gameRepresentation;

import java.util.List;


public class Campaign implements Describable {

    private String name;
    private String description;
    private List<Level> levels;

    @Override
    public String getName () {
        return name;
    }

    @Override
    public String getDescription () {
        return description;
    }

}
