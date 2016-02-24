package controllers;

import abstract_classes.Entity;
import components.*;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import tools.Constants;

import java.util.ArrayList;

/**
 * Created by Ross on 23/02/2016.
 */
public class ShopMenu extends Menu {
    public final static String NAME = "Shop Menu";
    private ItemDescriptor[] itemDesc = new ItemDescriptor[28];
    private Slot[] slots = new Slot[28];
    private int count = 0;
    private int btnX = 170, btnY = 150;

    private Button btnBuy = new Button(800, 450, 90, 30, "GREEN", 200, "BUY", 15);
    private Message n = null, d = null;
    private Image img = new Image(450, 440, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "EMPTY.png");
    private Image coins = new Image(800, 405, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "coins.png");

    private Slot clickedSlot = null;

    ArrayList<Item> helmets = new ArrayList<Item>();
    ArrayList<Item> torsos = new ArrayList<Item>();
    ArrayList<Item> arms = new ArrayList<Item>();
    ArrayList<Item> boots = new ArrayList<Item>();
    ArrayList<Item> weapons = new ArrayList<Item>();
    ArrayList<Item> shields = new ArrayList<Item>();
    ArrayList<Item> potions = new ArrayList<Item>();

    public ShopMenu(){
        super(NAME);

        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "shop_image.png"));

        //Button btnExit = new Button(100, 595, 90, 30, "YELLOW", 200, "EQUIPT", 15);
        addOption("HELMETS", "BROWN");
        addOption("TORSOS", "BROWN");
        addOption("ARMS", "BROWN");
        addOption("BOOTS", "BROWN");
        addOption("WEAPONS", "BROWN");
        addOption("SHIELDS", "BROWN");
        addOption("POTIONS", "BROWN");
        addOption("EXIT SHOP", 600, "RED");

        addEntity(new Rect(null, 300, centerY, 2, 600, new Color(126, 89, 106), 250)); //seperation border

        createItems();

        drawSlots();

        addSlot("LONG2", 625, 440, 520, 100);
        addSlot("EMPTY", 450, 440, 70, 70);
        n = new Message(550, 420, 0, "Select an Item!", Color.WHITE, 12);
        d = new Message(550, 450, 0, "Select an Item!", Color.WHITE, 12);
        addEntity(n);
        addEntity(d);
        addEntity(btnBuy);
        btnBuy.hide();
    }

    public void populateMenu(ArrayList<Item> items){
        for(int i = 0; i < 28; i++){
            if(slots[i].hasItem()){
                slots[i].removeItem();
            }
        }

        for(int i = 0; i < items.size(); i++){ //display items in passed in ArrayList
            System.out.println(items.get(i).getName()); //debug
            slots[i].addItem(items.get(i));
        }
    }

    public void createItems(){
        weapons.add(new Item("Basic Staff", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "staff0.png"), "A basic staff"));
        weapons.add(new Item("Basic Bow", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "bow0.png"), "A basic bow"));
        weapons.add(new Item("Basic Sword", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "sword0.png"), "A basic sword"));
    }

    private void drawSlots(){
        int name = 1;
        int x = 400, y = 100;

        int xadd = 520 / 7;
        int yadd = 470 / 6;

        count = 0;

        for(int i = 0; i < 4; y += yadd, i++) {
            for (int j = 0; j < 7; x +=xadd, j++) {
                itemDesc[count] = new ItemDescriptor("Slot " + name, x, y);

                Slot s = new Slot(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "BLANK.png", "" + name, itemDesc[count]);
                s.addClickListener(this);
                addEntity(s);

                slots[count] = s;

                name++;
                count++;
            }
            x = 400;
        }
    }

    private void addOption(String text, String colour){
        Button btn = new Button(btnX, btnY, 200, 40, colour, 200, text, 15);
        btn.addClickListener(this);
        addEntity(btn);

        btnY += 50;
    }

    private void addOption(String text, int y, String colour){
        Button btn = new Button(btnX, y, 200, 40, colour, 200, text, 15);
        btn.addClickListener(this);
        addEntity(btn);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof Slot) {
            Slot slot = (Slot) clickable;
            clickedSlot = (Slot) clickable;

            if (slot.hasItem()) {
                btnBuy.show();

                img.changeImage(slot.getItem().getItemIcon());
                addEntity(img);
                img.show();

                addEntity(coins);
                coins.show();

                n.setText(slot.getItem().getName());
                d.setText(slot.getItem().getDescription());

            } else {
                img.hide();
                coins.hide();
                btnBuy.hide();
                n.setText("Theres Nothing for Sale in that Slot!");
                d.setText("");
            }

            for (int i = 1; i < 36; i++) {
                if (slot.getName().equals("" + i)) {
                    System.out.println("Slot " + i + " clicked");
                }
            }
        } else if (clickable instanceof Button) {
            Entity button = (Button) clickable;

            if (button.getName().equals("HELMETS")) {
                populateMenu(helmets);
                System.out.println("HELMETS clicked");
            } else if (button.getName().equals("TORSOS")) {
                populateMenu(torsos);
                System.out.println("TORSOS clicked");
            } else if (button.getName().equals("ARMS")) {
                populateMenu(arms);
                System.out.println("ARMS clicked");
            } else if (button.getName().equals("BOOTS")) {
                populateMenu(boots);
                System.out.println("BOOTS clicked");
            } else if (button.getName().equals("WEAPONS")) {
                populateMenu(weapons);
                System.out.println("WEAPONS clicked");
            } else if (button.getName().equals("SHIELDS")) {
                populateMenu(shields);
                System.out.println("SHIELDS clicked");
            } else if (button.getName().equals("POTIONS")) {
                populateMenu(potions);
                System.out.println("POTIONS clicked");
            } else if (button.getName().equals("EXIT SHOP")) {
                this.unload();
                loadDrawer(MapMenu.class);
                System.out.println("EXIT SHOP clicked");
            } else if (button.getName().equals("BUY")) {
                System.out.println("BUY clicked");
            }
        }
    }

    private void addSlot(String slot, int x, int y, String name) {
        itemDesc[count] = new ItemDescriptor(slot, x, y);

        Slot s = new Slot(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png", name, itemDesc[count]);
        s.addClickListener(this);
        addEntity(s);

        count++;
    }

    private void addSlot(String slot, int x, int y, int length, int height) {
        addEntity(new ClickableImage(x, y, length, height, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png"));
    }
}
