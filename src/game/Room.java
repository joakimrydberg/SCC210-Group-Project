package game;

import components.Projectile;
import components.RoomEntity;
import components.mobs.*;
import interfaces.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.KeyEvent;
import tools.Constants;
import tools.FileHandling;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Joakim Rydberg.
 */
public class Room extends RoomEntity implements MovementListener, ClickListener {
    private String roomID;
    private final static String LEVEL_ID_DIR = "assets" + Constants.SEP + "levels" + Constants.SEP;
    private Level level;
    private HashMap<String, LevelPart> potentialDoors = new HashMap<>();
    public Player player;
    public Room(Level level) {
        this.level = level;
        addEntity(this);
    }

    public void create(String roomID) {
        this.roomID = roomID;

        ArrayList<Object> objects = FileHandling.readFile(LEVEL_ID_DIR + roomID);
        LevelPart[][] tiles = new LevelPart[11][11];

        {   //creating the room layout


            LevelPart tile;

            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    tile = (LevelPart) objects.get(i * 11 + j);

                    if ( tile.getType().equals("Door") ) {
                        String key = null;

                        switch ((int)tile.getRotation()) {
                            case 0:
                                key = "North";
                                break;
                            case 90:
                                key = "East";
                                break;
                            case 180:
                                key = "South";
                                break;
                            case 270:
                                key = "West";
                                break;
                            default:
                                throw new RuntimeException("AHHH invalid rotation" + (int)tile.getRotation());
                        }

                        boolean noPreviousDoors = true;
                        for (String tempKey : potentialDoors.keySet()) {
                            if (tempKey.equals(key)) {
                                noPreviousDoors = false;
                            }
                        }

                        if (noPreviousDoors) {
                            potentialDoors.put(key, tiles[i][j]);
                        }

                        LevelPart replacementPart = null, temp;

                        switch ( (int) tile.getRotation() ) {
                            case 0:    case 180:
                                if ((temp = (LevelPart) objects.get(i * 11 + j - 1)).getType().equals("Wall")) {
                                    replacementPart = temp;
                                } else if ((temp = (LevelPart) objects.get(i * 11 + j + 1)).getType().equals("Wall")) {
                                    replacementPart = temp;
                                }
                                break;
                            case 90:     case 270:
                                if ((temp = (LevelPart) objects.get((i - 1) * 11 + j )).getType().equals("Wall")) {
                                    replacementPart = temp;
                                } else if ((temp = (LevelPart) objects.get((i + 1) * 11 + j)).getType().equals("Wall")) {
                                    replacementPart = temp;
                                }
                                break;
                            default:
                                throw new RuntimeException("AHHH invalid rotation" + (int)tile.getRotation());
                        }

                        replacementPart = (replacementPart == null) ? temp : replacementPart;

                        replacementPart.setRowNo(i);
                        replacementPart.setColNo(j);

                        tile = replacementPart;
                    }
                    tiles[i][j] = tile;
                }
            }
        }

        create(tiles);


        Player p = null;
        if(Player.classType.equals("warrior"))
        {p  = new Warrior();}
        if(Player.classType.equals("mage"))
        {p  = new Mage();}
        if(Player.classType.equals("ranger"))
        {p  = new Ranger();}
        // p.setClass(Player.classType);

        Projectile arrow = new Projectile(this);
        arrow.addClickListener(this);
        p.addMovementListener(this);
        addEntity(arrow);
        EnemyWarrior enemyWarrior = new EnemyWarrior(this, p);
        EnemyMage enemyMage = new EnemyMage(this, p);
        EnemyRanger enemyRanger = new EnemyRanger(this, p);
        //DeathBall deathBall = new DeathBall(this, p);
        addEntity(p);
        player = p;
        addEntity(enemyWarrior);
        addEntity(enemyMage);
        addEntity(enemyRanger);
        // deathBall.setClass("ranger");
        // addEntity(deathBall);
    }

    public void addDoor(String direction) {
        if (!potentialDoors.containsKey(direction)) {
            throw new RuntimeException("Invalid Direction, (there isn't a door facing that way)");
        }

        super.setPart(potentialDoors.get(direction));  //added functionality to RoomEntity and Drawer
    }

    public HashMap<String, LevelPart> getPotentialDoors() {
        return potentialDoors;
    }

    private void locatePotentialDoors() {
        LevelPart[][] levelParts = getTiles();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {

            }
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
        if (left < 0 || right < 0 || top > wSize.x || bottom > wSize.y)
            return false;

        Vector2i partSize = getPartSize();
        LevelPart part;

        for (int i = 0; i < 11; i++) {
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

//

    public void keyPressed(KeyEvent event) {
//        if (event.asKeyEvent().key == Keyboard.Key.P) {
//            loadDrawer(PauseMenu.class);
//            unload();
//        }
    }


    public void keyReleased(KeyEvent event) {

    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {

    }
}