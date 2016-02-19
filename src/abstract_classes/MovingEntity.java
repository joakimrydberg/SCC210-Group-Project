package abstract_classes;

import interfaces.InteractingEntity;
import org.jsfml.system.Vector2i;
import tools.DebugPrinter;

/**
 * This class will represent an Entity with the ability to move
 *
 * @author Alexander J Mills
 */
public abstract class MovingEntity extends Entity {
    private Vector2i speed = new Vector2i(0, 0);
    private float multiplier = 1;

    /**
     * Creates a new moving entity, with a given name
     *
     * @param name - String name for the Entity
     * @param topLeftX - int X position of the top left of the entity
     * @param topLeftY - int Y position of the top left of the entity
     * @param width - int width
     * @param height - int height

     */
    public MovingEntity( String name, int topLeftX, int topLeftY, int width, int height) {
        super(name, topLeftX, topLeftY, width, height);

    }

    /**
     * Creates a new moving entity
     *
     * @param name - String name for the Entity
     */
    public MovingEntity( String name) {
        super(name);
    }


    /**
     * Implementation of move function. Will check for collisions if child implements InteractingEntity and then move if possible
     *
     */
    public void move() {
        final int newX = getCenterX() + speed.x, newY = getCenterY() + speed.y;

        if (this instanceof InteractingEntity) {
            final InteractingEntity collidingThis = (InteractingEntity)this;

            if (collidingThis.checkWithin(getCenterX(), getCenterY())) {
                DebugPrinter.debugPrint(this, "Already checkWithin!!");
                return;
            }

            if (collidingThis.checkWithin(newX, newY)) {
                DebugPrinter.debugPrint(this, "Already checkWithin!!");
                return;
            }
        }

        final Vector2i windowSize = getWindow().getSize();

        if (newX < 0 || newX > windowSize.x || newY < 0 || newY > windowSize.y) {
            DebugPrinter.debugPrint(this, "Going off screen so stop");
            return;
        }

        //updating X and Y coordinates
        setCenterX(getCenterX() + speed.x);
        setCenterY(getCenterY() + speed.y);

        draw();  //drawing to the screen
    }

    //======================== Getters and Setters ===================================

    /**
     * Set speed
     *
     * @param speed - Vector2i representing the speed of the entity in the x and y planes
     *
     */
    public void setSpeed(Vector2i speed) {
        this.speed = speed;
    }

    /**
     * Get the entities speed
     *
     * @return Vector2i value representing the speed in two dimensional space
     *
     */
    public Vector2i getSpeed() {
        return this.speed;
    }

    /**
     * Sets the speed multiplier for the Entity.
     *
     * This is used to scale the speed in both directions. Should probably only use on a class by class basis
     *
     * @param multiplier - float value
     */
    public void setSpeedMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }
}
