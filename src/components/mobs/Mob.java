package components.mobs;

import components.Animation;
import game.SpriteSheetLoad;
import interfaces.MovementListener;
import interfaces.MovingEntity;
import org.jsfml.system.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Mob extends Animation implements MovingEntity {
    public final static int SPEED_CAP = 5;
    public final static int ANIMATE_RIGHT = 2,
            ANIMATE_LEFT = 1,
            ANIMATE_UP = 3,
            ANIMATE_DOWN = 0;
    private Vector2f speed = new Vector2f(0, 0);
    private float multiplier = 1;
    ArrayList<MovementListener> listeners = new ArrayList<>();
    private String classType;
    public int Agility = 0;
    public int Intellect = 0;
    public int attackPower = 0;
    public int Endurance = 0;
    public int Vitality = 0;
    public int Health = 100;
    protected int tempDir = 0;
    public boolean dead = false;
    // SpriteSheetLoad ourSpriteSheet = new SpriteSheetLoad(64, 128);
    protected BufferedImage theSpriteSheet;
    public BufferedImage[] characterStill;
    protected int exp = 0;
    // public static Animation currAnimation;


    public Mob(int x, int y, int w, int h) {
        super(x, y, w, h, new BufferedImage[0], 1);
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

    public BufferedImage[] charHurt(BufferedImage character, int dir, int dmg)
    {
        BufferedImage[] characterHurt = {SpriteSheetLoad.getSprite(3, dir, character)};
        this.Health = this.Health-dmg;
        return characterHurt;
    }


    /**
     * Implementation of move function. Will check for collisions if child implements InteractingEntity and then move if possible
     */
    @Override
    public void move() { //this method must take no arguments
        { //moving
            final int newX = (int)(getCenterX() + speed.x * multiplier), newY = (int)(getCenterY() + speed.y * multiplier);


            if (newX != getCenterX() || newY != getCenterY()) {

                //checking all the MovementListeners are 'okay' with the proposed move
                boolean move = true;
                for (MovementListener listener : listeners) {
                    move = listener.isMoveAcceptable(newX, newY + getHeight() / 6, getWidth() / 2, getHeight() / 4) // It's so the top half of the player can overlap on the walls etc TODO adjust these values if they aren't great
                            && move;                                             // a little weird but for reasons.
                }

                if (move)
                    this.onMoveAccepted(newX, newY);
                else
                    this.onMoveRejected(newX, newY);

                draw();  //drawing to the screen

                if (move) {
                    for (MovementListener listener : listeners) {  //must be at end of method
                        listener.onMove(this);
                    }
                }
            }
        }

        if ( !(this instanceof EnemyWarrior && ((EnemyWarrior)this).attacking)){

            if (this.speed.x > 0 && this.speed.x >= this.speed.y) {
                setAnimation(ANIMATE_RIGHT);
            } else if (this.speed.y > 0 && this.speed.y >= this.speed.x) {
                setAnimation(ANIMATE_DOWN);
            } else if (this.speed.x < 0 && this.speed.x <= this.speed.y) {
                setAnimation(ANIMATE_LEFT);
            } else if (this.speed.y < 0 && this.speed.y <= this.speed.x) {
                setAnimation(ANIMATE_UP);
            }
        }

    }

    public abstract void onMoveAccepted(int newX, int newY);

    public abstract void onMoveRejected(int newX, int newY);

    /**
     * Set speed
     *
     * @param speed - Vector2i representing the speed of the entity in the x and y planes
     */
    @Override
    public synchronized void setSpeed(Vector2f speed) {
        setSpeed(speed, 0);
    }

    public synchronized void setSpeed(Vector2f speed, int speedBoost) {
        double  xSqrd = Math.pow(speed.x, 2),
                ySqrd = Math.pow(speed.y, 2),
                hypotenuse = Math.sqrt(xSqrd + ySqrd);


        this.speed = new Vector2f(
                (int) (  (speed.x / hypotenuse) * (SPEED_CAP + speedBoost) ),
                (int) (  (speed.y / hypotenuse) * (SPEED_CAP + speedBoost) )
        );

        //DebugPrinter.print(this, "Speed.. X: " + this.speed.x + ",  Y: " + this.speed.y);

    }

    /**
     * Get the entities speed
     *
     * @return Vector2i value representing the speed in two dimensional space
     */
    @Override
    public Vector2f getVelocity() {
        return speed;
    }

    /**
     * Get the entities speed
     *
     * @return Vector2i value representing the speed in two dimensional space
     */
    @Override
    public double getSpeed() {
        double  xSqrd = Math.pow(speed.x, 2),
                ySqrd = Math.pow(speed.y, 2),
                hypotenuse = Math.sqrt(xSqrd + ySqrd);

        return hypotenuse;
    }

    public void setCharacterStill(int dir) {
        characterStill = new BufferedImage[]{SpriteSheetLoad.getSprite(0, dir, getTheSpriteSheet())};
        this.characterStill = characterStill;
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

/*    public void updateMove(int xDir, int yDir)
    {
        setCenterX(getCenterX()+xDir);
        setCenterY(getCenterY()+yDir);
    }*/

    public void setAnimation(int dir) {
        if(this.tempDir != dir)
        {
            this.tempDir = dir;
            switch (dir) {
                case ANIMATE_RIGHT:
                  BufferedImage[] right = {SpriteSheetLoad.getSprite(0, 2, theSpriteSheet), SpriteSheetLoad.getSprite(1, 2, theSpriteSheet), SpriteSheetLoad.getSprite(0, 2, theSpriteSheet), SpriteSheetLoad.getSprite(2, 2, theSpriteSheet)};
                  this.setFrames(right);
                   break;
                case ANIMATE_LEFT:
                  BufferedImage[] left = {SpriteSheetLoad.getSprite(0, 1, theSpriteSheet), SpriteSheetLoad.getSprite(1, 1, theSpriteSheet), SpriteSheetLoad.getSprite(0, 1, theSpriteSheet), SpriteSheetLoad.getSprite(2, 1, theSpriteSheet)};
                   this.setFrames(left);
                  break;
             case ANIMATE_UP:
                 BufferedImage[] up = {SpriteSheetLoad.getSprite(0, 3, theSpriteSheet), SpriteSheetLoad.getSprite(1, 3, theSpriteSheet), SpriteSheetLoad.getSprite(0, 3, theSpriteSheet), SpriteSheetLoad.getSprite(2, 3, theSpriteSheet)};
                  this.setFrames(up);
                 break;
             case ANIMATE_DOWN:
                 BufferedImage[] down = {SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(1, 0, theSpriteSheet), SpriteSheetLoad.getSprite(0, 0, theSpriteSheet), SpriteSheetLoad.getSprite(2, 0, theSpriteSheet)};
                 this.setFrames(down);
                 break;
            }
        }
    }


    public void stopCharacter() {
        setFrames(characterStill);

        super.stop();
    }

    public void setSpriteSheet(BufferedImage theSpriteSheet) {
        this.theSpriteSheet = theSpriteSheet;
    }

    public BufferedImage getTheSpriteSheet() {
        return theSpriteSheet;
    }

    public void die(){

    }

    public int getExp(){
        return exp;
    }
    public void setExp(int xp){
        this.exp = xp;
    }
}

