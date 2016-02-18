/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author newby
 */
public class NodeDescriptor extends Menu {

    private final static String SEP = Constants.SEP;
    public int loaded = 0;
    ArrayList<ClickListener> clickListeners = new ArrayList<>();

    public NodeDescriptor(String name, Node n, int width, int height) {
        super(name);

        RenderWindow w = getWindow();
        final Vector2i windowSize = new Vector2i(width, height);
        final int centerX = n.getCenterX() + (width / 2), centerY = n.getCenterY() - (height / 2);

        //addEntity(new Rect("box", n.getCenterX() + (width / 2), n.getCenterY() + (height / 2), windowSize.x, windowSize.y, Color.WHITE, 300 ));
        addEntity(new Image(n.getCenterX() + (width / 2), n.getCenterY() - (height / 2), windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu_titled.png"));

        Button btnPlay = new Button(centerX, centerY + 10, 80, 40, Color.WHITE, 200 , "PLAY", 15 );
        btnPlay.addClickListener(this);
        addEntity(btnPlay);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof  Button ) {
            Entity button = (Button) clickable;
            if (button.getName().equals("PLAY")) {
                //this.unload();
                System.out.println("Play clicked");
            }
        }
    }
}
	
