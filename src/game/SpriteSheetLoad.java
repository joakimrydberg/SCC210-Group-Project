package game;

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
    BufferedImage[] sprites;

    public SpriteSheetLoad(int width, int height, int rows, int columns, int size, String fileName) {
        try {
            spriteSheet = ImageIO.read(new File(fileName));
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

    public BufferedImage[] returnSprites(){return sprites;}

    public BufferedImage move(int direction){
        for(int i=0; i<3; i++)
        {
            return sprites[direction+i];

        }
        return sprites[direction];
    }

    //Given an array of numbers makes the room
    public BufferedImage[] roomMake(int[] roomDesign){
        BufferedImage[] tempRoom = new BufferedImage[169];
        for(int i=0; i<169; i++)
        {
            switch (roomDesign[i]) {
                case 0:
                    tempRoom[i] = sprites[0];
                    break;
                case 1:
                    tempRoom[i] = sprites[1];
                    break;
                case 2:
                    tempRoom[i] = sprites[2];
                    break;
                case 3:
                    tempRoom[i] = sprites[3];
                    break;
                case 4:
                    tempRoom[i] = sprites[4];
                    break;
                case 5:
                    tempRoom[i] = sprites[5];
                    break;
                case 6:
                    tempRoom[i] = sprites[6];
                    break;
                case 7:
                    tempRoom[i] = sprites[7];
                    break;
                case 8:
                    tempRoom[i] = sprites[8];
                    break;
                case 9:
                    tempRoom[i] = sprites[9];
                    break;
                case 10:
                    tempRoom[i] = sprites[10];
                    break;
                case 11:
                    tempRoom[i] = sprites[11];
                    break;
                case 12:
                    tempRoom[i] = sprites[12];
                    break;
                case 13:
                    tempRoom[i] = sprites[13];
                    break;
                case 14:
                    tempRoom[i] = sprites[14];
                    break;
            }
        }
        return tempRoom;
    }


}