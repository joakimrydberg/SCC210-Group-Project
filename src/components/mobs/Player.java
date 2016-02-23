package components.mobs;

import components.Image;
import components.Item;
import game.Room;
import game.SpriteSheetLoad;
import interfaces.CollidingEntity;
import interfaces.KeyListener;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;
import tools.Constants;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by millsr3 on 16/02/2016.
 */
public class Player extends Mob implements KeyListener, CollidingEntity {
    private boolean colliding = false;
    public final static int ATTACK_RIGHT = 1,
            ATTACK_LEFT = 2,
            ATTACK_UP = 3,
            ATTACK_DOWN = 4;
    private float multiplier = 1;
    public static String classType;
    public int tempDir;
    // public static Animation currAnimation;
    private final static int MOVEBY = 5,
            SPEEDLIMIT = 5;
    protected boolean upPressed = false,
            downPressed = false,
            leftPressed = false,
            rightPressed = false;

    private Room room;
    protected int dir = 0;
    public boolean attacking = false;
    ArrayList<Item> inventory = new ArrayList<Item>();
    ArrayList<Item> equippedItems = new ArrayList<Item>();
    public int level = 1;

    public Player() {
        super(400, 400, 64, 128);
        //inventory.add(new Item("Basic Sword", new Image(10, 10, "assets" + Constants.SEP + "art" + Constants.SEP + "items" + Constants.SEP + "basic_sword.png"), "A basic sword"));
    }

    public void addToInventory(Item item){
        inventory.add(item);
    }

    public void removeFromInventory(Item item){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == item){
                inventory.remove(i);
            }
        }
    }

    public Item getFromInventory(Item item){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == item){
                inventory.remove(i);
                return item;
            }
        }
        return null;
    }

    public void printInventory(){
        System.out.println("--Inventory-------");
        for(int i = 0; i < inventory.size(); i++){
            System.out.println(inventory.get(i).getName());
        }
        System.out.println("------------------");
    }

    public void setClass(String c) {
        classType = c;

        if (c.equals("mage")) {
            System.out.println("mage selected");
            setSpriteSheet(SpriteSheetLoad.loadSprite("MageMaleSheet"));
            setCharacterStill(tempDir);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            // currAnimation = warriorWalk;
            BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

            super.stop(); //@see Mob , must be before we set the frames

            this.setFrames(mageA);

            this.start();
        }
        if (c.equals("warrior")) {
            Warrior p = new Warrior();
    }
        if (c.equals("ranger")) {
            System.out.println("ranger selected");
            setSpriteSheet(SpriteSheetLoad.loadSprite("RangerMaleSheet"));
            setCharacterStill(tempDir);
            BufferedImage[] ranger = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};


            super.stop(); //@see Mob , must be before we set the frames

            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            this.setFrames(ranger);

            this.start();
        }

    }

    @Override
    public void keyPressed(org.jsfml.window.event.KeyEvent event) {
        switch (event.key) {
            case RIGHT:
                if (!rightPressed) {
                    rightPressed = true;
                    setSpeed(new Vector2f(getVelocity().x + MOVEBY, getVelocity().y));
                    tempDir = 2;
                    setAnimation(ANIMATE_RIGHT);
                }
                break;
            case UP:
                if (!upPressed) {
                    upPressed = true;
                    setSpeed(new Vector2f(getVelocity().x, getVelocity().y - MOVEBY));
                    tempDir = 3;
                    setAnimation(ANIMATE_UP);
                }
                break;
            case LEFT:
                if (!leftPressed) {
                    leftPressed = true;
                    setSpeed(new Vector2f(getVelocity().x - MOVEBY, getVelocity().y));
                    tempDir = 1;
                    setAnimation(ANIMATE_LEFT);
                }
                break;
            case DOWN:
                if (!downPressed) {
                    downPressed = true;
                    setSpeed(new Vector2f(getVelocity().x, getVelocity().y + MOVEBY));
                    tempDir = 0;
                    setAnimation(ANIMATE_DOWN);
                }
                break;
        }

    }

    @Override
    public void keyReleased(org.jsfml.window.event.KeyEvent event) {
        switch (event.key) {
            case RIGHT:
                rightPressed = false;
                setSpeed(new Vector2f(0, getVelocity().y));

                if (getVelocity().x == 0 && getVelocity().y == 0) {
                    setCharacterStill(tempDir);
                    super.stopCharacter(); //@see Mob
                }

                break;
            case UP:
                upPressed = false;
                setSpeed(new Vector2f(getVelocity().x, 0));

                if (getVelocity().x == 0 && getVelocity().y == 0) {
                    setCharacterStill(tempDir);
                    super.stopCharacter(); //@see Mob
                }

                break;
            case LEFT:
                leftPressed = false;
                setSpeed(new Vector2f(0, getVelocity().y));

                if (getVelocity().x == 0 && getVelocity().y == 0) {
                    setCharacterStill(tempDir);
                    super.stopCharacter(); //@see Mob
                }
                break;
            case DOWN:
                downPressed = false;
                setSpeed(new Vector2f(getVelocity().x, 0));

                if (getVelocity().x == 0 && getVelocity().y == 0) {
                    setCharacterStill(tempDir);
                    super.stopCharacter(); //@see Mob
                }

                break;
        }

        this.stop();
        this.start();
        //this.getVelocity() = new Vector2i(0,0);

    }

    @Override
    public void onMoveAccepted(int newX, int newY) {
        setCenterX(newX);
        setCenterY(newY);
    }

    @Override
    public void onMoveRejected(int newX, int newY) {

        return;    //do nothing but don't remove (will be used for the bad guys)
    }

    @Override
    public void collide() {

    }

    @Override
    public void setSpeed(Vector2f speed) {
        super.setSpeed(speed, 2);  //so player is a bit faster
    }

    @Override
    public boolean isCollidable(int x, int y) {

        return (( Math.abs((getCenterX() + getWidth()/2) - (x  )) < 35)
                && (Math.abs((getCenterY() + getHeight()/2) - (y  )) < 50));
        //May need some tweaks to numbers

    }

    @Override
    public boolean checkWithin(int x, int y) {

        if (isCollidable(x, y)) {
            colliding = true;
            return true;
        }
        else {
            colliding = false;
        }

        return false;
    }


    @Override
    public boolean checkWithin(Event e) {
        return false;
    }

    public void damaged(){

        if(System.currentTimeMillis() - timeAtLastDamaged > 500){
            timeAtLastDamaged = System.currentTimeMillis();
            BufferedImage[] a = charHurt(getTheSpriteSheet(), tempDir, 15);
            setFrames(a);
        }

    }

    public void die(){



    }

}


