package action;


public enum Action {
    
    BASIC_COLLISION(new String[] { "blah", "blah"});    
    
    private String[] myTemplate;

    private Action (String[] template) {
        myTemplate = template;
    }
    
    public String[] getTemplate() {
        return myTemplate;
    }
    

}
