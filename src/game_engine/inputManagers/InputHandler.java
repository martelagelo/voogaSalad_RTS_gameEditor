package game_engine.inputManagers;

import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.scene.Node;
//TODO fix/ delete this +inputEvent
public class InputHandler {
    private List<InputEvent> eventHandlers;
    private Node myAttachmentNode;
    public InputHandler(Node attachmentNode){
        eventHandlers = new ArrayList<>();
        myAttachmentNode = attachmentNode;
    }
    public void handleEvent(Event e){

        eventHandlers.stream().filter(inputEvent->e instanceof inputEvent.getEventType()).forEach(inputEvent -> inputEvent.getAction().accept(null));

    }
    public void addEventHandler(InputEvent e){
        myAttachmentNode.addEventHandler(e.getEventType(),action->e.executeAction());
    }

}
