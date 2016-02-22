package components;

import components.mobs.Ranger;
import game.Room;
import interfaces.ClickListener;
import interfaces.Clickable;
import interfaces.MovementListener;
import interfaces.MovingEntity;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;
import tools.Navigator;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by millsr3 on 22/02/2016.
 */
public class Projectile extends Image implements Clickable, MovingEntity {

    private ArrayList<MovementListener> listeners = new ArrayList<>();
    private Vector2i speed = new Vector2i(0,0);
    private Room r;
    private Navigator ne;
    public Projectile(Room room){

        super(0, 0, 35, 35, "H:\\My Documents\\SCC210-Group-Project2\\assets\\art\\arrow.png");
        hide();
        r = room;
        Navigator n = new Navigator(r);
        ne = n;
    }

    @Override
    public void clicked(Event e) {



            Vector2i p = e.asMouseButtonEvent().position;

            int x = p.x, y = p.y;

            Vector2f to = new Vector2f(x, y);
            Vector2f from = new Vector2f(r.player.getCenterX(), r.player.getCenterY());


        /*if(ne.inLineOfSight(to,
                from)){
            setSpeed(p);
        }*/

            setCenterX((int) from.x);
            setCenterY((int) from.y);
            setSpeed(new Vector2i(x, y));


            ((Ranger) r.player).attack(r.player.tempDir);

            show();


    }

    @Override
    public void addClickListener(ClickListener clickListener) {

    }

    @Override
    public void move() { //this method must take no arguments
        { //moving
            final int newX = (int)(getCenterX() + speed.x), newY = (int)(getCenterY() + speed.y);
            System.out.format("%d %d \n",(getCenterX() + speed.x), (getCenterY() + speed.y));

            if (newX != getCenterX() || newY != getCenterY()) {

                //checking all the MovementListeners are 'okay' with the proposed move
                boolean move = true;
                for (MovementListener listener : listeners) {
                    move = listener.isMoveAcceptable(newX, newY + getHeight() / 6, getWidth() / 2, getHeight() / 4) // It's so the top half of the player can overlap on the walls etc TODO adjust these values if they aren't great
                            && move;                                             // a little weird but for reasons.
                }

                if (move) {
                    setCenterX(newX);
                    setCenterY(newY);
                    setCenterX(newX);
                    setCenterY(newY);
                }


                draw();  //drawing to the screen

                if (move) {
                    for (MovementListener listener : listeners) {  //must be at end of method
                        listener.onMove(this);
                    }
                }
            }
        }

    }

    @Override
    public void setSpeed(Vector2i speed) {

        double  xSqrd = Math.pow(speed.x, 2),
                ySqrd = Math.pow(speed.y, 2),
                hypotenuse = Math.sqrt(xSqrd + ySqrd);


        this.speed = new Vector2i(
                (int) (  (speed.x / hypotenuse) * 5 ),
                (int) (  (speed.y / hypotenuse) * 5 )
        );
    }

    @Override
    public double getSpeed() {
        return 0;
    }

    @Override
    public Vector2i getVelocity() {
        return null;
    }

    @Override
    public void setSpeedMultiplier(float multiplier) {

    }

    @Override
    public void addMovementListener(MovementListener listener) {

        listeners.add(listener);

    }
}
