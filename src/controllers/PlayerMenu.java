package controllers;

import components.Animation;
import components.Image;
import components.Message;
import components.Rect;
import game.SpriteSheetLoad;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import tools.Constants;

import java.awt.image.BufferedImage;

/**
 * Created by Ross on 21/02/2016.
 */
public class PlayerMenu extends Menu {
    public final static String NAME = "Player Menu";
    private int money = 100, lvl = 1;

    public PlayerMenu(){
        super(NAME);

        addSlot("LONG", 625, 150, 520, 70); //top panel

        //add the players money
        addEntity(new Message(430, 145, 0, Integer.toString(money), Color.WHITE, 20)); //TODO pass in actual currency
        addEntity(new Image(500, 150, "assets" + Constants.SEP + "art" + Constants.SEP + "coins.png"));

        addSlot("LVL", 585, 150, 85, 85); //level box
        addEntity(new Message(585, 150, 0, Integer.toString(lvl), Color.WHITE, 30)); //TODO pass in actual level

        //experiance bar
        //TODO add functioning xp bar, this is just for show atm
        addEntity(new Rect(null, 730, 150, 175, 15, Color.WHITE, 250));
        addEntity(new Image(850, 150, "assets" + Constants.SEP + "art" + Constants.SEP + "xp_icon.png"));

        //left slots
        addSlot("HELMET", 500, 250);
        addSlot("ARMS", 400, 350);
        addSlot("TORSO", 500, 350);
        addSlot("BOOTS", 500, 450);

        //character
        addSlot("LARGE", 670, 350, 203, 270);

        //right slots
        addSlot("WEAPON", 850, 250);
        addSlot("SHIELD", 850, 350);
        addSlot("POTION", 850, 450);

        //bottom bar //TODO dont know what to populate this or if its needed but its here to loog good tbh
        addSlot("LONG", 625, 550, 520, 70); //TODO resize / edit this with info
    }

    private void addSlot(String slot, int x, int y){
        addEntity(new Image(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot +".png"));
    }

    private void addSlot(String slot, int x, int y, int length, int height){
        addEntity(new Image(x, y, length, height, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot +".png"));
    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {
        //
    }
}
