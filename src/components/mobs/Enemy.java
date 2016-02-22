package components.mobs;

import game.Room;
import interfaces.CollidingEntity;
import interfaces.MovementListener;
import interfaces.MovingEntity;
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
    private Player player;
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

    public Enemy(Room room, Player player) {
        super( getWindow().getSize().x - 200 - Math.abs(new Random().nextInt() % 50 + 25)  /*getWindow().getSize().x + (new Random().nextInt() % (getWindow().getSize().x / 4))*/,
                getWindow().getSize().y - 200 - Math.abs(new Random().nextInt() % 300) /*getWindow().getSize().y + (new Random().nextInt() % (getWindow().getSize().y / 4))*/,
                64,
                128);

        this.player = player;
        this.room = room;
        navigator = new Navigator(room);
        player.addMovementListener(this);
    }

    @Override
    public boolean isMoveAcceptable(int newX, int newY, int w, int h) {
        //maybe we do collision stuff here?

        return true;
    }

    public Player getPlayer() {
        return player;
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
    public boolean isCollidable() {

        if (( Math.abs((getCenterX() + getWidth()/2) - (getPlayer().getCenterX() + getPlayer().getWidth() / 2)) < 35) && (Math.abs((getCenterY() + getHeight()/2) - (getPlayer().getCenterY() + getPlayer().getHeight() / 2)) < 50)){ //May need some tweaks to numbers
            return true;
        }
        return false;
    }

    @Override
    public boolean checkWithin(int x, int y) {

        if (isCollidable()) {
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

        BufferedImage[] a = charHurt(getTheSpriteSheet(), tempDir);
        setFrames(a); //

    }


    //==================================================================================================================
    // moving, do not edit below (without good reason). override in subclass if need be

    @Override
    public void onMove(MovingEntity mover) {
        if (!processingMove) {
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
        }
    }

    @Override
    public void move() {
        switch (movementState) {
            case FOLLOW_PLAYER:  case FOLLOW_PATH:  case FLEE_PLAYER:case BE_CAUTIOUS:
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
                            setSpeed(new Vector2i(newX, newY));
                        } else {
                            setSpeed(new Vector2i(0, 0));
                        }

                    } else {
                        setSpeed(new Vector2i(0, 0));
                    }
                }
                break;
        }

        super.move();
    }

}
