package interfaces;

/**
 * @author josh
 * @date 19/02/16.
 */
public interface MovementListener {

    boolean isMoveAcceptable(int newX, int newY, int w, int h);

    void onMove(MovingEntity mover);
}
