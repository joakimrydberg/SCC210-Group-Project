package interfaces;

/**
 * @author josh
 * @date 15/02/16.
 */
public interface CollidingEntity extends InteractingEntity {

    void collide(); //TODO flesh out and implement

    boolean isCollidable();

}
