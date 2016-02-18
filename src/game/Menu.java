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

    public Drawer loadDrawer(Class type) {
        Drawer drawer = Driver.getDrawer(null, type);

        try {
            drawer = (drawer == null) ? (Drawer) type.newInstance() : drawer;
        } catch (Exception e) {
            e.printStackTrace();
        }

        drawer.load();

        return drawer;
    }

}
