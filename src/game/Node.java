/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author newby
 */
public class Node extends Entity implements Clickable {

    ArrayList<ClickListener> clickListeners = new ArrayList<>();
    private boolean locked = false;
    private Sprite img;

    public Node(String name, int x, int y, int radius, Color fillC, Color lineC, float pt, int transparency) {
        super(name);

        CircleShape circle = new CircleShape(radius);
        circle.setFillColor(new Color(fillC, transparency));
        circle.setOutlineColor(lineC);
        circle.setOutlineThickness(pt);
        circle.setOrigin(radius, radius);

        super.setWidthHeight(radius * 2, radius * 2);
        super.setCenterX(x);
        super.setCenterY(y);

        super.addTransformable(circle, 0, 0, radius, radius);

        // Load image/ texture
        Texture imgTexture = new Texture( );
        try {
            imgTexture.loadFromFile(Paths.get("assets" + Constants.SEP + "art" + Constants.SEP + "lock.png"));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }
        imgTexture.setSmooth(true);

        img = new Sprite(imgTexture);

        Vector2i imgSize = imgTexture.getSize();
        /*
        if (locked){
            // Load image/ texture
            Texture imgTexture = new Texture( );
            try {
                imgTexture.loadFromFile(Paths.get("assets" + Constants.SEP + "art" + Constants.SEP + "lock.png"));
            } catch (IOException ex) {
                ex.printStackTrace( );
            }
            imgTexture.setSmooth(true);

            Sprite img = new Sprite(imgTexture);

            Vector2i imgSize = imgTexture.getSize();
            int widthTemp = (radius * 2 < 0) ? imgSize.x : radius * 2;
            int heightTemp = (radius * 2 < 0) ? imgSize.y : radius * 2;
            setWidthHeight(widthTemp, heightTemp);

            setCenterX(x);
            setCenterY(y);
            addTransformable(img, 0, 0, 0, 0); //not supplying w and h so the origin will be 0
        }
        */
    }

    // Default method typically assumes a rectangle,
    // so do something a little different
    @Override
    public boolean checkWithin(int px, int py) {

        // In this example, For simplicty...
        // - We just treat circle as a box
        // - x, y is the top corner rather than the centre
        // - We use points x,y and px, py not bounding boxes
        return (px > getTopLeftX() && px < getTopLeftX() + 2 * getWidth() && py > getTopLeftY() && py < getTopLeftY() + 2 * getWidth());

    }

    @Override
    public boolean checkWithin(Event e) {
        Vector2i v = e.asMouseButtonEvent().position;
        return checkWithin(v.x, v.y);
    }

    @Override
    public void clicked(Event e) {
        for (ClickListener listener : clickListeners) {
            listener.buttonClicked(this, null);
        }
    }

    @Override
    public void addClickListener(ClickListener clickListener) {
        clickListeners.add(clickListener);
    }

    public void setLocked(boolean l){
        locked = l;

        if (locked){
            addTransformable(img, 0, 0, 0, 0); //not supplying w and h so the origin will be 0
        } else {
            hideTransformable(img);

        }
    }

    public void lokc(){
        //
    }
}
	
