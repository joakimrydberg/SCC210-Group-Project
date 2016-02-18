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

    public AnimationFrame(BufferedImage frame) {
        setFrame(frame);
    }

    //Gets the current frame
    public Sprite getFrame() {
        return frame;
    }

    //Sets the current frame
    public void setFrame(BufferedImage frame) {
        Texture t = new Texture();
        org.jsfml.graphics.Image image = new org.jsfml.graphics.Image();
        image.create(frame);
        try {
            t.loadFromImage(image);
        } catch (TextureCreationException e) {
            e.printStackTrace();
        }



        Sprite sprite = new Sprite(t);

        Vector2i imgSize = t.getSize();

        int widthTemp = (64 < 0) ? imgSize.x : 64;
        int heightTemp = (128 < 0) ? imgSize.y : 128;

        sprite.scale((float)(widthTemp / (imgSize.x/3.0)), (float)(heightTemp / (imgSize.y / 3.0)));

        this.frame = sprite;
    }

}
