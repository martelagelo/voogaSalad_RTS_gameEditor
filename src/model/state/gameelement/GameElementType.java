package model.state.gameelement;

/**
 * Enum for game element resources (terrain, projectiles, units, buildings,
 * animatorstate)
 * 
 * @author Rahul
 *
 */
public enum GameElementType {
    TERRAIN("terrain"), PROJECTILES("projectile"), UNITS("units"), BUILDINGS("buildings"), ANIMATORSTATE(
            "animatorstate");

    String myName;

    GameElementType (String name) {
        myName = name;
    }

    @Override
    public String toString () {
        return myName;
    }

}
