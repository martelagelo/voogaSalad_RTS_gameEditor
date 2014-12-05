package engine.actions;




public enum Action {
    
    //collision
    BASIC_COLLISION(new String[] { "blah", "blah"}),    
    ADVANCED_COLLISION(new String[] { "la", "la", "la"}),
    SNATCHED_YO_GIRL(new String[] {"its", "over"}),
    
    //internal
    GAIN_HEALTH(new String[] {}),
    LOSE_HEALTH(new String[] {}),
    GAIN_MANA(new String[] {}),
    LOSE_MANA(new String[] {});    
    
    private String[] myTemplate;

    private Action (String[] template) {
        myTemplate = template;
    }
    
    public String[] getTemplate() {
        return myTemplate;
    }
    

}
