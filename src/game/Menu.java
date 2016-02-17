package game;

import interfaces.ClickListener;

/**
 * Currently Useless
 *
 * @author josh
 * @date 11/02/16.
 */
public abstract class Menu extends Drawer implements ClickListener {

    /**

     * @param name
     */
    public Menu(String name) {
        super( name);
    }

}
