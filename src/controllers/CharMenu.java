/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import abstract_classes.Entity;
import components.*;
import components.mobs.Player;
import game.SpriteSheetLoad;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2i;
import tools.Constants;
import tools.DebugPrinter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author ryan
 */
public class CharMenu extends Menu {
    private final static String SEP = Constants.SEP;
    public final static String NAME = "Character creation menu";
    private Image warr;
    private Image range;
    private Image mage;
    private Boolean playerChosen = false;

    //SpriteSheet load
    SpriteSheetLoad mageSpriteSheet = new SpriteSheetLoad(64, 128);
    private BufferedImage newWarriorSheet = SpriteSheetLoad.loadSprite("WarriorMaleSheet");
    private BufferedImage newRangerSheet = SpriteSheetLoad.loadSprite("RangerMaleSheet");
    private BufferedImage newMageSheet = SpriteSheetLoad.loadSprite("MageMaleSheet");
    private BufferedImage[] warrior = {SpriteSheetLoad.getSprite(0, 0, newWarriorSheet), SpriteSheetLoad.getSprite(1, 0, newWarriorSheet), SpriteSheetLoad.getSprite(0, 0, newWarriorSheet), SpriteSheetLoad.getSprite(2, 0, newWarriorSheet)};
    private BufferedImage[] ranger = {SpriteSheetLoad.getSprite(0, 0, newRangerSheet), SpriteSheetLoad.getSprite(1, 0, newRangerSheet), SpriteSheetLoad.getSprite(0, 0, newRangerSheet), SpriteSheetLoad.getSprite(2, 0, newRangerSheet)};
    private BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, newMageSheet), SpriteSheetLoad.getSprite(1, 0, newMageSheet), SpriteSheetLoad.getSprite(0, 0, newMageSheet), SpriteSheetLoad.getSprite(2, 0, newMageSheet)};
    private Animation warriorWalk = new Animation(200, 200, 64, 128, warrior, 3);
    private Animation rangerWalk = new Animation(200, 200, 64, 128, ranger, 3);
    private Animation mageWalk = new Animation(200, 200, 64, 128, mageA, 3);
    private Animation currAnimation = warriorWalk;
    private Player tempPlayer = new Player();
    BufferedImage[] currentAnimation = tempPlayer.charAttack(newMageSheet, 0);
    private Animation tempWalk = new Animation(100, 200, 64, 128, currentAnimation, 5);

    private String[] hairCols = new String[3];
    private Message[] messages = new Message[6];
    private int i; //TODO what even is this?
    private Message hairCol = null;
    private Player p;
    private String className;

    public CharMenu() {
        super(NAME);

        final Vector2i windowSize = getWindow().getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();

        //adding the map image to the screen
        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file
            String a = (dir.toString() + SEP + "assets" + SEP + "art" + SEP + "menu_background.png");

            addEntity(new Image(centerX, centerY, windowSize.x, windowSize.y, a));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //back button
        Button backButton = new Button(70, 40, 100, 50, "BROWN", 200 , "BACK", 15 );
        backButton.addClickListener(this);
        addEntity(backButton);

        hairCol = new Message(700, 242, 0, "Red", Color.BLACK, 11);

        //addEntity(new Rect(null, centerX, centerY, windowSize.x, windowSize.y, Color.BLACK, 128));
        addEntity(new Rect(null, 725, 375, 350, 700, Color.WHITE, 128));
        addEntity(new Rect(null, 725, 500, 350, 220, Color.WHITE, 128));
        Button createButton = new Button(685, 650, 100, 50, "BROWN", 100, "CREATE", 11);

        //URL url = getClass().getProtectionDomain().getCodeSource().getLocation();

        try{

            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file

            final String a = (dir.toString() + SEP +"assets" + SEP +"art" + SEP + "magic.png");
            final String d = (dir.toString() + SEP + "assets" + SEP + "art" + SEP + "ranged.png");
            final String e = (dir.toString()  + SEP + "assets" + SEP + "art" + SEP + "strength.png");


            ClickableImage clickableImage ;
            clickableImage = new ClickableImage(600, 100, a);
            clickableImage.addClickListener(this);
            addEntity(clickableImage);
            clickableImage = new ClickableImage(725, 100, e);
            clickableImage.addClickListener(this);
            addEntity(clickableImage);
            clickableImage = new ClickableImage(850, 100, d);
            clickableImage.addClickListener(this);
            addEntity(clickableImage);



            /*final String f = (dir.toString()  + SEP + "assets" + SEP + "art" + SEP + "mage-front.png");
            final String g = (dir.toString()  + SEP + "assets" + SEP + "art" + SEP + "warr-front.png");
            final String h = (dir.toString()  + SEP + "assets" + SEP + "art" + SEP + "range-front.png");
            mage = new Image(350, 400, 100, 200, f);
            range = new Image(350, 400, 100, 200, h);
            warr = new Image(350, 400, 100, 200, g);
            addEntity(mage);
            addEntity(range);
            addEntity(warr);

            warr.hide();
            range.hide();*/
            p = new Player();
            p.move();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        addEntity(createButton);
        createButton.addClickListener(this);

        // DebugPrinter.debugPrint(this, getClass().getClassLoader().getResource("\\SCC210-Group-Project\\assets\\art\\magic.png").toString());
        messages[0] = (new Message(575, 430, 0, "Stats", Color.BLACK, 20));
        messages[1] = (new Message(675, 450, 0, "Attack power : 5", Color.BLACK, 12));
        messages[2] = (new Message(675, 475, 0, "Intellect : 0", Color.BLACK, 12));
        messages[3] = (new Message(675, 500, 0, "Agility : 0", Color.BLACK, 12));
        messages[4] = (new Message(675, 525, 0, "Endurance : 5", Color.BLACK, 12));
        messages[5] = (new Message(675, 550, 0, "Vitality : 5", Color.BLACK, 12));

        addEntity(messages[0]);
        addEntity(messages[1]);
        addEntity(messages[2]);
        addEntity(messages[3]);
        addEntity(messages[4]);
        addEntity(messages[5]);


        hairCols[0] = "Red";
        hairCols[1] = "Blue";
        hairCols[2] = "Green";

        Button tempButton;

        tempButton = new Button(775, 250, 25, 25, "BROWN", 100, ">>", 9 );
        tempButton.addClickListener(this);
        addEntity(tempButton);

        tempButton = new Button(650, 250, 25, 25, "BROWN", 100, "<<", 9 );
        tempButton.addClickListener(this);
        addEntity(tempButton);

        addEntity(hairCol);
        addEntity(mageWalk);
        addEntity(rangerWalk);
        addEntity(warriorWalk);
        addEntity(tempWalk);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof  Button ) {
            Entity button = (Button)clickable;

            if (button.getName().equals("CREATE") && className !=null) {
                //p.setClass(className); //******RYAN, IVE JUST COMMENTED THIS OUT WHILE WORKING ON MAP MENU, IT RESULTED IN A NULL POINTER EXCEPTION ANS STOPPED MAP MENU LOADING*******
                this.unload();

              //  loadDrawer(MapMenu.class);
                new MapMenu(p).load();
            } else if (button.getName().equals(">>")) {
              //  System.out.format("%d", this.i);
                if (this.i < 2) {
                    this.i++;
                    this.moveRight();
                }
            } else if (button.getName().equals("<<")) {
               // System.out.format("%d", this.i);
                if (this.i > 0) {
                    this.i--;
                    this.moveLeft();
                }
            } else if (button.getName().equals("BACK")) {
                this.unload();

                loadDrawer(MainMenu.class);

                System.out.println("Back clicked");
            }
        } else if (clickable instanceof  ClickableImage ) {

            Entity button = (ClickableImage) clickable;

            if (button.getName().contains("magic")){
                DebugPrinter.debugPrint(this, "magic selected");
                this.setStats(0, 10, 0, 3, 2);
                p.setStats(0, 10, 0, 3, 2);
                p.setClass("mage");
                p.addToInventory(new Item("Basic Staff", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "staff0.png"), "A basic staff", "WEAPON", 100, 50));
                //range.hide();
                //warr.hide();
                //mage.show();

                className = "mage";
                currAnimation.stop();
                currAnimation = mageWalk;

                currAnimation.start();
            }
            else if (button.getName().contains("ranged")){
                DebugPrinter.debugPrint(this, "ranged selected");
                this.setStats(0, 0, 5, 5, 5);
                p.setStats(0, 0, 5, 5, 5);
               // range.show();
                //warr.hide();
               // mage.hide();
                p.setClass("ranger");
                p.addToInventory(new Item("Basic Bow", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "bow0.png"), "A basic bow", "WEAPON", 100, 50));
               // p.printInventory();//debug
                className = "ranged";
                currAnimation.stop();
                currAnimation = rangerWalk;
                currAnimation.start();

            }
            else if (button.getName().contains("strength")){
                DebugPrinter.debugPrint(this, "Strength selected");
                this.setStats(5, 0, 0, 5, 5);
                p.setStats(5, 0, 0, 5, 5);
                //range.hide();
                //warr.show();
                //mage.hide();
                p.setClass("warrior");
                p.addToInventory(new Item("Basic Sword", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "sword0.png"), "A basic sword", "WEAPON", 100, 50));
                className = "warr";
                currAnimation.stop();
                currAnimation = warriorWalk;
                //tempWalk.start();
                //tempWalk.increaseDelay(30);
                currAnimation.start();
            }

        }
    }


    public void moveRight(){
        (((Text)hairCol.getTransformable(0))).setString(hairCols[i]);
    }

    public void moveLeft(){
        (((Text)hairCol.getTransformable(0))).setString(hairCols[i]);
    }

    public void setStats(int a, int b, int c , int d, int e){
        ((Text)messages[1].getTransformable(0)).setString("Attack power : " + a);
        ((Text)messages[2].getTransformable(0)).setString("Intellect : " + b);
        ((Text)messages[3].getTransformable(0)).setString("Agility : " + c);
        ((Text)messages[4].getTransformable(0)).setString("Endurance : " + d);
        ((Text)messages[5].getTransformable(0)).setString("Vitality : " + e);
    }
}
