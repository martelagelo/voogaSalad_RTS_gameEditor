package model;

public enum GameElementImageType {
    Drawable("drawablegameelementstates"),
    Selectable("selectablegameelementstates"),
   Spritesheet("spritesheets"),
   Colormask("colormasks");
   
    
    private String myPath;
    private GameElementImageType(String path) {
        myPath = path;
    }
    
    public String getFolderName() {
        return myPath;
    }
}
