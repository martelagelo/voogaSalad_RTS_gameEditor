package action;


public enum ActionType {

    INTERNAL(new Action[] { Action.BASIC_COLLISION});
    
//    
//    internal = InternalAction
//            collision = CollisionAction
//            vision = VisionAction
//            objective = ObjectiveAction
//            selection = SelectionAction
    
    private Action[] myActions;

    private ActionType (Action[] actions) {
        myActions = actions;
    }
    
    public Action[] getActions() {
        return myActions;
    }

}
