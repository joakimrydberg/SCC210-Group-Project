package game;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2i;

/**
 * This class will represent an Entity with the ability to move
 *
 * @author Alexander J Mills
 */
public abstract class MovingEntity extends Entity {

    /**
     * Creates a new moving entity, with a given name
     *
     * @param window - RenderWindow in which the Entity will be displayed
     * @param name - String name for the Entity
     * @param topLeftX - int X position of the top left of the entity
     * @param topLeftY - int Y position of the top left of the entity
     * @param width - int width
     * @param height - int height
     * @param transformable - Transformable obj
     */
    public MovingEntity(RenderWindow window, String name, int topLeftX, int topLeftY, int width, int height, Transformable transformable) {
        super(window, name, topLeftX, topLeftY, width, height, transformable);

    }

    /**
     * Creates a new moving entity
     *
     * @param window - RenderWindow in which the Entity will be displayed
     * @param name - String name for the Entity
     */
    public MovingEntity(RenderWindow window, String name) {
        super(window, name);
    }

    /**
     * Implementation of move function. Will check for collisions if child implements CollidingEntity and then move if possible
     *
     * @param topLeftX - change in X coordinates (Top Left value)
     * @param topLeftY - change in Y coordinates (Top Left value)
     */
    public void move(int topLeftX, int topLeftY) {
        final int newX = getTopLeftX() + topLeftX, newY = getTopLeftY() + topLeftY;

        if (this instanceof CollidingEntity) {
            final CollidingEntity collidingThis = (CollidingEntity)this;

            if (collidingThis.colliding(getTopLeftX(), getTopLeftY())) {
                DebugPrinter.debugPrint(this, "Already colliding!!");
                return;
            }

            if (collidingThis.colliding(newX, newY)) {
                DebugPrinter.debugPrint(this, "Already colliding!!");
                return;
            }
        }

        final Vector2i windowSize = getWindow().getSize();

        if (newX < 0 || newX > windowSize.x || newY < 0 || newY > windowSize.y) {
            DebugPrinter.debugPrint(this, "Going off screen so stop");
            return;
        }

        //updating X and Y coordinates
        setTopLeftX(getTopLeftX() + topLeftX);
        setTopLeftY(getTopLeftY() + topLeftY);

        draw();  //drawing to the screen
    }
}
