package game;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.awt.*;
import java.awt.Image;
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
        this.frame = sprite;
    }

}
