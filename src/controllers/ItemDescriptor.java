/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import abstract_classes.Entity;
import components.*;
import game.Level;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import tools.Constants;

/**
 *
 * @author newby
 */
public class ItemDescriptor extends Menu {
    private final static String SEP = Constants.SEP;
    private static Image smallMenu;

    public ItemDescriptor(String name, int x, int y, int width, int height, InventoryMenu inv) {
        super(name);

        RenderWindow w = getWindow();
        final Vector2i windowSize = new Vector2i(width, height);
        int centerX = x + (width / 2), centerY = y - (height / 2);

        smallMenu = new components.Image(centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu.png");
        addEntity(smallMenu);

        //addEntity(new Image(centerX, centerY, windowSize.x - 50, 30, "assets" + Constants.SEP + "art" + Constants.SEP + "game_label.png"));

        //addEntity(new Message(centerX, centerY - 55, 0, name, Color.BLACK, 10)); //title

        //addEntity(new Message(centerX, centerY - 5, 0, this.difficulty, txtCol, 15));

//        Button btnClose = new Button(centerX - 50, centerY + 35, 30, 30, "RED", 200 , "<", 15 );
//        btnClose.addClickListener(this);
//        addEntity(btnClose);
//
//        Button btnPlay = new Button(centerX + 20, centerY + 35, 90, 30, "GREEN", 200 , "PLAY", 15 );
//        btnPlay.addClickListener(this);
//        addEntity(btnPlay);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        //
    }
}
	
