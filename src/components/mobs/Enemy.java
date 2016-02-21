package components.mobs;

import game.Room;
import interfaces.MovementListener;
import interfaces.MovingEntity;
import org.jsfml.system.Vector2i;
import tools.Navigator;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author josh
 * @date 20/02/16.
 */
public abstract class Enemy extends Mob implements MovementListener {
    private Player player;
    protected boolean colliding = false;
    private Room room;
    public final static int IDLE = 0,
            FOLLOW_PATH = 1,
            FOLLOW_PLAYER = 2,
            FLEE_PLAYER = 3;
    private boolean processingMove = false;
    private ArrayList<Vector2i> path;
    private int currentPos;
    private int movementState = IDLE;
    private Navigator navigator;

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
    public void onMove(MovingEntity mover) {
        if (!processingMove && movementState == FOLLOW_PLAYER) {
            processingMove = true;

            new Thread(() -> {  //TODO remove threading to add new slow-mo enemy, i love bugs
                this.path = navigator.navigateTo(this, getPlayer());

                currentPos = this.path.size() -1;

                processingMove= false;
            }).start();

        }
    }

    @Override
    public void move() {
        switch (movementState) {
            case FOLLOW_PLAYER:  case FOLLOW_PATH:
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

    public void damaged(){



    }

}
