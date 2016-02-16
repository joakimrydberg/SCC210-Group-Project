package game;

import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

/**
 * @author josh
 * @date 15/02/16.
 */
public abstract class Drawer extends Entity implements Runnable {
    private boolean loaded = false;
    private ArrayList<Entity> entities = new ArrayList<>();
    Thread thread = new Thread(this);

    public Drawer(RenderWindow w, String name) {
        super(w, name);
    }

    @Override
    public void run() {
        int i;

        while (loaded) {
            getWindow().clear(Color.WHITE);
            drawAll();
            // Update the display with any changes
            getWindow().display();

            for (Event event : getWindow().pollEvents()) {
                if (event.type == Event.Type.CLOSED)      // the user pressed the close button
                    getWindow().close();

                if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                    //   if(event.asMouseButtonEvent()){
                    for (Entity entity : getEntities()) {
                        if (entity instanceof Clickable
                                && ((Clickable) entity).checkWithin(event)) {

                            ((Clickable) entity).clicked(event);
                        }
                    }
                }
            }
        }
    }

    public void load(){
        loaded = true;
        thread.start();
    }

    public void unload(){
        loaded = false;
        //thread.interrupt(); causes errors
    }

    public boolean isLoaded() {
        return loaded;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    private void drawAll() {  //TODO rename to something remotely appropriate
        if (isLoaded()) {
            for (Entity entity : getEntities()) {
                if (entity instanceof MovingEntity)
                    ((MovingEntity) entity).move();

                entity.draw();
            }
        }
    }
}
