package components.mobs;

import game.Room;
import game.SpriteSheetLoad;
import interfaces.CollidingEntity;

import java.awt.image.BufferedImage;

/**
 * @author josh
 * @date 21/02/16.
 */
public class EnemyRanger extends Enemy implements CollidingEntity {

    public EnemyRanger(Room room, Player player) {
        super(room, player);
        setMovementState(BE_CAUTIOUS);

        setSpriteSheet(SpriteSheetLoad.loadSprite("EnemyMaleSheet"));
        setCharacterStill(0);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
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
}
