package controllers;

import components.Image;
import interfaces.Clickable;
import tools.Constants;

/**
 * Created by Ross on 21/02/2016.
 */
public class PlayerMenu extends Menu {
    public final static String NAME = "Player Menu";

    public PlayerMenu(){
        super(NAME);

        //top bar
        addSlot("LONG", 600, 150); //TODO resize / edit this with info

        //left slots
        addSlot("HELMET", 500, 250);
        addSlot("ARMS", 400, 350);
        addSlot("TORSO", 500, 350);
        addSlot("BOOTS", 500, 450);

        //character
        addSlot("LARGE", 650, 350);

        //right slots
        addSlot("WEAPON", 800, 250);
        addSlot("SHIELD", 800, 350);
        addSlot("POTION", 800, 450);
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
