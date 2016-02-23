package components;

import interfaces.MovementListener;
import interfaces.MovingEntity;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

/**
 * Created by millsr3 on 22/02/2016.
 */
public abstract class Projectile extends Image implements MovingEntity {

    private ArrayList<MovementListener> listeners = new ArrayList<>();
    private Vector2f speed = new Vector2f(0,0);
    public final static int BROKEN = 0, OKAY = 1;
    private boolean broken = false;


    public Projectile(String spriteName){

        super(0, 0, 35, 35, spriteName);
        hide();


    }

    @Override
    public void move() { //this method must take no arguments
        { //moving
            { //mover from mob
                //  if (!broken) {
                final int newX = (int) (getCenterX() + speed.x), newY = (int) (getCenterY() + speed.y);
                // System.out.format("%f %f \n",(getCenterX() + speed.x), (getCenterY() + speed.y));

                if (newX != getCenterX() || newY != getCenterY()) {

                    boolean move = true;
                    //checking all the MovementListeners are 'okay' with the proposed move
                    if (!broken) {
                        for (MovementListener listener : listeners) {
                            move = listener.isMoveAcceptable(newX - getWidth() / 2, newY - getHeight() / 2, 1, 1, this) // It's so the top half of the player can overlap on the walls etc TODO adjust these values if they aren't great
                                    && move;                                             // a little weird but for reasons.
                        }
                    }  else {
                        move = false;
                    }

                    if (move) {
                        setCenterX(newX);
                        setCenterY(newY);

                    } else {
                        broken = true;

                    }

                    draw();

                    //draw();  //drawing to the screen

                    if (move) {
                        for (MovementListener listener : listeners) {  //must be at end of method
                            listener.onMove(this);
                        }
                    }
                }
                //  }

            }
        }

    }

    public void correctDirection() {
        Vector2f direction = getVelocity();

        float angle = (float) Math.atan2(direction.y, direction.x);


        //      System.out.println(angle * (180 / Math.PI)+ 90);
        getTransformable(0).setOrigin(getCenterX() - getTopLeftX(), getCenterY()-getTopLeftY());
        super.rotate(((float)(angle * (180 / Math.PI) + 90)),true);
        //getTransformable(0).setOrigin(0,0);


    }


    @Override
    public void setSpeed(Vector2f speed) {

        double  xSqrd = Math.pow(speed.x, 2),
                ySqrd = Math.pow(speed.y, 2),
                hypotenuse = Math.sqrt(xSqrd + ySqrd);


        this.speed = new Vector2f(
                (int) (  (speed.x / hypotenuse) * 5 ),
                (int) (  (speed.y / hypotenuse) * 5 )
        );

        correctDirection();
    }

    @Override
    public double getSpeed() {
        double  xSqrd = Math.pow(speed.x, 2),
                ySqrd = Math.pow(speed.y, 2),
                hypotenuse = Math.sqrt(xSqrd + ySqrd);

        return hypotenuse;
    }

    @Override
    public Vector2f getVelocity() {
        return speed;
    }

    @Override
    public void setSpeedMultiplier(float multiplier) {

    }

    @Override
    public void addMovementListener(MovementListener listener) {
        listeners.add(listener);
    }

    public int getState() {
        return broken ? BROKEN : OKAY;
    }

}
