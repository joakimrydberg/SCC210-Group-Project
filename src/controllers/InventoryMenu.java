package controllers;

import abstract_classes.Entity;
import components.*;
import interfaces.Clickable;
import tools.Constants;

import java.util.ArrayList;

/**
 * Created by Ross on 21/02/2016.
 */
public class InventoryMenu extends Menu {
    public final static String NAME = "Inventory Menu";
    private ItemDescriptor[] itemDesc = new ItemDescriptor[42];
    private Slot[] slots = new Slot[42];
    //private ItemMenu itemMenu = new ItemMenu();
    private int count;

    public InventoryMenu() {
        super(NAME);

        drawSlots(); //draws all inventory boxes and item descriptors

        addSlot("LONG", 625, 600, 520, 70);
    }

    public void populateMenu(ArrayList<Item> inventory){
        //display items in playes inventory
        for(int i = 0; i < inventory.size(); i++){
            slots[i + 7].addItem(inventory.get(i));
        }
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof Slot){
            Entity button = (Slot) clickable;

            for(int i = 1; i < 36; i++) {
                if (button.getName().equals("" + i)) {
                    //loadDrawer(ItemMenu.class);
                    //itemMenu.load();
                    System.out.println("Slot " + i + " clicked");
                }
            }

            if (button.getName().equals("H")) {
                System.out.println("HELMET clicked");
            } else if (button.getName().equals("A")) {
                System.out.println("ARMS clicked");
            } else if (button.getName().equals("T")) {
                System.out.println("TORSO clicked");
            } else if (button.getName().equals("B")) {
                System.out.println("BOOTS clicked");
            } else if (button.getName().equals("W")) {
                System.out.println("WEAPON clicked");
            } else if (button.getName().equals("S")) {
                System.out.println("SHIELD clicked");
            } else if (button.getName().equals("P")) {
                System.out.println("POTION clicked");
            }
        }
    }

    private void drawSlots(){
        int x = 400, y = 100;
        int xadd = 520 / 7;

        count = 0;

        addSlot("HELMET", x, y, "H");
        x += xadd;
        addSlot("ARMS", x, y, "A");
        x += xadd;
        addSlot("TORSO", x, y, "T");
        x += xadd;
        addSlot("BOOTS", x, y, "B");
        x += xadd;
        addSlot("WEAPON", x, y, "W");
        x += xadd;
        addSlot("SHIELD", x, y, "S");
        x += xadd;
        addSlot("POTION", x, y, "P");

        int name = 1;
        count = 0;
        x = 400;
        y = 190;
        int yadd = 470 / 6;

        for(int i = 0; i < 5; y += yadd, i++) {
            for (int j = 0; j < 7; x +=xadd, j++) {
                itemDesc[count + 7] = new ItemDescriptor("Slot " + name, x, y);

                Slot s = new Slot(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "BLANK.png", "" + name, itemDesc[count + 7]);
                s.addClickListener(this);
                addEntity(s);

                slots[count + 7] = s;

                name++;
                count++;
            }
            x = 400;
        }
    }

    private void addSlot(String slot, int x, int y, String name) {
        itemDesc[count] = new ItemDescriptor(slot, x, y);

        Slot s = new Slot(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png", name, itemDesc[count]);
        s.addClickListener(this);
        addEntity(s);

        count++;
    }

    private void addSlot(String slot, int x, int y) {
        addEntity(new ClickableImage(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot +".png"));
    }

    private void addSlot(String slot, int x, int y, int length, int height, String name) {
        ClickableImage s = new ClickableImage(x, y, length, height, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png", name);
        s.addClickListener(this);
        addEntity(s);
    }

    private void addSlot(String slot, int x, int y, int length, int height) {
        addEntity(new ClickableImage(x, y, length, height, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png"));
    }
}
