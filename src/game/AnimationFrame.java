package game;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Vector2i;

import java.awt.image.BufferedImage;

/**
* Created by Michael on 16/02/2016.
 */
//Current frame of animation
public class AnimationFrame {

    private Sprite frame;

    public AnimationFrame(BufferedImage frame, int width, int height, float scale) {
        setFrame(frame, width, height, scale);
    }

    //Gets the current frame
    public Sprite getFrame() {
        return frame;
    }

    //Sets the current frame
    public void setFrame(BufferedImage frame, int width, int height, float scale) {
        Texture t = new Texture();
        org.jsfml.graphics.Image image = new org.jsfml.graphics.Image();
        image.create(frame);
        try {
            t.loadFromImage(image);
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }


        Sprite sprite = new Sprite(t);

       // sprite.setOrigin(0 + width / 2,0 + height / 2);
        Vector2i imgSize = t.getSize();

        int widthTemp = (width < 0) ? imgSize.x : width;
        int heightTemp = (height < 0) ? imgSize.y : height;

        sprite.scale((float)(widthTemp / (imgSize.x/scale)), (float)(heightTemp / (imgSize.y / scale)));

        this.frame = sprite;
    }

}
