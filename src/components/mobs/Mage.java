package components.mobs;

import components.Arrow;
import components.Fireball;
import components.Projectile;
import game.Room;
import game.SpriteSheetLoad;
import interfaces.ClickListener;
import interfaces.Clickable;
import interfaces.KeyListener;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by millsr3 on 20/02/2016.
 */
public class Mage extends Player implements ClickListener, KeyListener {

    final static int RECHARGE = 750;

    private ArrayList<Projectile> fireballs = new ArrayList<>();
    private Room room;
    protected long timeAtLastShot = System.currentTimeMillis();

    public Mage() {
        create();

    }
    public void setRoom(Room room){
        room.addClickListener(this);
        this.room = room;
    }

    public void create(){

        System.out.println("mage selected");
        setSpriteSheet(SpriteSheetLoad.loadSprite("MageMaleSheet"));
        setCharacterStill(dir);            //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        // currAnimation = warriorWalk;
        BufferedImage[] mageA = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};

        super.stop(); //@see Mob , must be before we set the frames

        this.setFrames(mageA);

        this.start();
    }

    public void attack(int dir){
        switch(dir){

            case ATTACK_RIGHT:

                BufferedImage[] right = {SpriteSheetLoad.getSprite(4, 2, theSpriteSheet), SpriteSheetLoad.getSprite(5, 2, theSpriteSheet), SpriteSheetLoad.getSprite(4, 2, theSpriteSheet), SpriteSheetLoad.getSprite(5, 2, theSpriteSheet)};
                this.setFrames(right);
                break;
            case ATTACK_DOWN:

                BufferedImage[] down = {SpriteSheetLoad.getSprite(4, 0, theSpriteSheet), SpriteSheetLoad.getSprite(5, 0, theSpriteSheet), SpriteSheetLoad.getSprite(4, 0, theSpriteSheet), SpriteSheetLoad.getSprite(5, 0, theSpriteSheet)};
                this.setFrames(down);
                break;
            case ATTACK_LEFT:

                BufferedImage[] left = {SpriteSheetLoad.getSprite(4, 1, theSpriteSheet), SpriteSheetLoad.getSprite(5, 1, theSpriteSheet), SpriteSheetLoad.getSprite(4, 1, theSpriteSheet), SpriteSheetLoad.getSprite(5, 1, theSpriteSheet)};
                this.setFrames(left);
                break;
            case ATTACK_UP:

                BufferedImage[] up = {SpriteSheetLoad.getSprite(4, 3, theSpriteSheet), SpriteSheetLoad.getSprite(5, 3, theSpriteSheet), SpriteSheetLoad.getSprite(4, 3, theSpriteSheet), SpriteSheetLoad.getSprite(5, 3, theSpriteSheet)};
                this.setFrames(up);
                break;
        }

    }


    public void buttonClicked(Clickable button, Object[] args) {

        if (System.currentTimeMillis() - timeAtLastShot > RECHARGE) {
            timeAtLastShot = System.currentTimeMillis();

            Fireball arrow = new Fireball();

            if (args.length == 1) {
                Event e = (Event) args[0];

                Vector2i from = new Vector2i(this.getCenterX(), this.getCenterY());
                Vector2i to = e.asMouseEvent().position;

                arrow.setCenterX(from.x);
                arrow.setCenterY(from.y);

                arrow.setSpeed(new Vector2f(to.x - from.x, to.y - from.y));
                arrow.correctDirection();
                //arrow.setSpeed(new Vector2f(to.x - from.x, to.y - from.y));

                arrow.addMovementListener(room);
                fireballs.add(arrow);
                room.addEntity(arrow);
            }
        }
    }


    public ArrayList<Projectile> getProjectiles() {
        clearBrokenProjectiles();

        return fireballs;
    }


    public void clearBrokenProjectiles() {
        for (int i = 0; i < fireballs.size(); i++) {
            Projectile arrow = fireballs.get(i);
            if (arrow.getState() == Projectile.BROKEN) {
                fireballs.remove(arrow);
            }
        }
    }


}
