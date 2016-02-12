package game;

import org.jsfml.graphics.RenderWindow;
import java.util.ArrayList;

/**
 * @author josh
 * @date 11/02/16.
 */
public abstract class EntityHolder extends Entity {
    private ArrayList<Entity> entities = new ArrayList<>();

    public EntityHolder(RenderWindow window, String name) {
        super(window, name);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}
