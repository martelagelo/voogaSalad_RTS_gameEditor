package test.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestJSON {
    
    
    public static void main(String [] args) {
        TestGame g = new TestGame("Game", "Game Descrp"); 
        TestCampaign c = new TestCampaign("Campaign 1", "Campaign 1 Info");
        g.addCampaign(c);
        c.addLevel(new TestLevel("Level 1", "defeat"));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(g));
              
        
        
    }

}
