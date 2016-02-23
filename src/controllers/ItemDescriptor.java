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
    private static Image smallMenu;
    private int width = 220, height = 34;
    private Message title;

    public ItemDescriptor(String name, int x, int y) {
        super(name);

        int centerX = x + (width / 2), centerY = y - (height / 2);
        smallMenu = new components.Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "game_label.png");
        addEntity(smallMenu);
        title = new Message(centerX, centerY, 0, this.getName(), Color.WHITE, 10); //title
        //title.setWidthHeight(200, 30);
        //title.setCenterX(centerX);

        addEntity(title);
    }

    public void setTitle(String txt){
        title.setText(txt);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        //nothing
    }
}
	
