package components.mobs;

import game.SpriteSheetLoad;
import interfaces.KeyListener;

import java.awt.image.BufferedImage;

/**
 * Created by millsr3 on 20/02/2016.
 */
public class Ranger extends Player implements KeyListener {

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
}
