package interfaces;

import org.jsfml.system.Vector2i;

/**
 * @author josh
 * @date 19/02/16.
 */
public interface MovingEntity {
    Vector2i speed = new Vector2i(0, 0);
    float multiplier = 1;


    /**
     * Implementation of move function. Will check for collisions if child implements InteractingEntity and then move if possible
     *
     */
    public void move() ;

    //======================== Getters and Setters ===================================

    /**
     * Set speed
     *
     * @param speed - Vector2i representing the speed of the entity in the x and y planes
     *
     */
    public void setSpeed(Vector2i speed);

    /**
     * Get the entities speed
     *
     * @return Vector2i value representing the speed in two dimensional space
     *
     */
    public Vector2i getSpeed();

    /**
     * Sets the speed multiplier for the Entity.
     *
     * This is used to scale the speed in both directions. Should probably only use on a class by class basis
     *
     * @param multiplier - float value
     */
    public void setSpeedMultiplier(float multiplier);
}
