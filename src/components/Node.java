/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import abstract_classes.Entity;
import interfaces.ClickListener;
import interfaces.Clickable;
import interfaces.MotionListener;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;
import tools.Constants;
import tools.MusicPlayer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author newby
 */
public class Node extends Entity implements Clickable, MotionListener {
    private String type = "Regular";
    ArrayList<ClickListener> clickListeners = new ArrayList<>();
    private boolean locked = false;
    private Sprite img;
    public int visitTime = Integer.MAX_VALUE;  //used by map menu
    private ArrayList<Rect> lines = new ArrayList<>();
    private boolean hovered = false;
    private ArrayList<Node> links = new ArrayList<>();

    public Node(String name, int x, int y, int radius, Color fillC, Color lineC, float pt, int transparency) {
        super(name);

        super.setWidthHeight(radius * 2, radius * 2);
        super.setCenterX(x);
        super.setCenterY(y);

        setColour(fillC, lineC, pt, transparency);

        // Load image / texture
        Texture imgTexture = new Texture( );
        try {
            imgTexture.loadFromFile(Paths.get("assets" + Constants.SEP + "art" + Constants.SEP + "lock.png"));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }
        imgTexture.setSmooth(true);

        img = new Sprite(imgTexture);
        addTransformable(img, 0, 0, radius, radius); //not supplying w and h so the origin will be 0
        hideTransformable(img);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean checkWithin(Event e) {
        Vector2i v = e.asMouseButtonEvent().position;
        return checkWithin(v.x, v.y);
    }

    @Override
    public void clicked(Event e) {
        if (locked){
            MusicPlayer.play("locked.wav");
        } else {
            MusicPlayer.play("button_click.wav");
        }

        for (ClickListener listener : clickListeners) {
            listener.buttonClicked(this, null);
        }
    }

    @Override
    public void addClickListener(ClickListener clickListener) {
        clickListeners.add(clickListener);
    }

    public void lock(){
        locked = true;
        showTransformable(img);
    }

    public void unlock(){
        locked = false;
        hideTransformable(img);
    }

    public boolean isLocked(){
        return locked;
    }

    @Override
    public void mouseMoved(Event event) {
        if (checkWithin(event.asMouseEvent().position.x, event.asMouseEvent().position.y)) {
            if (!hovered) {
                hovered = true;
                for (Rect line : lines) {
                    line.hidden = false;
                }
            }
        } else if (hovered) {
            hovered = false;
            for (Rect line : lines) {
                line.hidden = true;
            }
        }
    }


    public void clearLines() {
        this.lines = new ArrayList<>();
    }

    public void setColour(Color fillC, Color lineC, float pt, int transparency) {
        int radius = getWidth() / 2;
        // Load circle for node
        CircleShape circle = new CircleShape(radius);
        circle.setFillColor(new Color(fillC, transparency));
        circle.setOutlineColor(lineC);
        circle.setOutlineThickness(pt);
        circle.setOrigin(radius, radius);


        super.addTransformable(circle, radius, radius, radius * 2, radius * 2);
    }

    public void addNodeLinks(ArrayList<Rect> lines, Node node) {
        for (Rect line : lines) {
            line.hidden = true;
        }
        this.lines.addAll(lines);

        this.links.add(node);
    }

    public void addNodeLink(Rect line, Node node) {
        line.hidden = true;
        this.lines.add(line);
        this.links.add(node);
    }

    public ArrayList<Node> getLinks() {
        return links;
    }

}
	
