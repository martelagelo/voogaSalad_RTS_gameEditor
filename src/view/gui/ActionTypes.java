package view.gui;

public enum ActionTypes {
	
	COLLISION_ACTION("Collsiion Action"),
	SEE_ACTION("See Action"),
	INTERNAL_ACTION("Internal Action");
	
	private String myAction;
	
    private ActionTypes (String action) {
        myAction = action;
    }

    public String getAction () {
        return myAction;
    }

}
