package components;

import controllers.ItemDescriptor;
import interfaces.MotionListener;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Transformable;
import org.jsfml.window.event.Event;
import tools.Constants;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by newby on 23/02/2016.
 */
public class Slot extends ClickableImage implements MotionListener {

    private boolean hovered = false;
    private Item item = null;
    private ItemDescriptor itemDesc;
    private Transformable img;

    public Slot( int x, int y, String textureFile, String name, ItemDescriptor desc) {
        super(x, y, -1, -1, textureFile);
        super.setName(name);
        itemDesc = desc;
    }

    public void addItem(Item i){
        item = i;
        this.img = i.getItemIcon().getTransformable(0);
        addTransformable(img, 40, 40, 60, 60);
        itemDesc.setTitle(item.getName());
    }

    public void removeItem(){
        removeTransformable(1);
        item = null;
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

    public void setItemDesc(ItemDescriptor desc){
        itemDesc = desc;
    }

    /*
     * returns null if no item in the slot
     */
    public Item getItem() {
            return item;
    }

    public boolean hasItem(){
        if (item == null)
            return false;
        else
            return true;
    }


//    public Slot(int x, int y, int width, int height,  String textureFile, ItemDescriptor desc){
//        super(x, y, width, height, textureFile);
//        itemDesc = desc;
//    }
//
//    public Slot( int x, int y, int width, int height,  String textureFile, String name, ItemDescriptor desc){
//        super( x,  y,  width,  height,   textureFile);
//        super.setName(name);
//        itemDesc = desc;
//    }
//
//    public Slot( int x, int y, String textureFile, ItemDescriptor desc) {
//        super(x, y, -1, -1, textureFile);
//        itemDesc = desc;
//    }
}
