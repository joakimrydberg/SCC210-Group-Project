package abstract_classes;

import components.mobs.Enemy;
import components.mobs.Player;
import game.Driver;
import interfaces.Clickable;
import interfaces.CollidingEntity;
import interfaces.KeyListener;
import interfaces.MovingEntity;
import org.jsfml.window.event.Event;

import java.util.ArrayList;

/**
 * @author josh
 * @date 15/02/16.
 */
public abstract class Drawer extends Entity {
    private boolean loaded = false;
    private ArrayList<Entity> entities = new ArrayList<>();

    public Drawer(String name) {
        super(name);

        Driver.addDrawer(this);
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

                if (event.type == Event.Type.KEY_PRESSED) {
                    for (Entity entity : entities) {
                        if (entity instanceof KeyListener) {
                            ((KeyListener) entity).keyPressed(event.asKeyEvent());
                        }
                    }//
                }

                if (event.type == Event.Type.KEY_RELEASED) {
                    for (Entity entity : entities) {
                        if (entity instanceof KeyListener) {
                            ((KeyListener) entity).keyReleased(event.asKeyEvent());
                        }
                    }
                }
            }
        }
    }

    public void load() {
        loaded = true;
    }

    public void unload() {
        loaded = false;
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

    private void drawAll() {
        if (isLoaded()) {
            draw();

            for (Entity entity : getEntities()) {
                if (entity instanceof MovingEntity)
                    ((MovingEntity) entity).move();

                if(entity instanceof CollidingEntity) {

                    Player p = ((Enemy) entity).getPlayer();
                    ((CollidingEntity) entity).checkWithin(p.getCenterX(), p.getCenterY());

                }
                if(!entity.hidden)
                    entity.draw();
            }
        }
    }
}
