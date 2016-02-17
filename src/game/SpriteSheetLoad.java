package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheetLoad{

    private static BufferedImage spriteSheet;
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
            sprite = ImageIO.read(new File("assets/art/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(int xGrid, int yGrid) {

        if (spriteSheet == null) {
            spriteSheet = loadSprite("WarriorMaleSheet");
        }

        return spriteSheet.getSubimage(xGrid * width, yGrid * height, width, height);
    }

}