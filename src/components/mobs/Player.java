package components.mobs;

import game.SpriteSheetLoad;
import interfaces.KeyListener;
import org.jsfml.system.Vector2i;

import java.awt.image.BufferedImage;

/**
 * Created by millsr3 on 16/02/2016.
 */
public class Player extends Mob implements KeyListener {
    private float multiplier = 1;
    private String classType;
    // public static Animation currAnimation;
    private final static int MOVEBY = 5,
            SPEEDLIMIT = 5;
    private boolean upPressed = false,
            downPressed = false,
            leftPressed = false,
            rightPressed = false;

    public Player() {
        super(200, 200, 64, 128);

    }

    public void setClass(String c) {
        classType = c;

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
    public void keyPressed(org.jsfml.window.event.KeyEvent event) {
        switch (event.key) {
            case RIGHT:
                if (!rightPressed) {
                    rightPressed = true;
                    setSpeed(new Vector2i(getSpeed().x + MOVEBY, getSpeed().y));

                    setAnimation(ANIMATE_RIGHT);
                }
                break;
            case UP:
                if (!upPressed) {
                    upPressed = true;
                    setSpeed(new Vector2i(getSpeed().x, getSpeed().y - MOVEBY));

                    setAnimation(ANIMATE_UP);
                }
                break;
            case LEFT:
                if (!leftPressed) {
                    leftPressed = true;
                    setSpeed(new Vector2i(getSpeed().x - MOVEBY, getSpeed().y));

                    setAnimation(ANIMATE_LEFT);
                }
                break;
            case DOWN:
                if (!downPressed) {
                    downPressed = true;
                    setSpeed(new Vector2i(getSpeed().x, getSpeed().y + MOVEBY));

                    setAnimation(ANIMATE_DOWN);
                }
                break;
        }

    }

    @Override
    public void keyReleased(org.jsfml.window.event.KeyEvent event) {
        switch (event.key) {
            case RIGHT:
                rightPressed = false;
                setSpeed(new Vector2i(0, getSpeed().y));

                if (getSpeed().x == 0 && getSpeed().y == 0) {
                    super.stop(); //@see Mob
                }

                break;
            case UP:
                upPressed = false;
                setSpeed(new Vector2i(getSpeed().x, 0));

                if (getSpeed().x == 0 && getSpeed().y == 0) {
                    super.stop(); //@see Mob
                }

                break;
            case LEFT:
                leftPressed = false;
                setSpeed(new Vector2i(0, getSpeed().y));

                if (getSpeed().x == 0 && getSpeed().y == 0) {
                    super.stop(); //@see Mob
                }
                break;
            case DOWN:
                downPressed = false;
                setSpeed(new Vector2i(getSpeed().x, 0));

                if (getSpeed().x == 0 && getSpeed().y == 0) {
                    super.stop(); //@see Mob
                }

                break;
        }

        this.stop();
        this.start();
        //this.getSpeed() = new Vector2i(0,0);

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


