package engine.actions;

public enum ActionType {

    INTERNAL_ACTION(new Action[] { Action.BASIC_COLLISION,
                                  Action.ADVANCED_COLLISION,
                                  Action.SNATCHED_YO_GIRL }),
    COLLISION_ACTION(new Action[] { Action.GAIN_HEALTH,
                                   Action.LOSE_HEALTH,
                                   Action.GAIN_MANA,
                                   Action.LOSE_MANA });

    private Action[] myActions;

    private ActionType (Action[] actions) {
        myActions = actions;
    }

    public Action[] getActions () {
        return myActions;
    }

}
