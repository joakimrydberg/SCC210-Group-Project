package game;

import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Created by Michael on 16/02/2016.
 */
public class SpriteSheetLoad{
    BufferedImage spriteSheet;
    int width;
    int height;
    int rows;
    int columns;
    int size;
    private static BufferedImage[] sprites;

    public SpriteSheetLoad(int width, int height, int rows, int columns, int size, String fileName) {
        try {
            this.spriteSheet = ImageIO.read(new File(fileName));
            this.width = width;
            this.height = height;
            this.rows = rows;
            this.columns = columns;
            this.size = size;

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < columns; y++) {
                    sprites[(x * columns) + y] = spriteSheet.getSubimage(x * width, y * height, width, height);
                }
            }
        }catch(IOException ioe){
            System.out.println("Trouble reading from the file: " + ioe.getMessage());
        }
    }

    public static BufferedImage getSprite(int value) {
        return sprites[value];
    }


}