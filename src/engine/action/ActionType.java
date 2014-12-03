package engine.action;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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