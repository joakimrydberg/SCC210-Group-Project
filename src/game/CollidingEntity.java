package game;

/**
 * This interface outlines the methods necessary to implement an Entity that can be collided with.
 *
 * @author Alexander J Mills
 */
public interface CollidingEntity {

    /**
     * Checks whether the x and y parameters
     *
     * @param x - X coordinate to check
     * @param y - Y coordinate to check
     *
     * @return - true if colliding, false if not;
     */
    boolean colliding(int x, int y);
}
