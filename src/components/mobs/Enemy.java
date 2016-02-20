package components.mobs;

import game.Room;
import interfaces.MovementListener;

/**
 * @author josh
 * @date 20/02/16.
 */
public abstract class Enemy extends Mob implements MovementListener {
    private Player player;
    private Room room;

    public Enemy(Room room, Player player) {
        super( getWindow().getSize().x - 200  /*getWindow().getSize().x + (new Random().nextInt() % (getWindow().getSize().x / 4))*/,
                getWindow().getSize().y - 200 /*getWindow().getSize().y + (new Random().nextInt() % (getWindow().getSize().y / 4))*/,
                64,
                128);

        this.player = player;
        this.room = room;
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
}
