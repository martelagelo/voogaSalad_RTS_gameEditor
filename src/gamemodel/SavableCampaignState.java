package gamemodel;

import gamemodel.exceptions.LevelExistsException;
import gamemodel.exceptions.LevelNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 * @author Jonathan Tseng
 *
 */
public class SavableCampaignState extends SavableState {

    private List<SavableLevelState> myLevels;

    protected SavableCampaignState (String name) {
        super(name);
    }

    /**
     * gets the level with associated levelName
     * 
     * @param levelName
     * @return
     * @throws LevelNotFoundException
     */
    public SavableLevelState getLevel (String levelName) throws LevelNotFoundException {
        List<SavableLevelState> levels = myLevels.stream().filter( (level) -> {
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
     * adds a level to the campaign
     * 
     * @param levelToAdd
     * @throws LevelExistsException
     */
    public void addLevel (SavableLevelState levelToAdd) throws LevelExistsException {
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
