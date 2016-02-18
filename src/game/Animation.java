package game;

import org.jsfml.graphics.Sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Michael on 17/02/2016.
 */
public class Animation extends Entity {
    private int frameCount;
    private int frameDelay = 10;
    private int currentFrame;
    private int totalFrames;                // total amount of frames for your animation

    private boolean stopped;                // have animations stopped

    private List<AnimationFrame> frames = new ArrayList<AnimationFrame>();    // Arraylist of frames

    public Animation(int x, int y, int width, int height, BufferedImage[] frames) {
        super("name");

        setWidthHeight(width, height);

        setCenterX(x);
        setCenterY(y);
        this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i]);
        }

        this.frameCount = 0;
        this.currentFrame = 0;
        this.totalFrames = this.frames.size();

    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }

        stopped = false;
    }

    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    private void addFrame(BufferedImage frame) {
        frames.add(new AnimationFrame(frame));
        currentFrame = 0;
    }

    public Sprite getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    public void update() {
        if (!stopped) {
            frameCount++;

            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += 1;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }

    }

    @Override
    public void draw() {
        if (!stopped) {
            update();

            removeTransformable(0);  ///there should only ever be one
            addTransformable(getSprite(), 0, 0, 0, 0);

            super.draw();
        }
    }

}
