package controllers;

import abstract_classes.Entity;
import components.*;
import game.SpriteSheetLoad;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import tools.Constants;
import tools.DebugPrinter;

import java.awt.image.BufferedImage;

/**
 * Created by Ross on 21/02/2016.
 */
public class PlayerMenu extends Menu {
    public final static String NAME = "Player Menu";
    private int money = 100, lvl = 1, ag = 0, in = 0, at = 0, en = 0, vi = 0, he = 100;
    private ItemDescriptor[] itemDesc = new ItemDescriptor[7];
    private Slot[] slots = new Slot[7];
    private int count = 0;

    public PlayerMenu() {
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
        addSlot("HELMET", 500, 250, "HELMET");
        addSlot("ARMS", 400, 350, "ARMS");
        addSlot("TORSO", 500, 350, "TORSO");
        addSlot("BOOTS", 500, 450, "BOOTS");

        //character
        addSlot("LARGE", 670, 350, 203, 270);

        //right slots
        addSlot("WEAPON", 850, 250, "WEAPON");
        addSlot("SHIELD", 850, 350, "SHIELD");
        addSlot("POTION", 850, 450, "POTION");

        //bottom bar //TODO dont know what to populate this or if its needed but its here to loog good tbh
        addSlot("LONG2", 625, 580, 520, 120); //TODO resize / edit this with info

        addEntity(new Message(500, 550, 0, "Attack power : " + at, Color.WHITE, 12));
        addEntity(new Message(500, 575, 0, "Intellect : " + in, Color.WHITE, 12));
        addEntity(new Message(500, 600, 0, "Agility : " + ag, Color.WHITE, 12));
        addEntity(new Message(700, 550, 0, "Endurance : " + en, Color.WHITE, 12));
        addEntity(new Message(700, 575, 0, "Vitality : " + vi, Color.WHITE, 12));
        addEntity(new Message(700, 600, 0, "Health : " + he, Color.WHITE, 12));
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof ClickableImage) {

            Entity button = (ClickableImage) clickable;

            if (button.getName().equals("HELMET")) {
                System.out.println("HELMET clicked");
            } else if (button.getName().equals("ARMS")) {
                System.out.println("ARMS clicked");
            } else if (button.getName().equals("TORSO")) {
                System.out.println("TORSO clicked");
            } else if (button.getName().equals("BOOTS")) {
                System.out.println("BOOTS clicked");
            } else if (button.getName().equals("WEAPON")) {
                System.out.println("WEAPON clicked");
            } else if (button.getName().equals("SHIELD")) {
                System.out.println("SHIELD clicked");
            } else if (button.getName().equals("POTION")) {
                System.out.println("POTION clicked");
            }
        }
    }

    private void addSlot(String slot, int x, int y, String name) {
        itemDesc[count] = new ItemDescriptor(name, x, y);

        Slot s = new Slot(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png", name, itemDesc[count]);
        //s.addClickListener(this);
        addEntity(s);

        slots[count] = s;

        count++;
        //System.out.println(slots[count].getName());
    }

    private void addSlot(String slot, int x, int y, int length, int height) {
        addEntity(new ClickableImage(x, y, length, height, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png"));
    }


//    private void addSlot(String slot, int x, int y) {
//        addEntity(new ClickableImage(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot +".png"));
//    }
//
//    private void addSlot(String slot, int x, int y, int length, int height, String name) {
//        ClickableImage s = new ClickableImage(x, y, length, height, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png", name);
//        s.addClickListener(this);
//        addEntity(s);
//    }
}
