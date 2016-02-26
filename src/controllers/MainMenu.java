/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import abstract_classes.Drawer;
import abstract_classes.Entity;
import components.Button;
import components.Image;
import interfaces.Clickable;
import interfaces.KeyListener;
import levelcreator.LevelCreator;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;
import tools.Constants;
import tools.MusicPlayer;

/**
 *
 * @author ryan
 */
public class MainMenu extends Menu implements KeyListener {
  //  public int loaded = 0;
    public final static String NAME = "Main Menu";
    private boolean cheating = false;
    private String cheat = "";


    public MainMenu() {
        super(NAME);

        this.addEntity(this);

        final Vector2i windowSize = Entity.getWindow().getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "main_menu_background.png"));
        //addEntity(new Image(centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "main_menu_background_wBoard.png"));
        //addEntity(new Image(centerX, 450, 360, 600, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu.png"));

        Button btnNewGame = new Button(centerX, 250, 250, 60, "BROWN", 200, "NEW GAME", 20);
        Button btnLoadGame = new Button( centerX, 330, 250, 60, "BROWN", 200, "LOAD GAME", 20);
        Button btnInstructions = new Button(centerX, 410, 250, 60, "BROWN", 200, "HOW TO PLAY", 20);
        Button btnOptions = new Button(centerX, 490, 250, 60, "BROWN", 200, "OPTIONS", 20);
        Button btnCredits = new Button( centerX, 570, 250, 60, "BROWN", 200, "CREDITS", 20);
        Button btnQuit = new Button( centerX, 650, 250, 60, "RED", 200, "QUIT", 20);

        //temo button for testing pause menu - removed in finished game
//        Button tempPauseButton = new Button(170, 500, 120, 50, "BROWN", 200 , "PAUSE MENU", 15 );
//        tempPauseButton.addClickListener(this);
//        addEntity(tempPauseButton);

        btnNewGame.addClickListener(this);
        addEntity(btnNewGame);
        btnLoadGame.addClickListener(this);
        //addEntity(btnLoadGame);
        btnInstructions.addClickListener(this);
        addEntity(btnInstructions);
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
            else if (button.getName().equals("HOW TO PLAY")){
                loadDrawer(Instructions.class);
                System.out.println("HOW TO PLAY clicked");
            }
            else if (button.getName().equals("OPTIONS")){
                System.out.println("OPTIONS clicked");
            }
            else if (button.getName().equals("CREDITS")){
                this.unload();
                new CreditMenu().load();
                System.out.println("CREDITS clicked");
            }
            else if (button.getName().equals("QUIT")){
                Entity.getWindow().close();
                System.out.println("QUIT clicked");
            }
            else if (button.getName().equals("PAUSE MENU")) { //temp button for loading and testing pause menu
                this.unload();
                loadDrawer(PauseMenu.class);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (Keyboard.isKeyPressed(Keyboard.Key.LCONTROL)) {
            if (event.key != Keyboard.Key.LCONTROL) {
                cheat += event.key;

                if (cheat.toLowerCase().equals("levelcreator")
                        || cheat.toLowerCase().equals("level creator")) {

                    new Thread(LevelCreator::new).start(); //who even knew java had this syntax
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (!Keyboard.isKeyPressed(Keyboard.Key.LCONTROL)) {
            cheat = "";
        }
    }
}
