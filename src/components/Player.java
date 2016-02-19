package components;

import game.SpriteSheetLoad;
import interfaces.InteractingEntity;
import interfaces.KeyListener;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;
import tools.DebugPrinter;

import java.awt.image.BufferedImage;

/**
 * Created by millsr3 on 16/02/2016.
 */
public class Player extends Animation implements KeyListener, interfaces.MovingEntity {
    private Vector2i speed = new Vector2i(0, 0);
    private float multiplier = 0;
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
    public void keyPressed(Event event) {
        System.out.println("Pressed");
    }

    @Override
    public void keyReleased(Event event) {

    }




    /**
     * Implementation of move function. Will check for collisions if child implements InteractingEntity and then move if possible
     */
    @Override
    public void move() { //this method must take no arguments
        { //moving
            final int newX = (int)(getCenterX() + speed.x * multiplier), newY = (int)(getCenterY() + speed.y * multiplier);

            if (this instanceof InteractingEntity) {
                final InteractingEntity collidingThis = (InteractingEntity) this;

                if (collidingThis.checkWithin(getCenterX(), getCenterY())) {
                    DebugPrinter.debugPrint(this, "Already checkWithin!!");
                    return;
                }

                if (collidingThis.checkWithin(newX, newY)) {
                    DebugPrinter.debugPrint(this, "Already checkWithin!!");
                    return;
                }
            }

            final Vector2i windowSize = getWindow().getSize();

            if (newX < 0 || newX > windowSize.x || newY < 0 || newY > windowSize.y) {
                // DebugPrinter.debugPrint(this, "Going off screen so stop");
                return;
            }

            //updating X and Y coordinates
            setCenterX(getCenterX() + speed.x);
            setCenterY(getCenterY() + speed.y);

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
    }

    /**
     * Set speed
     *
     * @param speed - Vector2i representing the speed of the entity in the x and y planes
     */
    @Override
    public void setSpeed(Vector2i speed) {
        this.speed = speed;
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
        this.multiplier = multiplier;
    }

    public void updateMove(int xDir, int yDir)
    {
        setCenterX(getCenterX()+xDir);
        setCenterY(getCenterY()+yDir);
    }
}
