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

//    private final static String SEP = Constants.SEP;
//    public int loaded = 0;
//    ArrayList<ClickListener> clickListeners = new ArrayList<>();

    public NodeDescriptor(String name, Node n, int width, int height) {
        super(name);

        RenderWindow w = getWindow();
        final Vector2i windowSize = new Vector2i(width, height);
        final int centerX = n.getCenterX() + (width / 2), centerY = n.getCenterY() - (height / 2);

        addEntity(new Image(centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu_titled.png"));

        addEntity(new Message(centerX, centerY - 37, 0, name, Color.BLACK, 10));

        Button btnClose = new Button(centerX - 50, centerY + 10, 30, 40, Color.WHITE, 200 , "X", 15 );
        btnClose.addClickListener(this);
        addEntity(btnClose);

        Button btnPlay = new Button(centerX + 30, centerY + 10, 80, 40, Color.WHITE, 200 , "PLAY", 15 );
        btnPlay.addClickListener(this);
        addEntity(btnPlay);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof  Button ) {
            Entity button = (Button) clickable;
            if (button.getName().equals("X")) {
                this.unload();
                System.out.println("Close clicked");
            } else if (button.getName().equals("PLAY")) {
                System.out.println("Play clicked");
            }
        }
    }
}
	
