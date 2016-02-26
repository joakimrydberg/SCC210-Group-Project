package components.mobs;

import components.Projectile;
import controllers.MapMenu;
import game.Room;
import interfaces.CollidingEntity;
import interfaces.MovementListener;
import interfaces.MovingEntity;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;
import tools.Navigator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author josh
 * @date 20/02/16.
 */
public abstract class Enemy extends Mob implements MovementListener, CollidingEntity {
    private long timeAtMove = System.currentTimeMillis() + (long)(Math.random() * 1000);
    protected boolean colliding = false;
    private Room room;
    public final static int IDLE = 0,
            FOLLOW_PATH = 1,
            FOLLOW_PLAYER = 2,
            FLEE_PLAYER = 3,
            BE_CAUTIOUS = 4;
    private boolean processingMove = false;
    private ArrayList<Vector2i> path;
    private int currentPos;
    private int movementState = IDLE;
    private Navigator navigator;
    private int fleeDistance = 400;
    private int cautiousThreshold = 50,
            cautiousDistance = 300;

    public Enemy(Room room) {
        int x, y;

        //randomise position until we get a suitable one
        while (!isPlacable(room, x = MapMenu.randomInt(0, getWindow().getSize().x), y = MapMenu.randomInt(0, getWindow().getSize().y), 64, 128));

        super.create(x, y, 64, 128);
        this.exp = 50;

        this.room = room;
        navigator = new Navigator(room);
        MapMenu.getPlayer().addMovementListener(this);
        onMove(MapMenu.getPlayer());
    }

    @Override
    public boolean isMoveAcceptable(int newX, int newY, int w, int h, MovingEntity movingEntity) {
        //maybe we do collision stuff here?

        return true;
    }

    public Player getPlayer() {
        return MapMenu.getPlayer();
    }

    public Room getRoom() {
        return room;
    }

    public void setMovementState(int movementState) {
        this.movementState = movementState;
    }

    public int getMovementState() {
        return movementState;
    }

    @Override
    public void collide() {

    }
    @Override
    public void onMoveAccepted(int newX, int newY) {
        setCenterX(newX);
        setCenterY(newY);
    }

    @Override
    public void onMoveRejected(int newX, int newY) {
        switch (movementState) {
            case FOLLOW_PLAYER:  case FOLLOW_PATH:  case FLEE_PLAYER:   case BE_CAUTIOUS:
                //trust the path, to any end..
                onMoveAccepted(newX, newY);
                break;
            default:
                setSpeed(new Vector2f(-getVelocity().x, -getVelocity().y));
                //turn around
        }
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

        if (MapMenu.getPlayer() instanceof Ranger || MapMenu.getPlayer() instanceof Mage){

            BufferedImage[] a = charHurt(getTheSpriteSheet(), tempDir, 5);
            setFrames(a);

        }
        else {

            BufferedImage[] a = charHurt(getTheSpriteSheet(), tempDir, 10);
            setFrames(a);
        }

        if (getHealth() < 30) {
            setMovementState(FLEE_PLAYER);
        }
    }


    //==================================================================================================================
    // moving, do not edit below (without good reason). override in subclass if need be

    @Override
    public void onMove(MovingEntity mover) {
        if (!processingMove && System.currentTimeMillis() - timeAtMove > 1000) {
            processingMove = true;

            switch (movementState) {
                case FOLLOW_PLAYER:      //find and attack
                    new Thread(() -> {
                        this.path = navigator.navigateDistanceTo(this, getPlayer(), 0, false);

                        currentPos = this.path.size() - 1;

                        processingMove = false;
                    }).start();
                    break;

                case FLEE_PLAYER:    //run
                    if (Math.sqrt(Math.pow(getCenterX() - getPlayer().getCenterX(), 2) + Math.pow(getCenterY() -  getPlayer().getCenterY(), 2)) < fleeDistance) {
                        new Thread(() -> {
                            this.path = navigator.navigateDistanceAway(this, getPlayer(), fleeDistance, false);

                            currentPos = this.path.size() -1 ;

                            processingMove = false;
                        }).start();

                    } else {
                        processingMove = false;
                    }
                    break;
                case BE_CAUTIOUS:   //stay at a certain distance and also be in line of sight
                    double distanceToPlayer = Math.sqrt(Math.pow(getCenterX() - getPlayer().getCenterX(), 2) + Math.pow(getCenterY() -  getPlayer().getCenterY(), 2));
                    if (distanceToPlayer > cautiousThreshold + fleeDistance) {
                        new Thread(() -> {
                            this.path = navigator.navigateDistanceTo(this, getPlayer(), cautiousDistance + new Random().nextInt(cautiousThreshold * 2) - cautiousThreshold, true);

                            currentPos = this.path.size() - 1;

                            processingMove = false;
                        }).start();
                    } else if (distanceToPlayer < fleeDistance - cautiousThreshold) {
                        new Thread(() -> {
                            this.path = navigator.navigateDistanceAway(this, getPlayer(), cautiousDistance + new Random().nextInt(cautiousThreshold * 2) - cautiousThreshold, true);

                            currentPos = this.path.size() -1 ;

                            processingMove = false;
                        }).start();
                    } else {
                        processingMove = false;
                    }
                    break;
            }
            timeAtMove = System.currentTimeMillis();
        }
    }

    @Override
    public void move() {
        switch (movementState) {
            case FOLLOW_PLAYER:  case FOLLOW_PATH:  case FLEE_PLAYER:   case BE_CAUTIOUS:
                if (path != null) {
                    int newX, newY;

                    if (currentPos >= 0) {
                        Vector2i temp = path.get(currentPos);
                        newX = temp.x - this.getCenterX();
                        newY = temp.y - this.getCenterY();

                        if (Math.abs(newX) < 20 && Math.abs(newY) < 20) {
                            currentPos--;
                        }

                        if (currentPos >= 0) {
                            temp = path.get(currentPos);
                            newX = temp.x - this.getCenterX();
                            newY = temp.y - this.getCenterY();
                            setSpeed(new Vector2f(newX, newY));
                        } else {
                            setSpeed(new Vector2f(0, 0));
                        }

                    } else {
                        setSpeed(new Vector2f(0, 0));
                    }

                    if (getVelocity().x > 0
                            && Math.abs(getVelocity().x) > Math.abs(getVelocity().y)) {
                        tempDir = 2;
                    } else if (getVelocity().x < 0
                            && Math.abs(getVelocity().x) > Math.abs(getVelocity().y)) {
                        tempDir = 1;
                    } else if (getVelocity().y > 0
                            && Math.abs(getVelocity().y) > Math.abs(getVelocity().x)) {
                        tempDir = 0;
                    } else if (getVelocity().y < 0
                            && Math.abs(getVelocity().y) > Math.abs(getVelocity().x)) {
                        tempDir = 3;
                    }

                }
                break;
        }

        super.move();
    }
    public void die(){

        stopCharacter();
        MapMenu.getPlayer().exp = MapMenu.getPlayer().exp + this.exp;
        dead = true;
        MapMenu.getPlayer().coins = MapMenu.getPlayer().coins+100;
    }
    public ArrayList<Projectile> getProjectiles() {


        return new ArrayList<>();
    }

}