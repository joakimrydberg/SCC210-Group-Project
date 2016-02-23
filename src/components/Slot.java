package components;

import controllers.ItemDescriptor;
import interfaces.MotionListener;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

/**
 * Created by newby on 23/02/2016.
 */
public class Slot extends ClickableImage implements MotionListener {

    private ArrayList<Rect> lines = new ArrayList<>();
    private boolean hovered = false;
    private ItemDescriptor itemDesc;

    public Slot(int x, int y, int width, int height,  String textureFile, ItemDescriptor desc){
        super(x, y, width, height, textureFile);
        itemDesc = desc;
    }

    public Slot( int x, int y, int width, int height,  String textureFile, String name, ItemDescriptor desc){
        super( x,  y,  width,  height,   textureFile);
        super.setName(name);
        itemDesc = desc;
    }

    public Slot( int x, int y, String textureFile, ItemDescriptor desc) {
        super(x, y, -1, -1, textureFile);
        itemDesc = desc;
    }

    public Slot( int x, int y, String textureFile, String name, ItemDescriptor desc) {
        super(x, y, -1, -1, textureFile);
        super.setName(name);
        itemDesc = desc;
    }

    @Override
    public void mouseMoved(Event event) {
        if (checkWithin(event.asMouseEvent().position.x, event.asMouseEvent().position.y)) {
            if (!hovered) {
                hovered = true;
                itemDesc.load();
            }
        } else if (hovered) {
            hovered = false;
            itemDesc.unload();
        }
    }

    public boolean getHovered(){
        return hovered;
    }
}
