/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import abstract_classes.Entity;
import components.Button;
import components.Image;
import controllers.CharMenu;
import interfaces.Clickable;
import org.jsfml.system.Vector2i;
import tools.Constants;
import tools.MusicPlayer;

/**
 *
 * @author ryan
 */
public class MainMenu extends Menu {
    public int loaded = 0;
    public final static String NAME = "Main Menu";

    public MainMenu() {
        super(NAME);

        final Vector2i windowSize = Entity.getWindow().getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "main_menu_background.png"));
        //addEntity(new Image(centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "main_menu_background_wBoard.png"));
        //addEntity(new Image(centerX, 450, 360, 600, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu.png"));

        Button btnNewGame = new Button(centerX, 250, 250, 60, "BROWN", 200, "NEW GAME", 20);
        Button btnLoadGame = new Button( centerX, 330, 250, 60, "BROWN", 200, "LOAD GAME", 20);
        Button btnOptions = new Button(centerX, 450, 250, 60, "BROWN", 200, "OPTIONS", 20);
        Button btnCredits = new Button( centerX, 530, 250, 60, "BROWN", 200, "CREDITS", 20);
        Button btnQuit = new Button( centerX, 650, 250, 60, "RED", 200, "QUIT", 20);

        btnNewGame.addClickListener(this);
        addEntity(btnNewGame);
        btnLoadGame.addClickListener(this);
        addEntity(btnLoadGame);
        btnOptions.addClickListener(this);
        addEntity(btnOptions);
        btnCredits.addClickListener(this);
        addEntity(btnCredits);
        btnQuit.addClickListener(this);
        addEntity(btnQuit);

        MusicPlayer.play("main_menu_loop.wav", true);

    }


    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof  Button ) {
            Entity button = (Button) clickable;

            if (button.getName().equals("NEW GAME")) {
                this.unload();

                super.loadDrawer(CharMenu.class);
                //
                System.out.println("NEW GAME clicked");
            }
            else if (button.getName().equals("LOAD GAME")){
                System.out.println("LOAD GAME clicked");
            }
            else if (button.getName().equals("OPTIONS")){
                System.out.println("OPTIONS clicked");
            }
            else if (button.getName().equals("CREDITS")){
                this.unload();;
                new CreditMenu().load();
                System.out.println("CREDITS clicked");
            }
            else if (button.getName().equals("QUIT")){
                Entity.getWindow().close();
                System.out.println("QUIT clicked");
            }
        }
    }

}
