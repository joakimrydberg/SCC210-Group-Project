package components;

import game.SpriteSheetLoad;
import interfaces.KeyListener;
import interfaces.MovementListener;
import interfaces.MovingEntity;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by millsr3 on 16/02/2016.
 */
public class Player extends Animation implements KeyListener, MovingEntity {
    private Vector2i speed = new Vector2i(0, 0);
    private float multiplier = 1;
    ArrayList<MovementListener> listeners = new ArrayList<>();
    private String classType;
    public int Agility = 0;
    public int Intellect = 0;
    public int attackPower = 0;
    public int Endurance = 0;
    public int Vitality = 0;
    public int Health = 100;
    SpriteSheetLoad ourSpriteSheet = new SpriteSheetLoad(64, 128);
    static BufferedImage theSpriteSheet;
    public static BufferedImage[] characterStill;
    // public static Animation currAnimation;
    private final static int MOVEBY = 5,
            SPEEDLIMIT = 5;
    private boolean upPressed = false,
            downPressed = false,
            leftPressed = false,
            rightPressed = false;

    public Player() {
        super(200, 200, 64, 128, new BufferedImage[0], 1);


    }

    public void setClass(String c){
        classType = c;

        if (c.equals("mage")){
            System.out.println("mage selected");
            theSpriteSheet = SpriteSheetLoad.loadSprite("MageMaleSheet");
            characterStill = new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, theSpriteSheet)};
            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            // currAnimation = warriorWalk;
            BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(1, 0, theSpriteSheet), SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(2, 0, theSpriteSheet)};

            this.setFrames(mageA);
            this.stop();

            this.start();
        }
        if (c.equals("warrior")){
            System.out.println("warrior selected");
            theSpriteSheet = SpriteSheetLoad.loadSprite("WarriorMaleSheet");
            characterStill = new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, theSpriteSheet)};
            BufferedImage[] warrior = {SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(1, 0, theSpriteSheet), SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(2, 0, theSpriteSheet)};

            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            this.setFrames(warrior);
            this.stop();
            this.start();
        }
        if (c.equals("ranger")){
            System.out.println("ranger selected");
            theSpriteSheet  = SpriteSheetLoad.loadSprite("RangerMaleSheet");
            characterStill = new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, theSpriteSheet)};
            BufferedImage[] ranger = {SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(1, 0, theSpriteSheet), SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(2, 0, theSpriteSheet)};

            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            this.setFrames(ranger);
            this.stop();
            this.start();
        }

    }

    public void setStats(int a, int b, int c , int d, int e){

        attackPower = a;
        Intellect = b;
        Agility = c;
        Endurance = d;
        Vitality = e;
    }


    //Move character from sheet based on direction, 0-down, 1-right, 2-left, 3-up
/*    public static BufferedImage[] charMove(BufferedImage character, int dir)
    {
    }*/

    public BufferedImage[] charAttack(BufferedImage character, int dir)
    {
        BufferedImage[] characterAttack = {SpriteSheetLoad.getSprite(4, dir, character), SpriteSheetLoad.getSprite(5, dir, character)};
        return characterAttack;
    }

    public BufferedImage[] charHurt(BufferedImage character, int dir)
    {
        BufferedImage[] characterHurt = {SpriteSheetLoad.getSprite(3, dir, character)};
        this.Health = this.Health-20;
        return characterHurt;
    }

    @Override
    public void keyPressed(org.jsfml.window.event.KeyEvent event) {
        // System.out.println("Pressed");

        Keyboard.Key key = event.key;
        switch (key){
            case RIGHT:
                if (!rightPressed) {
                    rightPressed = true;
                    setSpeed(new Vector2i(speed.x + MOVEBY, speed.y));
                }
                break;
            case UP:
                if (!upPressed) {
                    upPressed = true;
                    setSpeed(new Vector2i(speed.x, speed.y - MOVEBY));
                }
                break;
            case LEFT:
                if (!leftPressed) {
                    leftPressed = true;
                    setSpeed(new Vector2i(speed.x - MOVEBY, speed.y));
                }
                break;
            case DOWN:
                if (!downPressed) {
                    downPressed = true;
                    setSpeed(new Vector2i(speed.x, speed.y + MOVEBY));
                }
                break;
        }

    }

    @Override
    public void keyReleased(org.jsfml.window.event.KeyEvent event) {
        //this would look so pretty as a switch state
        Keyboard.Key key = event.key;
        switch (key){
            case RIGHT:
                rightPressed = false;
                BufferedImage[] right = {SpriteSheetLoad.getSprite(0, 2, theSpriteSheet), SpriteSheetLoad.getSprite(1, 2, theSpriteSheet), SpriteSheetLoad.getSprite(0, 2, theSpriteSheet), SpriteSheetLoad.getSprite(1, 2, theSpriteSheet)};
                this.setFrames(right);

                setSpeed(new Vector2i(speed.x - MOVEBY, speed.y));

                break;
            case UP:
                upPressed = false;
                setSpeed(new Vector2i(speed.x, speed.y + MOVEBY));
                BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 3, theSpriteSheet), SpriteSheetLoad.getSprite(1, 3, theSpriteSheet), SpriteSheetLoad.getSprite(0, 3, theSpriteSheet), SpriteSheetLoad.getSprite(1, 3, theSpriteSheet)};
                this.setFrames(mageA);

                break;
            case LEFT:
                leftPressed = false;
                setSpeed(new Vector2i(speed.x + MOVEBY, speed.y));
                BufferedImage[] left = {SpriteSheetLoad.getSprite(0, 1, theSpriteSheet), SpriteSheetLoad.getSprite(1, 1, theSpriteSheet), SpriteSheetLoad.getSprite(0, 1, theSpriteSheet), SpriteSheetLoad.getSprite(1,1, theSpriteSheet)};
                this.setFrames(left);

                break;
            case DOWN:
                downPressed = false;
                setSpeed(new Vector2i(speed.x, speed.y - MOVEBY));
                BufferedImage[] down = {SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(1, 0, theSpriteSheet), SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(1, 0, theSpriteSheet)};
                this.setFrames(down);

                break;
        }


        this.stop();

        this.start();


        //this.speed = new Vector2i(0,0);

    }

    /**
     * Implementation of move function. Will check for collisions if child implements InteractingEntity and then move if possible
     */
    @Override
    public void move() { //this method must take no arguments
        { //moving
            final int newX = (int)(getCenterX() + speed.x * multiplier), newY = (int)(getCenterY() + speed.y * multiplier);

            if (newX == getCenterX() && newY == getCenterY())
                return;

            //checking all the MovementListeners are 'okay' with the proposed move
            boolean move = true;
            for (MovementListener listener : listeners) {
                move = listener.isMoveAcceptable(newX, newY + getHeight() / 6, getWidth() / 2, getHeight() /4) // It's so the top half of the player can overlap on the walls etc TODO adjust these values if they aren't great
                        && move;                                             // a little weird but for reasons.
            }

            if (move) {
                //updating X and Y coordinates
                setCenterX(newX);
                setCenterY(newY);
            } else {
                //setCenterX(getCenterX() - (speed.x / MOVEBY) );
                // setCenterY(getCenterY() - (speed.y / MOVEBY) );
            }

            draw();  //drawing to the screen
        }

/*        { //animating TODO needs fixing
            currAnimation.stop();
            BufferedImage[] characterMove = {SpriteSheetLoad.getSprite(0, dir, character), SpriteSheetLoad.getSprite(1, dir, character), SpriteSheetLoad.getSprite(0, dir, character), SpriteSheetLoad.getSprite(2, dir, character)};
            warriorWalk = new Animation(200, 200, 64, 128, characterMove, 1);
            currAnimation = warriorWalk;
            if (dir == 0)
                currAnimation.updateMove(0, 1);
            else if (dir == 1)
                currAnimation.updateMove(1, 0);
            else if (dir == 2)
                currAnimation.updateMove(-1, 0);
            else if (dir == 3)
                currAnimation.updateMove(0, -1);
            currAnimation.start();
        }*/

        for (MovementListener listener : listeners) {  //must be at end of method
            listener.onMove(this);
        }
    }

    /**
     * Set speed
     *
     * @param speed - Vector2i representing the speed of the entity in the x and y planes
     */
    @Override
    public synchronized void setSpeed(Vector2i speed) {
        // attempt to limit the diagonal speed will return to if we have time
//        if (Math.sqrt(speed.x * speed.x + speed.y + speed.y) > 5) {
//            speed = new Vector2i(speed.x * ((speed.x * speed.x) / (speed.x * speed.x + speed.y + speed.y)),
//                    speed.y * ((speed.y * speed.y) / (speed.x * speed.x + speed.y + speed.y)));
//        }

        if (Math.abs(speed.x) > 5)
            speed = new Vector2i(0, speed.y);    //shouldn't need but it's in now anyway

        if (Math.abs(speed.y) > 5)
            speed = new Vector2i(speed.x, 0);    //shouldn't need but it's in now anyway

        this.speed = speed;

        System.out.println("Speed.. X: " + speed.x + ",  Y: " + speed.y);

    }

    /**
     * Get the entities speed
     *
     * @return Vector2i value representing the speed in two dimensional space
     */
    @Override
    public Vector2i getSpeed() {
        return speed;
    }

    /**
     * Sets the speed multiplier for the Entity.
     * <p>
     * This is used to scale the speed in both directions. Should probably only use on a class by class basis
     *
     * @param multiplier - float value
     */
    @Override
    public void setSpeedMultiplier(float multiplier) {
        multiplier = multiplier;
    }

    @Override
    public void addMovementListener(MovementListener listener) {
        listeners.add(listener);
    }

    public void updateMove(int xDir, int yDir)
    {
        setCenterX(getCenterX()+xDir);
        setCenterY(getCenterY()+yDir);
    }
}