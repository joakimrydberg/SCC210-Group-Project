package components.mobs;

import components.Arrow;
import components.Projectile;
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
public class EnemyRanger extends Enemy implements CollidingEntity, interfaces.Ranger {
    private long timeAtLastShot = System.currentTimeMillis();
    private Navigator navigator;
    private Room room;
    private ArrayList<Projectile> arrows = new ArrayList<>();

    public EnemyRanger(Room room) {
        super(room);
        setMovementState(BE_CAUTIOUS);
        this.room = room;

        setSpriteSheet(SpriteSheetLoad.loadSprite("EnemyRangerSheet"));
        setCharacterStill(0);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        // currAnimation = warriorWalk;
        BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

        super.stop(); //@see Mob , must be before we set the frames
        this.setFrames(mageA);

        this.start();

        navigator = new Navigator(room);

        onMove(getPlayer());

    }

    //if  in line of sight and haven/t shot of a while then shoot otherwise just move
    public void move() {
        if(!stopped) {

            navigator.populateNavPixels();
            if (navigator.inLineOfSight(new Vector2f(getCenterX(), getCenterY()),
                    new Vector2f(getPlayer().getCenterX(), getPlayer().getCenterY()))) {

                if (System.currentTimeMillis() - timeAtLastShot > RECHARGE + 250) { // a bit slower than that for player for the sake of fun
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
        }

        super.move();

    }


    @Override
    public ArrayList<Projectile> getProjectiles() {
        clearBrokenProjectiles();

        return arrows;
    }

    @Override
    public void clearBrokenProjectiles() {
        for (int i = 0; i < arrows.size(); i++) {
            Projectile arrow = arrows.get(i);
            if (arrow.getState() == Projectile.BROKEN) {
                arrows.remove(arrow);
            }
        }
    }
}
