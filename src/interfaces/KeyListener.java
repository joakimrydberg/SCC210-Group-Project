package interfaces;

import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

/**
 * @author josh
 * @date 19/02/16.
 */
public interface KeyListener {
    void keyPressed(KeyEvent event);

    void keyReleased(Event event);
}
