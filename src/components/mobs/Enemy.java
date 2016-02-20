package components.mobs;

import interfaces.MovementListener;

/**
 * @author josh
 * @date 20/02/16.
 */
public abstract class Enemy extends Mob implements MovementListener {
    private Player player;
    public Enemy(Player player) {
        super( 200  /*getWindow().getSize().x + (new Random().nextInt() % (getWindow().getSize().x / 4))*/,
                200 /*getWindow().getSize().y + (new Random().nextInt() % (getWindow().getSize().y / 4))*/,
                64,
                128);

        this.player = player;
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
}
