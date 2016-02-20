package components.mobs;

import game.SpriteSheetLoad;
import interfaces.KeyListener;

import java.awt.image.BufferedImage;

/**
 * Created by millsr3 on 20/02/2016.
 */
public class Mage extends Player implements KeyListener {

    public Mage(){

        super();
        System.out.println("mage selected");
        setSpriteSheet(SpriteSheetLoad.loadSprite("MageMaleSheet"));
        setCharacterStill(dir);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        // currAnimation = warriorWalk;
        BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

        super.stop(); //@see Mob , must be before we set the frames

        this.setFrames(mageA);

        this.start();
    }
}
