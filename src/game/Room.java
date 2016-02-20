package game;

import components.RoomEntity;
import components.mobs.EnemyWarrior;
import components.mobs.Player;
import components.mobs.Warrior;
import interfaces.MovementListener;
import interfaces.MovingEntity;
import org.jsfml.system.Vector2i;
import tools.Constants;
import tools.FileHandling;

import java.util.ArrayList;

/**
 * @author Joakim Rydberg.
 */
public class Room extends RoomEntity implements MovementListener {
    private String roomID;
    private ArrayList<String> potentialDoors = new ArrayList<>();
    private final static String LEVEL_ID_DIR = "assets" + Constants.SEP + "levels" + Constants.SEP;
    private Level level;

    public Room(Level level) {
        this.level = level;
    }

    public void create(String roomID) {
        this.roomID = roomID;
        ArrayList<Object> objects = FileHandling.readFile(LEVEL_ID_DIR + roomID);
        LevelPart[][] tiles = new LevelPart[11][11];

        for (int i = 0; i < 11; i++){
            for (int j = 0; j < 11; j++){
                tiles[i][j] = (LevelPart) objects.get(i * 11 + j);
            }
        }
       /* Player p = new Player();
        p.setClass(Player.classType);*/

        Warrior w = new Warrior();

        EnemyWarrior enemyWarrior = new EnemyWarrior(this, w);
        enemyWarrior.setClass("warrior");

        w.addMovementListener(this);
        create(tiles);

        addEntity(w);
        addEntity(enemyWarrior);

        locatePotentialDoors();
    }

    public ArrayList<String> getPotentialDoors() {
        return potentialDoors;
    }

    private void locatePotentialDoors(){  //TODO make work with other positions
        LevelPart[][] levelParts = getTiles();

        if(levelParts[0][6].getType().equals("Door")) {
            potentialDoors.add("North");
        }
        if(levelParts[6][0].getType().equals("Door")) {
            potentialDoors.add("West");
        }
        if(levelParts[6][10].getType().equals("Door")) {
            potentialDoors.add("East");
        }
        if(levelParts[10][6].getType().equals("Door")) {
            potentialDoors.add("South");
        }
    }

    @Override
    public boolean isMoveAcceptable(int x, int y, int w, int h) {
        Vector2i wSize = getWindow().getSize();

        final int left = x - w / 2,
                right = x + w / 2,
                top = y - h / 2,
                bottom = y + h / 2;

        //return false if outside of window
        if  (left < 0 || right < 0 || top > wSize.x || bottom > wSize.y)
            return false;

        Vector2i partSize = getPartSize();
        LevelPart part;

        for (int i = 0; i < 11; i++){
            for (int j = 0; j < 11; j++) {
                part = getPart(i, j);

                if (part.getType().equals("Wall")) {
                    final int partLeft = j * partSize.x,
                    partRight = (j + 1) * partSize.x,
                    partBottom = (i + 1) * partSize.y,
                    partTop = i * partSize.y;

                    //col * w + w/2, row * h + h/2

                    if (left < partRight
                            && right > partLeft
                            && bottom > partTop
                            && top < partBottom) {

                            return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void onMove(MovingEntity mover) {

    }
}
