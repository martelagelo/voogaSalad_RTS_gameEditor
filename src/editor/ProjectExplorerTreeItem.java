package editor;

import java.util.function.Consumer;
import javafx.scene.control.TreeItem;

/**
 * 
 * @author Jonathan Tseng
 *
 * @param <T> object to display on tree item
 */
public class ProjectExplorerTreeItem<T> extends TreeItem<T> {
    
    public ProjectExplorerTreeItem () {
        super();
    }
    
    public ProjectExplorerTreeItem (T name) {
        super(name);
    }
    
    private Consumer<ProjectExplorerTreeItem<T>> myAction = (ProjectExplorerTreeItem<T> item)->{};
    
    public void setAction(Consumer<ProjectExplorerTreeItem<T>> action) {
        myAction = action;
    }
    
    public void onClicked(ProjectExplorerTreeItem<T> item) {
        myAction.accept(item);
    }
}
