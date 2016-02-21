package components.mobs;

import game.Room;
import game.SpriteSheetLoad;
import interfaces.CollidingEntity;
import org.jsfml.window.event.Event;

import java.awt.image.BufferedImage;

/**
 * @author josh
 * @date 21/02/16.
 */
public class EnemyMage extends Enemy implements CollidingEntity {

    private final static int MOVEBY = 5,
            SPEEDLIMIT = 5;
    private int tempDir = 0;


    public EnemyMage(Room room, Player player) {
        super(room, player);
        setMovementState(FLEE_PLAYER);  //temp value

        setSpriteSheet(SpriteSheetLoad.loadSprite("EnemyMaleSheet"));
        setCharacterStill(tempDir);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        // currAnimation = warriorWalk;
        BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

        super.stop(); //@see Mob , must be before we set the frames
        this.setFrames(mageA);

        this.start();


        onMove(getPlayer());

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


    @Override
    public void collide() {

    }

    @Override
    public boolean isCollidable() {

        if (( Math.abs((getCenterX() + getWidth()/2) - (getPlayer().getCenterX() + getPlayer().getWidth() / 2)) < 35) && (Math.abs((getCenterY() + getHeight()/2) - (getPlayer().getCenterY() + getPlayer().getHeight() / 2)) < 50)){ //May need some tweaks to numbers
            return true;
        }
        return false;
    }

    @Override
    public boolean checkWithin(int x, int y) {

        if (isCollidable()) {
            colliding = true;
            return true;
        }
        else {
            colliding = false;
        }

        return false;
    }

    @Override
    public boolean checkWithin(Event e) {
        return false;
    }

    public void damaged(){

        BufferedImage[] a = charHurt(getTheSpriteSheet(), tempDir);
        setFrames(a); //

    }
}
