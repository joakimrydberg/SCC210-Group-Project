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
    private static Image smallMenu;

    public NodeDescriptor(String name, Node n, int width, int height) {
        super(name);

        RenderWindow w = getWindow();
        final Vector2i windowSize = new Vector2i(width, height);
        int centerX = n.getCenterX() + (width / 2), centerY = n.getCenterY() - (height / 2);

        //stop the descriptor appearing off the edge of the screen
        int topPoint = centerY - (height / 2);
        if(topPoint < 0) {
            centerY += Math.abs(topPoint);
        }
        int rightPoint = centerX + (width / 2);
        if(rightPoint > w.getSize().x){
            centerX -= (rightPoint - w.getSize().x);
        }

        smallMenu = new Image(centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu_titled.png");
        addEntity(smallMenu);

        addEntity(new Message(centerX, centerY - 37, 0, name, Color.BLACK, 10));

        Button btnClose = new Button(centerX - 50, centerY + 10, 30, 40, "RED", 200 , "<", 15 );
        btnClose.addClickListener(this);
        addEntity(btnClose);

        Button btnPlay = new Button(centerX + 20, centerY + 10, 90, 40, "GREEN", 200 , "PLAY", 15 );
        btnPlay.addClickListener(this);
        addEntity(btnPlay);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof Button) {
            Entity button = (Button) clickable;
            if (button.getName().equals("PLAY")) {
                System.out.println("Play clicked");
            }
            else if (button.getName().equals("<")) {
                this.unload();
                System.out.println("Close clicked");
            }
        }
    }
}
	
