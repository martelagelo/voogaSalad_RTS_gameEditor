package game_engine.elementFactories;

import game_engine.computers.pathingComputers.MapGrid;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Group;


/**
 * Factory for creating Level objects from LevelState objects.
 * 
 * @author Steve
 *
 */
public class LevelFactory {

    private GameElementFactory myElementFactory;

    public LevelFactory (GameElementFactory elementFactory) {
        myElementFactory = elementFactory;
    }

    public Level createLevel (LevelState state) {
        List<GameElement> goals = generateGoalElements(state.getGoals());
        List<DrawableGameElement> terrain = generateTerrainElements(state.getTerrain());
        List<SelectableGameElement> units = generateUnitEelements(state.getUnits());
        Number levelHeight = state.getNumericalAttribute("LevelHeight");
        Number levelWidth = state.getNumericalAttribute("LevelWidth");
        MapGrid newGrid = generateUpToDateGrid(levelHeight, levelWidth, terrain, units);
        Group terrainAndSessileUnitsGroup = generateBackGroundGroup(terrain, units);
        Group mobileUnitsGroup = generateUnitsGroup(units);
        return new Level(state, terrain, units, goals, terrainAndSessileUnitsGroup,
                         mobileUnitsGroup, newGrid);
    }

    private List<GameElement> generateGoalElements (List<GameElementState> goals) {
        return goals.stream().map(s -> myElementFactory.createGameElement(s))
                .collect(Collectors.toList());
    }

    private List<DrawableGameElement> generateTerrainElements (List<DrawableGameElementState> terrain) {
        return terrain.stream().map(s -> myElementFactory.createDrawableGameElement(s))
                .collect(Collectors.toList());
    }

    private List<SelectableGameElement> generateUnitEelements (List<SelectableGameElementState> units) {
        return units.stream().map(s -> myElementFactory.createSelectableGameElement(s))
                .collect(Collectors.toList());
    }

    private MapGrid generateUpToDateGrid (Number levelHeight,
                                          Number levelWidth,
                                          List<DrawableGameElement> terrain,
                                          List<SelectableGameElement> units) {
        MapGrid newGrid = new MapGrid(levelHeight, levelWidth);
        updateGridForObstacles(terrain, units, newGrid);
        return newGrid;
    }

    private void updateGridForObstacles (List<DrawableGameElement> terrain,
                                         List<SelectableGameElement> units,
                                         MapGrid newGrid) {
        units.stream().filter(u -> u.getNumericalAttribute("MovementSpeed").doubleValue() == 0)
                .forEach(u -> newGrid.registerObstacle(u.getBounds()));
        terrain.stream().forEach(t -> newGrid.registerObstacle(t.getBounds()));
    }

    private Group generateBackGroundGroup (List<DrawableGameElement> terrain,
                                           List<SelectableGameElement> units) {
        Group backgroundGroup = new Group();
        units.stream().filter(u -> u.getNumericalAttribute("MovementSpeed").doubleValue() == 0)
                .map(u -> u.getNode()).collect(Collectors.toList()).stream()
                .forEach(n -> backgroundGroup.getChildren().add(n));
        terrain.stream().map(t -> t.getNode()).collect(Collectors.toList())
                .forEach(n -> backgroundGroup.getChildren().add(n));
        return backgroundGroup;
    }

    private Group generateUnitsGroup (List<SelectableGameElement> units) {
        Group unitsGroup = new Group();
        units.stream().filter(u -> u.getNumericalAttribute("MovementSpeed").doubleValue() > 0)
                .map(u -> u.getNode()).collect(Collectors.toList()).stream()
                .forEach(n -> unitsGroup.getChildren().add(n));
        return unitsGroup;
    }
}
