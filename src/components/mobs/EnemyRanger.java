package components.mobs;

import components.Projectile;
import game.Room;
import game.SpriteSheetLoad;
import interfaces.CollidingEntity;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import tools.Navigator;

import java.awt.image.BufferedImage;

/**
 * @author josh
 * @date 21/02/16.
 */
public class EnemyRanger extends Enemy implements CollidingEntity {
    private long timeAtLastShot = System.currentTimeMillis();
    private final static int RECHARGE = 1000;
    private Navigator navigator;
    private Room room;

    public EnemyRanger(Room room, Player player) {
        super(room, player);
        setMovementState(BE_CAUTIOUS);
        this.room = room;

        setSpriteSheet(SpriteSheetLoad.loadSprite("EnemyMaleSheet"));
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
        navigator.populateNavPixels();
        if (navigator.inLineOfSight(new Vector2f(getCenterX(), getCenterY()),
                new Vector2f(getPlayer().getCenterX(), getPlayer().getCenterY()))) {

            if (System.currentTimeMillis() - timeAtLastShot > RECHARGE) {
                timeAtLastShot = System.currentTimeMillis();
                Projectile projectile = new Projectile();


                Vector2i from = new Vector2i(this.getCenterX(), this.getCenterY());
                Vector2i to = new Vector2i(getPlayer().getCenterX(), getPlayer().getCenterY());

                projectile.setCenterX(from.x);
                projectile.setCenterY(from.y);

                projectile.setSpeed(new Vector2f(to.x - from.x, to.y - from.y));
                projectile.correctDirection();
                projectile.setSpeed(new Vector2f(to.x - from.x, to.y - from.y));

                room.addEntity(projectile);
            }

        }

        super.move();

    }
    @Override
    public void onMoveAccepted(int newX, int newY) {
        setCenterX(newX);
        setCenterY(newY);
    }

    @Override
    public void onMoveRejected(int newX, int newY) {
        return;    //do nothing but don't remove (will be used for the bad guys)
    }
}
