package game;

import org.jsfml.graphics.RenderWindow;
import java.util.ArrayList;

/**
 * @author josh
 * @date 11/02/16.
 */
public abstract class Menu extends Entity {
    private boolean loaded;

    private ArrayList<Entity> entities = new ArrayList<>();

    public Menu(RenderWindow window, String name) {
        super(window, name);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void load(){
        loaded = true;
    }

    public void unload(){
        loaded = false;
    }

    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void draw() {
        super.draw();

        if (isLoaded())
            for (Entity a : entities)
                a.draw();
    }

}
