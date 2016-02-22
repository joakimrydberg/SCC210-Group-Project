package components.mobs;

import game.Room;
import game.SpriteSheetLoad;
import interfaces.KeyListener;

import java.awt.image.BufferedImage;

/**
 * Created by millsr3 on 20/02/2016.
 */
public class Ranger extends Player {

    public Ranger(){

        super();
        System.out.println("ranger selected");
        setSpriteSheet(SpriteSheetLoad.loadSprite("RangerMaleSheet"));
        setCharacterStill(dir);
        BufferedImage[] ranger = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};


        super.stop(); //@see Mob , must be before we set the frames

        //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        this.setFrames(ranger);

        this.start();
    }
    public void attack(int dir) {
        switch (dir) {

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
