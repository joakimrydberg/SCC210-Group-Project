package game;

import interfaces.ClickListener;
import org.jsfml.graphics.RenderWindow;

/**
 * Currently Useless
 *
 * @author josh
 * @date 11/02/16.
 */
public abstract class Menu extends Drawer implements ClickListener {

    /**
     *
     * @param window
     * @param name
     */
    public Menu(RenderWindow window, String name) {
        super(window, name);
    }

}
