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
    private Message n = null, d = null, price = null, errorMess = null;
    private Image img = new Image(450, 440, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "EMPTY.png");
    private Image coins = new Image(825, 420, 20, 20, "assets" + Constants.SEP + "art" + Constants.SEP + "coin.png");

    private Slot clickedSlot = null;

    private ArrayList<Item> helmets = new ArrayList<Item>();
    private ArrayList<Item> torsos = new ArrayList<Item>();
    private ArrayList<Item> arms = new ArrayList<Item>();
    private ArrayList<Item> boots = new ArrayList<Item>();
    private ArrayList<Item> weapons = new ArrayList<Item>();
    private ArrayList<Item> shields = new ArrayList<Item>();
    private ArrayList<Item> potions = new ArrayList<Item>();

    private ArrayList<Item> lastList;

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

        populateMenu(helmets);

        addSlot("LONG2", 625, 440, 520, 100);
        addSlot("EMPTY", 450, 440, 70, 70);
        n = new Message(550, 420, 0, "Select an Item!", Color.WHITE, 12);
        d = new Message(550, 450, 0, "Select an Item!", Color.WHITE, 12);
        errorMess = new Message(550, 420, 0, "Not enough money", Color.WHITE, 12);
        price = new Message(775, 410, 0, "", Color.WHITE, 12);
        btnBuy.addClickListener(this);
        addEntity(price);
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

        lastList = items;
    }

    public void createItems(){
        String path = "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP;

        //HELMETS
        helmets.add(new Item("Basic Helmet", new Image(10, 10, path + "helmet1.png"), "A basic helmet.", "HELMET", 100, 50));
        helmets.add(new Item("Beginners Helmet", new Image(10, 10, path + "helmet2.png"), "A warrior's first helm.", "HELMET", 200, 100));
        helmets.add(new Item("Hardened Helmet", new Image(10, 10, path + "helmet3.png"), "Protect your head.", "HELMET", 300, 150));
        helmets.add(new Item("Swift Helmet", new Image(10, 10, path + "helmet4.png"), "Keep your head on a swivel.", "HELMET", 400, 200));
        helmets.add(new Item("Warrior's Helmet", new Image(10, 10, path + "helmet5.png"), "A true warrior's helmet.", "HELMET", 500, 250));
        helmets.add(new Item("The Bull", new Image(10, 10, path + "helmet6.png"), "Gendry?", "HELMET", 600, 300));
        helmets.add(new Item("Mo's Helmet", new Image(10, 10, path + "helmetS.png"), "Sweet.", "HELMET", 700, 350));
        helmets.add(new Item("The Skull", new Image(10, 10, path + "helmetS2.png"), "Never end up like Oberyn!", "HELMET", 800, 400));

        //TORSOS
        torsos.add(new Item("Basic Armor", new Image(10, 10, path + "torso0.png"), "Basic chain mail.", "TORSO", 300, 150));
        torsos.add(new Item("Advanced Armor", new Image(10, 10, path + "torsoS.png"), "No blade shall penetrate it.", "TORSO", 600, 300));

        //ARMS
        arms.add(new Item("Basic Gloves", new Image(10, 10, path + "arms0.png"), "Basic gloves.", "ARM", 150, 75));
        arms.add(new Item("Metal Gloves", new Image(10, 10, path + "armsS.png"), "Never lose a finger again.", "ARM", 300, 150));

        //BOOTS
        boots.add(new Item("Basic Shoes", new Image(10, 10, path + "boots0.png"), "A basic shoe.", "BOOT", 100, 50));
        boots.add(new Item("Beginners Boots", new Image(10, 10, path + "boots1.png"), "Basic boots.", "BOOT", 200, 100));
        boots.add(new Item("Hardened Boots", new Image(10, 10, path + "boots2.png"), "Boots that'll last the distance.", "BOOT", 300, 150));
        boots.add(new Item("Toughened Boots", new Image(10, 10, path + "boots3.png"), "When you need excellent foot protection.", "BOOT", 400, 200));
        boots.add(new Item("Warrior's Slip On's", new Image(10, 10, path + "boots4.png"), "A warrior's basic.", "BOOT", 500, 250));
        boots.add(new Item("Mo's Boots", new Image(10, 10, path + "bootsS.png"), "What are THOOOSE!", "BOOT", 600, 300));

        //WEAPONS
        weapons.add(new Item("Basic Staff", new Image(10, 10,  path + "staff0.png"), "A basic staff.", "WEAPON", 100, 50));
        weapons.add(new Item("Beginners Staff", new Image(10, 10,  path + "staff1.png"), "Every mage started with one.", "WEAPON", 200, 100));
        weapons.add(new Item("Hardened Staff", new Image(10, 10,  path + "staff2.png"), "For that extra kick.", "WEAPON", 300, 150));
        weapons.add(new Item("Wizard's Staff", new Image(10, 10,  path + "staff3.png"), "A true wizard's staff", "WEAPON", 400, 250));
        weapons.add(new Item("Mo's Staff", new Image(10, 10,  path + "staff4.png"), "Wow.", "WEAPON", 500, 250));

        weapons.add(new Item("Basic Bow", new Image(10, 10, path + "bow0.png"), "A basic bow", "WEAPON", 100, 50));

        weapons.add(new Item("Basic Sword", new Image(10, 10, path + "sword0.png"), "A basic sword.", "WEAPON", 100, 50));
        weapons.add(new Item("Beginners Sword", new Image(10, 10, path + "sword1.png"), "A beginners sword.", "WEAPON", 200, 100));
        weapons.add(new Item("Hardened Sword", new Image(10, 10, path + "sword2.png"), "This'll do some damage.", "WEAPON", 300, 150));
        weapons.add(new Item("Warrior's Sword", new Image(10, 10, path + "sword3.png"), "A true warrior never leaves home without it.", "WEAPON", 400, 200));
        weapons.add(new Item("Mo's Sword", new Image(10, 10, path + "sword4.png"), "Oooh.", "WEAPON", 500, 250));
        weapons.add(new Item("Matilda", new Image(10, 10, path + "swordS.png"), "A new prototype, it packs a punch.", "WEAPON", 600, 300));

        //SHIELDS
        shields.add(new Item("Basic Shield", new Image(10, 10, path + "shield0.png"), "A basic shield.", "SHIELD", 100, 50));
        shields.add(new Item("Beginners Shield", new Image(10, 10, path + "shield1.png"), "A classic for a newbie.", "SHIELD", 200, 100));
        shields.add(new Item("Advanced Shield", new Image(10, 10, path + "shield2.png"), "Toughen yourself up.", "SHIELD", 300, 150));
        shields.add(new Item("Mo's Shield", new Image(10, 10, path + "shield3.png"), "Nice.", "SHIELD", 400, 200));
        shields.add(new Item("The Wall", new Image(10, 10, path + "shield4.png"), "The best shield on the market.", "SHIELD", 500, 250));

        //POTIONS
        potions.add(new Item("Health Potion", new Image(10, 10, path + "potion_red.png"), "Heals you ..", "POTION", 100, 50));
        potions.add(new Item("Vitality Boost", new Image(10, 10, path + "potion_blue.png"), "Increases your vitality.", "POTION", 150, 75));
        potions.add(new Item("Strength Potion", new Image(10, 10, path + "potion_black.png"), "Increases your attack.", "POTION", 150, 75));
        potions.add(new Item("Toughness Potion", new Image(10, 10, path + "potion_brown.png"), "Increases your endurance.", "POTION", 150, 75));
        potions.add(new Item("Wisdom Potion", new Image(10, 10, path + "potion_green.png"), "Gives you an xp boost.", "POTION", 150, 75));
        potions.add(new Item("Brains Potion", new Image(10, 10, path + "potion_purple.png"), "Increases your intellect.", "POTION", 150, 75));
        potions.add(new Item("Swiftness Potion", new Image(10, 10, path + "potion_white.png"), "Increases your agility.", "POTION", 150, 75));
        potions.add(new Item("Magic Elixr", new Image(10, 10, path + "potion_yellow.png"), "Boost all stats.", "POTION", 300, 1580));
        potions.add(new Item("Mo's Special Blend", new Image(10, 10, path + "potion_S.png"), "Hmmm... It cant hurt to try it.", "POTION", 500, 250));
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

            errorMess.setText("");

            if (slot.hasItem()) {
                btnBuy.show();

                img.changeImage(slot.getItem().getItemIcon());
                addEntity(img);
                img.show();

                addEntity(coins);
                coins.show();

                n.setText(slot.getItem().getName());
                d.setText(slot.getItem().getDescription());
                price.setText("" + slot.getItem().getPrice());

            } else {
                img.hide();
                coins.hide();
                btnBuy.hide();
                n.setText("Theres Nothing for Sale in that Slot!");
                d.setText("");
                price.setText("");
            }

            for (int i = 1; i < 36; i++) {
                if (slot.getName().equals("" + i)) {
                    System.out.println("Slot " + i + " clicked");
                }
            }
        } else if (clickable instanceof Button) {
            Entity button = (Button) clickable;
            errorMess.setText("");

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
            } else if (button.getName().equals("BUY"))
            {
                if(MapMenu.getPlayer().getCoins() > clickedSlot.getItem().getPrice()) {
                    MapMenu.getPlayer().addToInventory(clickedSlot.getItem()); //add the item to the players inventory

                    //remove the item from being purchasable
                    for (int i = 0; i < lastList.size(); i++) {
                        if (lastList.get(i) == clickedSlot.getItem()) {
                            lastList.remove(i);
                        }
                    }
                    populateMenu(lastList);

                    //Output to screen that the item has been bought successfully
                    img.hide();
                    coins.hide();
                    btnBuy.hide();
                    n.setText("Bought!");
                    d.setText("");
                    price.setText("");

                    errorMess.setText("");

                    System.out.println("BUY clicked");
                }else{errorMess.setText("Not enough Money");
                    n.setText("");
                    d.setText("");
                    addEntity(errorMess);}
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
