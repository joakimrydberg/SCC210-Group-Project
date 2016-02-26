package components.mobs;

import components.Arrow;
import components.Fireball;
import components.Projectile;
import controllers.MapMenu;
import game.Room;
import game.SpriteSheetLoad;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import tools.Navigator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Ryan on 26/02/2016.
 */
public class Boss extends Enemy {
    private long timeAtLastShot = System.currentTimeMillis();
    private long timeAtLastFlamed = System.currentTimeMillis();
    private Navigator navigator;
    private Room room;
    private ArrayList<Projectile> arrows = new ArrayList<>();

    public Boss(Room room) {
        super(room);
        setMovementState(BE_CAUTIOUS);
        this.room = room;
        this.health = 300;

        setSpriteSheet(SpriteSheetLoad.loadSprite("BossSheetStart"));
        setCharacterStill(0);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        // currAnimation = warriorWalk;
        BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

        super.stop(); //@see Mob , must be before we set the frames
        this.setFrames(mageA);

        this.start();

        navigator = new Navigator(room);

        onMove(getPlayer());
    }

    public void move() {
        if(!stopped) {

            navigator.populateNavPixels();
            if (navigator.inLineOfSight(new Vector2f(getCenterX(), getCenterY()),
                    new Vector2f(getPlayer().getCenterX(), getPlayer().getCenterY()))) {

                if (System.currentTimeMillis() - timeAtLastShot >  2000) { // a bit slower than that for player for the sake of fun
                    timeAtLastShot = System.currentTimeMillis();
                    Arrow arrow = new Arrow();


                    Vector2i from = new Vector2i(this.getCenterX(), this.getCenterY());
                    Vector2i to = new Vector2i(getPlayer().getCenterX(), getPlayer().getCenterY());

                    arrow.setCenterX(from.x);
                    arrow.setCenterY(from.y);

                    arrow.setSpeed(new Vector2f(to.x - from.x, to.y - from.y));
                    arrow.correctDirection();
                    arrow.setSpeed(new Vector2f(to.x - from.x, to.y - from.y));

                    arrow.addMovementListener(room);
                    arrows.add(arrow);
                    room.addEntity(arrow);
                }

            }
            if (System.currentTimeMillis() - timeAtLastFlamed >  3000) { // a bit slower than that for player for the sake of fun

                timeAtLastFlamed = System.currentTimeMillis();
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

        super.move();

    }


    @Override
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
