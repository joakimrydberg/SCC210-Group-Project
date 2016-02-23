package interfaces;

import components.Projectile;

import java.util.ArrayList;

/**
 * @author josh
 * @date 23/02/16.
 */
public interface Ranger {
    final static int RECHARGE = 750;

    ArrayList<Projectile> getProjectiles();
    void clearBrokenProjectiles();


}
