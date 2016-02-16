package interfaces;

import org.jsfml.window.event.Event;

/**
 * Works with Click Listener to do the events.
 *
 * @see ClickListener
 *
 * @author josh
 * @date 15/02/16.
 */
public interface Clickable extends InteractingEntity {
    void clicked(Event e);

    void addClickListener(ClickListener clickListener);
}
