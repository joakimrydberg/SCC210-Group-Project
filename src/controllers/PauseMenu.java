package controllers;

import abstract_classes.Entity;
import components.Button;
import components.Image;
import components.Item;
import components.Rect;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import tools.Constants;

import java.util.ArrayList;

/**
 * Created by Ross on 21/02/2016.
 */
public class PauseMenu extends Menu {
    public final static String NAME = "Pause Menu";
    private int btnX = 170, btnY = 150;
    private PlayerMenu pMenu = new PlayerMenu();
    private InventoryMenu iMenu = new InventoryMenu();
    private StatsMenu sMenu = new StatsMenu();

    /**
     *
     */
    public PauseMenu() {
        super(NAME);

        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "pause_menu_background.png"));

        addOption("PLAYER", "BROWN");
        addOption("INVENTORY", "BROWN");
        addOption("STATS", "BROWN");
        addOption("DUNGEON MAP", "BROWN");
        addOption("RESUME", "GREEN");
        addOption("SAVE GAME", 550, "BLUE");
        addOption("QUIT DUNGEON", 600, "RED");

        addEntity(new Rect(null, 300, centerY, 2, 600, new Color(117, 62, 29), 250)); //seperation border

        pMenu.load();
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
        if (clickable instanceof Button) {
            Entity button = (Button) clickable;
            if (button.getName().equals("PLAYER"))
            {
                unloadMenus();
                pMenu.load();
                System.out.println("PLAYER clicked");
            }
            else if (button.getName().equals("INVENTORY"))
            {
                unloadMenus();

                ArrayList<Item> inventory = new ArrayList<Item>();
                inventory.add(new Item("Basic Sword", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "basic_sword.png"), "A basic sword"));
                inventory.add(new Item("Basic Staff", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "basic_staff.png"), "A basic staff"));
                inventory.add(new Item("Basic Bow", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "basic_bow.png"), "A basic bow"));

                iMenu.load();
                iMenu.populateMenu(inventory);
                System.out.println("INVENTORY clicked");
            }
            else if (button.getName().equals("STATS"))
            {
                unloadMenus();
                sMenu.load();
                System.out.println("STATS clicked");
            }
            else if (button.getName().equals("DUNGEON MAP"))
            {
                unloadMenus();
                System.out.println("DUNGEON MAP clicked");
            }
            else if (button.getName().equals("RESUME"))
            {
                this.unload();
                unloadMenus();
                new MainMenu().load();
                System.out.println("RESUME clicked");
            }
            else if (button.getName().equals("QUIT DUNGEON"))
            {
                this.unload();
                unloadMenus();
                new MainMenu().load();
                System.out.println("QUIT DUNGEON clicked");
            }
        }
    }

    private void unloadMenus(){
        pMenu.unload();
        sMenu.unload();
        iMenu.unload();
    }

}
