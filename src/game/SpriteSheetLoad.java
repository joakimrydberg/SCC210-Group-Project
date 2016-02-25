package game;

import tools.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Michael on 16/02/2016.
 */

public class SpriteSheetLoad{

    //private static BufferedImage spriteSheet;
    private static int width = 0;
    private static int height = 0;

    //On initialising state width and height of each sprite
    public SpriteSheetLoad(int width, int height){
        this.width = width;
        this.height = height;
    }

    public static BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(new File("assets" + Constants.SEP + "art" + Constants.SEP + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }

    public static BufferedImage getSprite(int xGrid, int yGrid, BufferedImage spriteSheet) {
        //Load the default sprite sheet if null
        if (spriteSheet == null) {
            spriteSheet = loadSprite("WarriorMaleSheet");
        }

        //Get current sprite
        return spriteSheet.getSubimage(xGrid * width, yGrid * height, width, height);

    }

}