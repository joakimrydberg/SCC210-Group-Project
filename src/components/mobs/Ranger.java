package components.mobs;

import components.Projectile;
import game.Room;
import game.SpriteSheetLoad;
import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.awt.image.BufferedImage;

/**
 * Created by millsr3 on 20/02/2016.
 */
public class Ranger extends Player implements ClickListener {
    private Room room;

    public Ranger(){
        create();
    }

    public Ranger(Room room){
        room.addClickListener(this);
        this.room = room;

        create();
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
        Projectile projectile = new Projectile();

        if (args.length == 1) {
            Event e = (Event)args[0];

            Vector2i from = new Vector2i(this.getCenterX(), this.getCenterY());
            Vector2i to = e.asMouseEvent().position;

            projectile.setCenterX(from.x);
            projectile.setCenterY(from.y);

            projectile.setSpeed(new Vector2f(to.x - from.y, to.y - from.y));
            projectile.correctDirection();
            projectile.setSpeed(new Vector2f(to.x - from.y, to.y - from.y));


            room.addEntity(projectile);
        }

    }
}
