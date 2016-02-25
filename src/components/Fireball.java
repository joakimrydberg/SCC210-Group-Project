package components;

import tools.Constants;

/**
 * Created by Ryan on 23/02/2016.
 */
public class Fireball extends Projectile {

    private long timeLastFlamed = System.currentTimeMillis();
    private long time;
    private int x = 0;

    public Fireball() {
        super(("assets" + Constants.SEP + "art" + Constants.SEP + "Flames.png"), 62, 62);
    }
    @Override
    public void move(){

        setCenterX(getCenterX());
        setCenterY(getCenterY());
        if(System.currentTimeMillis() - timeLastFlamed > 1500){
            this.broken = true;
            this.hidden = true;

        }

    }
}
