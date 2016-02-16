package game;

import interfaces.Clickable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

/**
 * @author josh
 * @date 15/02/16.
 */
public abstract class Drawer extends Entity {
    private boolean loaded = false;
    private ArrayList<Entity> entities = new ArrayList<>();
    private Driver driver;

    public Drawer(RenderWindow w, String name, Driver driver) {
        super(w, name);

        this.driver = driver;
    }

    public void update(Iterable<Event> events) {
        if (loaded) {
            drawAll();

            for (Event event : events) {
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

    public void load() {
        loaded = true;
        driver.addDrawer(this);
    }

    public void unload() {
        loaded = false;
        driver.removeDrawer(this);
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

                if(!entity.hidden)
                    entity.draw();
            }
        }
    }
    public Driver getDriver() {
        return driver;
    }
}
