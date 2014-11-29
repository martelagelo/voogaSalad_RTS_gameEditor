package game_engine.gameRepresentation.stateRepresentation;

import gamemodel.exceptions.LevelExistsException;
import gamemodel.exceptions.LevelNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is a collection of Levels used to string together gameplay in
 * order to make a cohesive user experience.
 * 
 * @author Steve, Jonathan, Nishad, Rahul
 *
 */
public class CampaignState extends DescribableState {

    private List<LevelState> myLevels;

    public CampaignState (String name) {
        super(name);
        myLevels = new ArrayList<>();
    }

    /**
     * gets the level with associated levelName
     * 
     * @param levelName
     * @return the state of the level of interest
     * @throws LevelNotFoundException
     */
    public LevelState getLevel (String levelName) throws LevelNotFoundException {
        List<LevelState> levels = myLevels.stream().filter( (level) -> {
            return level.getName().equals(levelName);
        }).collect(Collectors.toList());
        if (levels.isEmpty()) {
            throw new LevelNotFoundException(levelName);
        }
        else {
            return levels.get(0);
        }
    }

    /**
     * gets unmodifiable list of all of the levels
     * 
     * @return
     */
    public List<LevelState> getLevels () {
        return Collections.unmodifiableList(myLevels);
    }

    /**
     * adds a level to the campaign
     * 
     * @param levelToAdd
     * @throws LevelExistsException
     */
    public void addLevel (LevelState levelToAdd) throws LevelExistsException {
        if (myLevels.stream().anyMatch( (campaign) -> {
            return levelToAdd.getName().equals(campaign.getName());
        })) {
            throw new LevelExistsException(levelToAdd.getName());
        }
        else {
            myLevels.add(levelToAdd);
        }
    }

    /**
     * removes a level with a given name
     * 
     * @param levelName
     */
    public void removeLevel (String levelName) {
        myLevels = myLevels.stream().filter( (level) -> {
            return !level.getName().equals(levelName);
        }).collect(Collectors.toList());
    }

}
