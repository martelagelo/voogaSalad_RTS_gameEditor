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

    private Consumer<T> myAction = (T value) -> {
    };

    public void setAction (Consumer<T> action) {
        myAction = action;
    }

    public void onClicked (T value) {
        myAction.accept(value);
    }
}
