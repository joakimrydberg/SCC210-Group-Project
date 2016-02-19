package interfaces;

import org.jsfml.window.event.Event;

/**
 * @author josh
 * @date 19/02/16.
 */
public interface KeyListener {
    void keyPressed(Event event);

    void keyReleased(Event event);
}
