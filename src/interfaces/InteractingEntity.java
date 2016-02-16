package interfaces;

import org.jsfml.window.event.Event;

/**
 * This interface outlines the methods necessary to implement an Entity that can be collided with.
 *
 * @author Alexander J Mills
 */
public interface InteractingEntity {

    /**
     * Checks whether the x and y parameters
     *
     * @param x - X coordinate to check
     * @param y - Y coordinate to check
     *
     * @return - true if checkWithin, false if not;
     */
    boolean checkWithin(int x, int y);

    /**
     * Checks whether the x and y parameters passed in an Event obj
     *
     * @param e - the Event that caused this method call
     *
     * @return - true if checkWithin, false if not;
     */
    boolean checkWithin(Event e);


}
