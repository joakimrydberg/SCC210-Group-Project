package components.mobs;

import components.Arrow;
import components.Fireball;
import components.Projectile;
import controllers.MapMenu;
import game.Room;
import game.SpriteSheetLoad;
import interfaces.CollidingEntity;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import tools.Navigator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author josh
 * @date 21/02/16.
 */
public class EnemyMage extends Enemy implements CollidingEntity {
    private long timeAtLastShot = System.currentTimeMillis();
    private Navigator navigator;
    private Room room;
    private ArrayList<Projectile> arrows = new ArrayList<>();


    public EnemyMage(Room room) {
        super(room);
        setMovementState(FLEE_PLAYER);  //temp value

        setSpriteSheet(SpriteSheetLoad.loadSprite("EnemyMageSheet"));
        setCharacterStill(0);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        // currAnimation = warriorWalk;
        BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

        super.stop(); //@see Mob , must be before we set the frames
        this.setFrames(mageA);
        this.room = room;
        this.start();
        navigator = new Navigator(room);
  //      navigator.populateNavPixels();


        onMove(getPlayer());

    }
    public void move() {
        if(!stopped) {

            navigator.populateNavPixels();
            if (navigator.inLineOfSight(new Vector2f(getCenterX(), getCenterY()),
                    new Vector2f(getPlayer().getCenterX(), getPlayer().getCenterY()))) {

                if (System.currentTimeMillis() - timeAtLastShot >  2000) { // a bit slower than that for player for the sake of fun

                    timeAtLastShot = System.currentTimeMillis();
                    Fireball arrow = new Fireball();

                 //   Vector2i from = new Vector2i(this.getCenterX(), this.getCenterY());
                    Vector2i to = new Vector2i(getPlayer().getCenterX(), getPlayer().getCenterY());

                    int dfx = MapMenu.randomInt(0, 100);
                    int dfy = MapMenu.randomInt(0, 100);
                    dfx = dfx - 50;
                    dfx = dfx - 50;

                    arrow.setCenterX(to.x + dfx);
                    arrow.setCenterY(to.y + dfy);
                    arrow.setCenterX(to.x + dfx);
                    arrow.setCenterY(to.y + dfy);

                    if(room.isMoveAcceptable(to.x, to.y, this.getWidth() - 20, this.getHeight()- 20, this)){
                        arrow.show();
                        arrows.add(arrow);
                        room.addEntity(arrow);
                    }

                }

            }
        }

        super.move();

    }
    public ArrayList<Projectile> getProjectiles() {
        clearBrokenProjectiles();

        return arrows;
    }

    public void clearBrokenProjectiles() {
        for (int i = 0; i < arrows.size(); i++) {
            Projectile arrow = arrows.get(i);
            if (arrow.getState() == Projectile.BROKEN) {
                arrows.remove(arrow);
            }
        }
    }

}
