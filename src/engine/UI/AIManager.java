package engine.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import model.MainModel;
import model.state.gameelement.StateTags;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.stateManaging.GameElementManager;
import engine.stateManaging.GameLoop;
import engine.users.AIParticipant;
import engine.visuals.ScrollablePane;


/**
 * The manager that controls the AI units.
 * TODO: actually implement some logic. For now, all this AI manager
 * is going to do is have all of it's units just run haphazardly
 * 
 * @author John Lorenz
 *
 */
public class AIManager {
    private GameElementManager myElementManager;
    private List<AIParticipant> myAIUsers;

    public AIManager (GameElementManager gameElementManager) {
        myElementManager = gameElementManager;
        myAIUsers = new ArrayList<>();
    }

    public void update (List<SelectableGameElement> allUnits) {
        /**
         * Goes through and for every team that does not correspond to the human player that exists,
         * creates a new AI team for it
         */
        for (SelectableGameElement element : allUnits) {
            int ID = (element.getNumericalAttribute(StateTags.TEAM_ID).intValue());
            boolean containsID =
                    myAIUsers.stream().filter(e -> e.checkSameTeam(ID))
                            .collect(Collectors.toList()).size() > 0;
            if (!containsID && ID!=1) {
                myAIUsers.add(new AIParticipant(ID, "AI" + ID));
            }
        }
        /**
         * Tells all units on all teams to move at complete random around the screen
         */
        for (AIParticipant p : myAIUsers) {
            for (SelectableGameElement e : allUnits
                    .stream()
                    .filter(e -> p.checkSameTeam(e.getNumericalAttribute(StateTags.TEAM_ID)
                            .doubleValue())).collect(Collectors.toList())) {
                Random r = new Random();
                if (r.nextDouble() > 0.99) {
                    e.setHeading(r.nextDouble() * ScrollablePane.FIELD_WIDTH,
                                 r.nextDouble() * ScrollablePane.FIELD_HEIGHT);
                }

            }
        }
    }
}
