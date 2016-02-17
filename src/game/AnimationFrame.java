package game;
import java.awt.image.BufferedImage;

/**
 * Created by Michael on 16/02/2016.
 */
//Current frame of animation
public class AnimationFrame {

    private BufferedImage frame;

    public AnimationFrame(BufferedImage frame) {
        this.frame = frame;
    }

    //Gets the current frame
    public BufferedImage getFrame() {
        return frame;
    }

    //Sets the current frame
    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

}
