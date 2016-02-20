package components.mobs;

import game.SpriteSheetLoad;
import interfaces.MovingEntity;
import org.jsfml.system.Vector2i;

import java.awt.image.BufferedImage;

/**
 * @author josh
 * @date 20/02/16.
 */
public class EnemyWarrior extends Enemy {
    // public static Animation currAnimation;
    private final static int MOVEBY = 5,
            SPEEDLIMIT = 5;

    public EnemyWarrior(Player player) {
        super(player);

    }

    @Override
    public void onMove(MovingEntity mover) {
        final int playerX = getPlayer().getCenterX(),
                playerY = getPlayer().getCenterY();

        int newX = playerX - this.getCenterX();
        int newY = playerY - this.getCenterY();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setSpeed(new Vector2i(newX, newY));
        }).start();

    }

    public void setClass(String c) {  //TODO remove completely and set the sprite otherwise (only for enemies, this is good for player)
        //classType = c;

        if (c.equals("mage")) {
            System.out.println("mage selected");
            setSpriteSheet(SpriteSheetLoad.loadSprite("MageMaleSheet"));
            setCharacterStill(new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet())});            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            // currAnimation = warriorWalk;
            BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

            super.stop(); //@see Mob , must be before we set the frames

            this.setFrames(mageA);

            this.start();
        }
        if (c.equals("warrior")) {
            System.out.println("warrior selected");
            setSpriteSheet(SpriteSheetLoad.loadSprite("WarriorMaleSheet"));
            setCharacterStill(new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet())});
            BufferedImage[] warrior = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

            super.stop(); //@see Mob , must be before we set the frames

            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            this.setFrames(warrior);

            this.start();
        }
        if (c.equals("ranger")) {
            System.out.println("ranger selected");
            setSpriteSheet(SpriteSheetLoad.loadSprite("RangerMaleSheet"));
            setCharacterStill(new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet())});
            BufferedImage[] ranger = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};


            super.stop(); //@see Mob , must be before we set the frames

            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            this.setFrames(ranger);

            this.start();
        }

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


