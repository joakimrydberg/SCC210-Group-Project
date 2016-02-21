package controllers;

import components.Animation;
import components.Image;
import game.SpriteSheetLoad;
import interfaces.Clickable;
import tools.Constants;

import java.awt.image.BufferedImage;

/**
 * Created by Ross on 21/02/2016.
 */
public class PlayerMenu extends Menu {
    public final static String NAME = "Player Menu";

    public PlayerMenu(){
        super(NAME);

        //top bar
        addSlot("LONG", 625, 150, 520, 70); //TODO resize / edit this with info

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
