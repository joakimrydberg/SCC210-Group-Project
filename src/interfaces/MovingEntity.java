package interfaces;

import org.jsfml.system.Vector2f;

/**
 * @author josh
 * @date 19/02/16.
 */
public interface MovingEntity {


    /**
     * Implementation of move function. Will check for collisions if child implements InteractingEntity and then move if possible
     *
     */
    void move() ;

    //======================== Getters and Setters ===================================

    /**
     * Set speed
     *
     * @param speed - Vector2i representing the speed of the entity in the x and y planes
     *
     */
    void setSpeed(Vector2f speed);

    /**
     * Get the entities speed
     *
     * @return Vector2i value representing the speed in two dimensional space
     */
    double getSpeed();

    /**
     * Get the entities speed
     *
     * @return Vector2i value representing the speed in two dimensional space
     *
     */
    Vector2f getVelocity();

    /**
     * Sets the speed multiplier for the Entity.
     *
     * This is used to scale the speed in both directions. Should probably only use on a class by class basis
     *
     * @param multiplier - float value
     */
    void setSpeedMultiplier(float multiplier);

    void addMovementListener(MovementListener listener);
}
