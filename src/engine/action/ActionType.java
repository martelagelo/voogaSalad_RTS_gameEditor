package engine.action;


public enum ActionType {
     CollisionAction,
     InternalAction;
     
     enum CollisionAction{
    	 BasicCollision,
    	 AdvancedCollision,
    	 SnatchedYoGirl;
     }
     
     enum InternalAction{
    	 GainHealth,
    	 LoseHealth,
    	 GainMana,
    	 LoseMana;
     }

}