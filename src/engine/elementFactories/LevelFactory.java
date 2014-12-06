package engine.elementFactories;

import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Group;
import model.state.LevelState;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import engine.computers.pathingComputers.MapGrid;
import engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.gameRepresentation.renderedRepresentation.Level;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;


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
        Number levelHeight = state.attributes.getNumericalAttribute(StateTags.LEVEL_HEIGHT);
        Number levelWidth = state.attributes.getNumericalAttribute(StateTags.LEVEL_WIDTH);
        MapGrid newGrid = generateUpToDateGrid(levelHeight, levelWidth, terrain, units);
        Group terrainAndSessileUnitsGroup = generateBackGroundGroup(terrain, units);
        Group mobileUnitsGroup = generateUnitsGroup(units);
        mobileUnitsGroup.toFront();
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
        units.stream()
                .filter(u -> u.getNumericalAttribute(StateTags.MOVEMENT_SPEED).doubleValue() == 0 &&
                             u.getNumericalAttribute(StateTags.BLOCKING).intValue() == 1)
                .forEach(u -> newGrid.registerObstaclePlacement(u.getBounds()));
        terrain.stream().filter(t -> t.getNumericalAttribute(StateTags.BLOCKING).intValue() == 1)
                .forEach(t -> newGrid.registerObstaclePlacement(t.getBounds()));
    }

    private Group generateBackGroundGroup (List<DrawableGameElement> terrain,
                                           List<SelectableGameElement> units) {
        Group backgroundGroup = new Group();
        units.stream()
                .filter(u -> u.getNumericalAttribute(StateTags.MOVEMENT_SPEED).doubleValue() == 0)
                .map(u -> u.getNode()).collect(Collectors.toList()).stream()
                .forEach(n -> backgroundGroup.getChildren().add(n));
        terrain.stream().map(t -> t.getNode()).collect(Collectors.toList())
                .forEach(n -> backgroundGroup.getChildren().add(n));
        return backgroundGroup;
    }

    private Group generateUnitsGroup (List<SelectableGameElement> units) {
        Group unitsGroup = new Group();
        units.stream()
                .filter(u -> u.getNumericalAttribute(StateTags.MOVEMENT_SPEED).doubleValue() > 0)
                .map(u -> u.getNode()).collect(Collectors.toList()).stream()
                .forEach(n -> unitsGroup.getChildren().add(n));
        return unitsGroup;
    }
}
