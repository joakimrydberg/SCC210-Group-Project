package controllers;

import abstract_classes.Entity;
import components.*;
import components.Button;
import components.Image;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import tools.Constants;

import java.util.ArrayList;

/**
 * Created by Ross on 21/02/2016.
 */
public class InventoryMenu extends Menu {
    public final static String NAME = "Inventory Menu";
    private ItemDescriptor[] itemDesc = new ItemDescriptor[42];
    private Slot[] slots = new Slot[42];
    private Slot clickedSlot = null;

    private int count;
    private Button btnEquipt = null, btnDiscard = null;
    private boolean firstClick = true;
    Message n = null, d = null;
    Image img = new Image(450, 610, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "EMPTY.png");

    public InventoryMenu() {
        super(NAME);

        drawSlots(); //draws all inventory boxes and item descriptors

        addSlot("LONG2", 625, 610, 520, 100);

        addSlot("EMPTY", 450, 610, 70, 70);
        n = new Message(550, 590, 0, "Select an Item!", Color.WHITE, 12);
        d = new Message(550, 620, 0, "Select an Item!", Color.WHITE, 12);
        addEntity(n);
        addEntity(d);
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
            Slot slot = (Slot) clickable;
            clickedSlot = (Slot) clickable;

            if (slot.hasItem()) {
                if(firstClick) {
                    btnEquipt = new Button(800, 595, 90, 30, "YELLOW", 200, "EQUIPT", 15);
                    btnEquipt.addClickListener(this);
                    addEntity(btnEquipt);

                    btnDiscard = new Button(800, 630, 90, 30, "RED", 200, "DISCARD", 15);
                    btnDiscard.addClickListener(this);
                    addEntity(btnDiscard);

                    img.changeImage(slot.getItem().getItemIcon());
                    addEntity(img);

                    n.setText(slot.getItem().getName());
                    d.setText(slot.getItem().getDescription());

                    firstClick = false;
                }else{
                    img.show();
                    btnEquipt.show();
                    btnDiscard.show();
                    img.changeImage(slot.getItem().getItemIcon());
                    n.setText(slot.getItem().getName());
                    d.setText(slot.getItem().getDescription());
                }
            }else {
                img.hide();
                btnEquipt.hide();
                btnDiscard.hide();
                n.setText("Empty Inventory Slot!");
                    d.setText("");
            }

            for(int i = 1; i < 36; i++) {
                if (slot.getName().equals("" + i)) {
                    //loadDrawer(ItemMenu.class);
                    //itemMenu.load();
                    System.out.println("Slot " + i + " clicked");
                }
            }

            if (slot.getName().equals("H")) {
                System.out.println("HELMET clicked");
            } else if (slot.getName().equals("A")) {
                System.out.println("ARMS clicked");
            } else if (slot.getName().equals("T")) {
                System.out.println("TORSO clicked");
            } else if (slot.getName().equals("B")) {
                System.out.println("BOOTS clicked");
            } else if (slot.getName().equals("W")) {
                System.out.println("WEAPON clicked");
            } else if (slot.getName().equals("S")) {
                System.out.println("SHIELD clicked");
            } else if (slot.getName().equals("P")) {
                System.out.println("POTION clicked");
            }
        }else if (clickable instanceof Button) {
            Entity button = (Button) clickable;

            if (button.getName().equals("EQUIPT")) {
                System.out.println("EQUIPT clicked");
            }else if (button.getName().equals("DISCARD")) {
                System.out.println("DISCARD clicked");
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
