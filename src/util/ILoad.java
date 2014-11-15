package util;

public interface ILoad {
    
    //public JSONable loadResource (String filename);

    public <T> T loadResource (String filePath);
}
