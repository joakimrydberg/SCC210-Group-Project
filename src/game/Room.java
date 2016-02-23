package game;

import abstract_classes.Entity;
import components.Message;
import components.Projectile;
import components.RoomEntity;
import components.mobs.*;
import components.mobs.Ranger;
import controllers.GameOverMenu;
import controllers.MapMenu;
import controllers.PauseMenu;
import interfaces.*;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import tools.Constants;
import tools.FileHandling;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Joakim Rydberg.
 */
public class Room extends RoomEntity implements MovementListener, ClickListener, Clickable, KeyListener {
    private String roomID;
    private ArrayList<ClickListener> listeners = new ArrayList<>();
    private final static String LEVEL_ID_DIR = "assets" + Constants.SEP + "levels" + Constants.SEP;
    private Level level;
    private HashMap<String, LevelPart> potentialDoors = new HashMap<>();
    private Message levelUp = null;
    int x = 0;
    private boolean endRoom = false;
    private PauseMenu pauseMenu = new PauseMenu();

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
                            potentialDoors.put(key, tile);
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

        { //adding the player
            MapMenu.getPlayer().addMovementListener(this);
            MapMenu.getPlayer().place(this);
            addEntity(MapMenu.getPlayer());
        }

        int enemyWarriors = 0,
                enemyMages = 0,
                enemyRangers = 0;
        int diff = level.getDifficulty(getName());

        { //difficulty tweaks //todo make better, much better
            System.out.print(diff);
            if (diff == 1) {
                enemyWarriors = MapMenu.randomInt(0, 3);
                enemyRangers = MapMenu.randomInt(0, 1);
                enemyMages = MapMenu.randomInt(0, 0);
            } else if (diff < 3) {
                enemyWarriors = MapMenu.randomInt(0, 2);
                enemyRangers = MapMenu.randomInt(0, 3);
                enemyMages = MapMenu.randomInt(0, 1);
            } else if (diff < 6) {
                enemyWarriors = MapMenu.randomInt(2, 3);
                enemyRangers = MapMenu.randomInt(2, 4);
                enemyMages = MapMenu.randomInt(2, 3);
            } else if (diff < 9) {
                enemyWarriors = MapMenu.randomInt(3, 4);
                enemyRangers = MapMenu.randomInt(0, 3);
                enemyMages = MapMenu.randomInt(0, 7);
            } else if (diff < 12) {
                enemyWarriors = MapMenu.randomInt(0, 6);
                enemyRangers = MapMenu.randomInt(0, 6);
                enemyMages = MapMenu.randomInt(0, 6);
            } else {
                enemyWarriors = MapMenu.randomInt(0, 8);
                enemyRangers = MapMenu.randomInt(0, 8);
                enemyMages = MapMenu.randomInt(0, 8);
            }
        }

        { //populating with enemies

            EnemyWarrior enemyWarrior;
            for (int i = 0; i < enemyWarriors; i++) {
                enemyWarrior = new EnemyWarrior(this);
                enemyWarrior.addMovementListener(this);
                addEntity(enemyWarrior);
            }

            EnemyMage enemyMage;
            for (int i = 0; i < enemyMages; i++) {
                enemyMage = new EnemyMage(this);
                enemyMage.addMovementListener(this);
                addEntity(enemyMage);
            }

            EnemyRanger enemyRanger;
            for (int i = 0; i < enemyRangers; i++) {
                enemyRanger = new EnemyRanger(this);
                enemyRanger.addMovementListener(this);
                addEntity(enemyRanger);
            }
        }
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

    @Override
    public boolean isMoveAcceptable(int x, int y, int w, int h) {
        return isMoveAcceptable(x, y, w, h, false);
    }

    public boolean isMoveAcceptable(int x, int y, int w, int h, boolean override) {
        if (isLoaded() || override) {
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

//                    //col * w + w/2, row * h + h/2
//                    if (w == 1) {
//                        System.out.format("l: %d, r: %d, t: %d, b %d, pl: %d, pr: %d, pt: %d, pb %d\n"
//                                , left, right, top, bottom,
//                                partLeft, partRight, partTop, partBottom);
//                    }
                        if (left < partRight
                                && right > partLeft
                                && bottom > partTop
                                && top < partBottom) {

                            return false;
                        }
                    }
                }
            }
        }
        return true;

    }

    @Override
    public void onMove(MovingEntity mover) {
        if (isLoaded()) {
            if (mover instanceof Player) {
                for (LevelPart door : potentialDoors.values()) {

                    Vector2i partSize = getPartSize();

                    if (door.displayed) {
                        double dist = Math.sqrt(Math.pow(MapMenu.getPlayer().getCenterX() - (door.getColNo() * partSize.x + partSize.x), 2)
                                + Math.pow(MapMenu.getPlayer().getCenterY() - (door.getRowNo() * partSize.y + partSize.y), 2));

                        // System.out.println(dist);
                        int i = door.getRowNo(), j = door.getRowNo();
                        final int partLeft = j * partSize.x,
                                partRight = (j + 1) * partSize.x,
                                partBottom = (i + 1) * partSize.y,
                                partTop = i * partSize.y;

//                    //col * w + w/2, row * h + h/2
//                    if (w == 1) {
//                        System.out.format("l: %d, r: %d, t: %d, b %d, pl: %d, pr: %d, pt: %d, pb %d\n"
//                                , left, right, top, bottom,
//                                partLeft, partRight, partTop, partBottom);
//                    }
                        if (MapMenu.getPlayer().getCenterX() < partRight
                                && MapMenu.getPlayer().getCenterX() > partLeft
                                && MapMenu.getPlayer().getCenterY() > partTop
                                && MapMenu.getPlayer().getCenterY() < partBottom) {

                            for (String key : potentialDoors.keySet()) {
                                if (potentialDoors.get(key).equals(door)) {
                                    //System.out.println("Move Rooms");
                                    level.moveRooms(this, key);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void load() {
        if (MapMenu.getPlayer() instanceof Ranger) {
            ((Ranger) MapMenu.getPlayer()).setRoom(this);
        }
        if (MapMenu.getPlayer() instanceof Mage) {((Mage) MapMenu.getPlayer()).setRoom(this);}


        super.load();
    }
//

    public void keyPressed(KeyEvent event) {
        if (event.asKeyEvent().key == Keyboard.Key.P) {
            //loadDrawer(PauseMenu.class);
            MapMenu.getPlayer().printInventory();
            pauseMenu.loadInPlayer(MapMenu.getPlayer());
            pauseMenu.load();
            unload();
        }
    }


    public void keyReleased(KeyEvent event) {

    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {

    }

    @Override
    public void clicked(Event e) {
        for (ClickListener listener : listeners) {
            listener.buttonClicked(this, new Object[] {e});
        }
    }

    @Override
    public void addClickListener(ClickListener listener) {
        listeners.add(listener);
    }

    @Override
    public void drawAll() {
        if (isLoaded()) {
            draw();

            for (int i = 0; i < getEntities().size(); i++) {   //done properly to avoid co-modification
                Entity entity = getEntity(i);

                if (levelUp != null) {
                    levelUp.follow(MapMenu.getPlayer().getCenterX(), MapMenu.getPlayer().getCenterY() - 50);
                }

                if(levelUp != null && x > 10000)

                    levelUp.hidden = true;

                if(entity instanceof CollidingEntity
                        && entity instanceof Enemy) {


                    if (((CollidingEntity) entity).checkWithin(MapMenu.getPlayer().getCenterX(), MapMenu.getPlayer().getCenterY()) && MapMenu.getPlayer().attacking) {
                        ((Enemy) entity).damaged();
                    }

                    if (entity instanceof EnemyWarrior) {
                        if (((CollidingEntity) MapMenu.getPlayer()).checkWithin(entity.getCenterX(), entity.getCenterY()) && ((EnemyWarrior)entity).attacking) {
                            (MapMenu.getPlayer()).damaged();
                        }
                    }

                    if (entity instanceof EnemyRanger) { //and maybe mage?
                        for (Projectile projectile : ((EnemyRanger) entity).getProjectiles()) {
                            if (projectile.getState() == Projectile.OKAY
                                    && ((CollidingEntity) MapMenu.getPlayer()).checkWithin(projectile.getCenterX(), projectile.getCenterY())) {
                                (MapMenu.getPlayer()).damaged();
                            }
                        }
                    }

//                    if (MapMenu.getPlayer() instanceof Ranger || MapMenu.getPlayer() instanceof Mage) { //and maybe mage?
//                        for (Projectile projectile : ((Player) MapMenu.getPlayer()).getProjectiles()) {
//                            if (projectile.getState() == Projectile.OKAY
//                                    &&((CollidingEntity) entity).checkWithin(projectile.getCenterX(), projectile.getCenterY())) {
//                                ((Enemy) entity).damaged();
//                            }
//                        }
//                    }


                }

                if(entity instanceof Enemy && ((Enemy)entity).health < 0 && !((Enemy)entity).dead){
                    ((Enemy)entity).die();
                }
                if(entity instanceof Player && ((Player)entity).health < 0 && !((Player)entity).dead){
                    ((Player)entity).die();
                    loadDrawer(GameOverMenu.class);
                    unload();
                }


                if(MapMenu.getPlayer().getExp() > 99){

                    levelUp = new Message(MapMenu.getPlayer().getCenterX(), MapMenu.getPlayer().getCenterY() - 50, 0, "LEVEL UP", Color.RED, 20);
                    addEntity(levelUp);
                    x = 0;
                    MapMenu.getPlayer().setExp(0);
                    MapMenu.getPlayer().level++;

                }

                if (entity instanceof MovingEntity)
                    ((MovingEntity) entity).move();


                if(!entity.hidden)
                    entity.draw();

                x++;
            }


        }


    }

    /**
     * Checks whether the x and y parameters
     *
     * @param x - X coordinate to check
     * @param y - Y coordinate to check
     * @return - true if checkWithin, false if not;
     */
    @Override
    public boolean checkWithin(int x, int y) {
        return true;
    }

    /**
     * Checks whether the x and y parameters passed in an Event obj
     *
     * @param e - the Event that caused this method call
     * @return - true if checkWithin, false if not;
     */
    @Override
    public boolean checkWithin(Event e) {
        return true;
    }


    public void setEndRoom(boolean endRoom) {
        this.endRoom = endRoom;
    }

    public boolean getEndRoom() {
        return this.endRoom;
    }
}