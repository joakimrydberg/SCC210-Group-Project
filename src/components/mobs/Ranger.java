package components.mobs;

import components.Arrow;
import components.Projectile;
import game.Room;
import game.SpriteSheetLoad;
import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by millsr3 on 20/02/2016.
 */
public class Ranger extends Player implements ClickListener, interfaces.Ranger {
    private Room room;
    private ArrayList<Projectile> arrows = new ArrayList<>();
    protected Long timeAtLastShot = System.currentTimeMillis();

    public Ranger(){
        create();
    }

    public void setRoom(Room room){
        room.addClickListener(this);
        this.room = room;
    }

    public void create() {
        System.out.println("ranger selected");
        setSpriteSheet(SpriteSheetLoad.loadSprite("RangerMaleSheet"));
        setCharacterStill(dir);
        BufferedImage[] ranger = {SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(1, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(0, 0, getTheSpriteSheet()), SpriteSheetLoad.getSprite(2, 0, getTheSpriteSheet())};


        super.stop(); //@see Mob , must be before we set the frames

        //warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
        this.setFrames(ranger);

        this.start();
    }

    public void attack(int dir) {
        switch (dir) {

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

    @Override
    public void buttonClicked(Clickable button, Object[] args) {

        if (System.currentTimeMillis() - timeAtLastShot > RECHARGE) {
            timeAtLastShot = System.currentTimeMillis();
            Arrow arrow = new Arrow();

            if (args.length == 1) {
                Event e = (Event) args[0];

                Vector2i from = new Vector2i(this.getCenterX(), this.getCenterY());
                Vector2i to = e.asMouseEvent().position;

                System.out.println("Player: " + getCenterX() + " " + getCenterY());
                arrow.setCenterX(from.x);
                arrow.setCenterY(from.y);

                arrow.setSpeed(new Vector2f(to.x - from.x, to.y - from.y));
                arrow.correctDirection();
                //arrow.setSpeed(new Vector2f(to.x - from.x, to.y - from.y));

                arrow.addMovementListener(room);
                arrows.add(arrow);
                room.addEntity(arrow);
            }
        }
    }

    @Override
    public ArrayList<Projectile> getProjectiles() {
        clearBrokenProjectiles();

        return arrows;
    }

    @Override
    public void clearBrokenProjectiles() {
        for (int i = 0; i < arrows.size(); i++) {
            Projectile arrow = arrows.get(i);
            if (arrow.getState() == Projectile.BROKEN) {
                arrows.remove(arrow);
            }
        }
    }
}
