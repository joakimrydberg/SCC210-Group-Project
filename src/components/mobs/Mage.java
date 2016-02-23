package components.mobs;

import game.SpriteSheetLoad;
import interfaces.KeyListener;

import java.awt.image.BufferedImage;

/**
 * Created by millsr3 on 20/02/2016.
 */
public class Mage extends Player implements KeyListener {

    public Mage() {

    }


    public void create(){

        System.out.println("mage selected");
        setSpriteSheet(SpriteSheetLoad.loadSprite("MageMaleSheet"));
        setCharacterStill(dir);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        // currAnimation = warriorWalk;
        BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

        super.stop(); //@see Mob , must be before we set the frames

        this.setFrames(mageA);

        this.start();
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

    @Override
    public void keyPressed(org.jsfml.window.event.KeyEvent event) {

        switch(event.key){

            case SPACE:

                if(tempDir == 2 && !attacking){
                    attack(ATTACK_RIGHT);
                    attacking = true;
                    break;
                }
                if(tempDir == 1 && !attacking){
                    attack(ATTACK_LEFT);
                    attacking = true;
                    break;
                }
                if(tempDir == 3 && !attacking){
                    attack(ATTACK_UP);
                    attacking = true;
                    break;
                }
                else if (tempDir == 0 && !attacking){
                    attack(ATTACK_DOWN);
                    attacking = true;
                    break;
                }
        }

        super.keyPressed(event);


    }

    @Override
    public void keyReleased(org.jsfml.window.event.KeyEvent event) {


        this.setFrames(characterStill);
        super.keyReleased(event);
        if(rightPressed){
            setAnimation(ANIMATE_LEFT);
        } else if (downPressed){
            setAnimation(ANIMATE_LEFT);
        } else if (leftPressed){
            setAnimation(ANIMATE_RIGHT);
        } else if (upPressed){
            setAnimation(ANIMATE_LEFT);
        }
        attacking = false;

    }
}
