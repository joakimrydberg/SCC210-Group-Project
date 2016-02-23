package components.mobs;

import game.Room;
import game.SpriteSheetLoad;

import java.awt.image.BufferedImage;

/**
 * @author josh
 * @date 20/02/16.
 */
public class EnemyWarrior extends Enemy {
    public final static int ATTACK_RIGHT = 1,
            ATTACK_LEFT = 2,
            ATTACK_UP = 3,
            ATTACK_DOWN = 4;
    public int attackingDir = -1;
    public final static int ATTACK_DIST = 50;

    public boolean attacking = false;

    public EnemyWarrior(Room room, Player player) {
        super(room, player);
        setMovementState(FOLLOW_PLAYER);

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
    public void move() {
        if(!stopped) {
            Player player = getPlayer();
            final int xDiff = player.getCenterX() - getCenterX(),
                    yDiff = player.getCenterY() - getCenterY();

            double dist_to_player = Math.sqrt(Math.pow(xDiff, 2)
                    + Math.pow(yDiff, 2));

            if (dist_to_player < ATTACK_DIST) {

                if (xDiff > 0 && Math.abs(xDiff) > Math.abs(yDiff)) {
                    if (attackingDir != ATTACK_RIGHT) {
                        attackingDir = ATTACK_RIGHT;
                        attack(attackingDir);
                    }
                } else if (xDiff < 0 && Math.abs(xDiff) > Math.abs(yDiff)) {
                    if (attackingDir != ATTACK_LEFT) {
                        attackingDir = ATTACK_LEFT;
                        attack(attackingDir);
                    }
                } else if (yDiff > 0 && Math.abs(yDiff) > Math.abs(xDiff)) {
                    if (attackingDir != ATTACK_DOWN) {
                        attackingDir = ATTACK_DOWN;
                        attack(attackingDir);
                    }

                } else if (yDiff < 0 && Math.abs(yDiff) > Math.abs(xDiff)) {
                    if (attackingDir != ATTACK_UP) {
                        attackingDir = ATTACK_UP;
                        attack(attackingDir);
                    }

                }

                attacking = true;
            } else {
                attacking = false;
            }

            super.move();
        }
    }



    public void attack(int dir){
        switch(dir){

            case ATTACK_RIGHT:

                BufferedImage[] right = {SpriteSheetLoad.getSprite(4, 2, theSpriteSheet), SpriteSheetLoad.getSprite(5, 2, theSpriteSheet), SpriteSheetLoad.getSprite(4, 2, theSpriteSheet), SpriteSheetLoad.getSprite(5, 2, theSpriteSheet)};
                this.setFrames(right);
                break;
            case ATTACK_DOWN:

                BufferedImage[] down = {SpriteSheetLoad.getSprite(4, 0, theSpriteSheet), SpriteSheetLoad.getSprite(5, 0, theSpriteSheet), SpriteSheetLoad.getSprite(4, 0, theSpriteSheet), SpriteSheetLoad.getSprite(5, 0, theSpriteSheet)};
                this.setFrames(down);
                break;
            case ATTACK_LEFT:

                BufferedImage[] left = {SpriteSheetLoad.getSprite(4, 1, theSpriteSheet), SpriteSheetLoad.getSprite(5, 1, theSpriteSheet), SpriteSheetLoad.getSprite(4, 1, theSpriteSheet), SpriteSheetLoad.getSprite(5, 1, theSpriteSheet)};
                this.setFrames(left);
                break;
            case ATTACK_UP:

                BufferedImage[] up = {SpriteSheetLoad.getSprite(4, 3, theSpriteSheet), SpriteSheetLoad.getSprite(5, 3, theSpriteSheet), SpriteSheetLoad.getSprite(4, 3, theSpriteSheet), SpriteSheetLoad.getSprite(5, 3, theSpriteSheet)};
                this.setFrames(up);
                break;
        }

    }
}


