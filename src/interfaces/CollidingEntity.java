package interfaces;

/**
 * @author josh
 * @date 15/02/16.
 */
public interface CollidingEntity extends InteractingEntity {

    void collide(); //TODO flesh out and implement

    public boolean isCollidable(int x, int y) ;
}
