package components;

import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.window.event.Event;
import tools.MusicPlayer;

import java.util.ArrayList;

/**
 * Created by millsr3 on 16/02/2016.
 */
public class ClickableImage extends Image implements Clickable {
    ArrayList<ClickListener> clickListeners = new ArrayList<>();

    public ClickableImage( int x, int y, int width, int height,  String textureFile){
        super( x,  y,  width,  height,   textureFile);
    }

    public ClickableImage( int x, int y, int width, int height,  String textureFile, String name){
        super( x,  y,  width,  height,   textureFile);
        super.setName(name);
    }

    public ClickableImage( int x, int y, String textureFile) {
        this(x, y, -1, -1, textureFile);
    }

    public ClickableImage( int x, int y, String textureFile, String name) {
        this( x, y, -1, -1, textureFile);
        super.setName(name);
    }

    @Override
    public void clicked(Event e) {
        MusicPlayer.play("button_click.wav");

        for (ClickListener listener : clickListeners) {
            listener.buttonClicked(this, null);
        }
    }

    @Override
    public void addClickListener(ClickListener clickListener) {
        clickListeners.add(clickListener);
    }

}
