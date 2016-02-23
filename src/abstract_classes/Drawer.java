package abstract_classes;

import game.Driver;
import interfaces.Clickable;
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
                    for (int i = 0; i < getEntities().size(); i++) {
                        Entity entity = getEntity(i);
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

    public void drawAll() {
        if (isLoaded()) {
            draw();

            for (int i = 0; i < getEntities().size(); i++) {   //done properly to avoid co-modification
                Entity entity = getEntity(i);

                //ryan, I moved the missing methods to Room

                if (entity instanceof MovingEntity)
                    ((MovingEntity) entity).move();


                if(!entity.hidden)
                    entity.draw();
            }
        }
    }

    public Drawer loadDrawer(Class type) {
        Drawer drawer = Driver.getDrawer(null, type);

        try {
            drawer = (drawer == null) ? (Drawer) type.newInstance() : drawer;
        } catch (Exception e) {
            e.printStackTrace();
        }

        drawer.load();

        return drawer;
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

    public Entity getEntity(int i) {
        return entities.get(i);
    }

    public void replaceEntity(int i, Entity entity) {
        ArrayList<Entity> newEntities = new ArrayList<>();

        for (int j = 0; j < entities.size(); j++) {
            if (j == i) {
                entities.add(entity);
            } else {
                newEntities.add(entities.get(j));
            }
        }
    }

}
