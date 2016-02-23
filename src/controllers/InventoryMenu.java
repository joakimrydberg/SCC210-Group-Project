package controllers;

import abstract_classes.Entity;
import components.ClickableImage;
import components.Image;
import components.Node;
import components.Slot;
import interfaces.Clickable;
import tools.Constants;

/**
 * Created by Ross on 21/02/2016.
 */
public class InventoryMenu extends Menu {
    public final static String NAME = "Inventory Menu";
    private ItemDescriptor[] itemDesc = new ItemDescriptor[42];

    public InventoryMenu(){
        super(NAME);
        //addEntity(new Image(625, 350, 520, 470, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "PANEL.png"));

        drawSlots(); //draws all inventory boxes and item descriptors

        addSlot("LONG", 625, 600, 520, 70);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof ClickableImage){
            Entity button = (ClickableImage) clickable;

            for(int i = 1; i < 36; i++) {
                if (button.getName().equals("" + i)) {
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

        addSlot("HELMET", x, y, "H", new ItemDescriptor("H", x, y, 200, 150));
        x += xadd;
        addSlot("ARMS", x, y, "A", new ItemDescriptor("A", x, y, 200, 150));
        x += xadd;
        addSlot("TORSO", x, y, "T", new ItemDescriptor("T", x, y, 200, 150));
        x += xadd;
        addSlot("BOOTS", x, y, "B", new ItemDescriptor("B", x, y, 200, 150));
        x += xadd;
        addSlot("WEAPON", x, y, "W", new ItemDescriptor("W", x, y, 200, 150));
        x += xadd;
        addSlot("SHIELD", x, y, "S", new ItemDescriptor("S", x, y, 200, 150));
        x += xadd;
        addSlot("POTION", x, y, "P", new ItemDescriptor("P", x, y, 200, 150));

        int name = 1;
        int d = 0;
        x = 400;
        y = 190;
        int yadd = 470 / 6;

        for(int i = 0; i < 5; y += yadd, i++) {
            for (int j = 0; j < 7; x +=xadd, j++) {
                ;
                itemDesc[d] = new ItemDescriptor("D" + name, x, y, 200, 150);
                addSlot("BLANK", x, y, "" + name, itemDesc[d]);

                Slot s = new Slot(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "BLANK.png", "" + name, itemDesc[d]);
                s.addClickListener(this);
                addEntity(s);
                name++;
                d++;
            }
            x = 400;
        }
    }

    private void addSlot(String slot, int x, int y, String name, ItemDescriptor desc) {
        Slot s = new Slot(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png", name, desc);
        s.addClickListener(this);
        addEntity(s);
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
