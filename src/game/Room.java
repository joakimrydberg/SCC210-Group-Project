package game;

import abstract_classes.Entity;
import components.RoomEntity;
import components.mobs.EnemyWarrior;
import components.mobs.Player;
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
    private static int navPixelsRowCount = 11 * 5;
    private NavPixel[][] navPixels = new NavPixel[navPixelsRowCount][navPixelsRowCount];
    private NavReturn[] navTrace = new NavReturn[10];


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

        Player p = new Player();
        p.setClass(Player.classType);

        EnemyWarrior enemyWarrior = new EnemyWarrior(this, p);

        p.addMovementListener(this);
        create(tiles);

        addEntity(p);
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
    static int i = 0;

    public Vector2i navigateTo(Entity frm, Entity to) {
        this.i++;
        NavPixel navFrm = new NavPixel(0, 0),
                navTo = new NavPixel(0, 0);

        for (int i = 0; i < navPixels.length; i++) {
            for (int j = 0; j < navPixels[0].length; j++) {
                navPixels[i][j] = new NavPixel(i, j);

                if (    Math.sqrt(Math.pow(navPixels[i][j].x - to.getCenterX(), 2) + Math.pow(navPixels[i][j].y - to.getCenterY(), 2))
                        < Math.sqrt(Math.pow(navTo.x - to.getCenterX(), 2) + Math.pow(navTo.y - to.getCenterY(), 2))
                        ) {
                    navTo = navPixels[i][j];
                }

                if (    Math.sqrt(Math.pow(navPixels[i][j].x - frm.getCenterX(), 2) + Math.pow(navPixels[i][j].y - frm.getCenterY(), 2))
                        < Math.sqrt(Math.pow(navFrm.x - frm.getCenterX(), 2) + Math.pow(navFrm.y - frm.getCenterY(), 2))
                        ) {
                    navFrm = navPixels[i][j];
                }
            }
        }

        NavReturn[] navTrace = new NavReturn[10];

        for (int i = 0; i < navTrace.length; i++)
            navTrace[i] = null;

        NavReturn navReturn = navigateTo(navFrm, navTo, 0, navTrace);

        if (navReturn.nextNav == null)
            return new Vector2i(0, 0);

        for (int i = navTrace.length - 1; i > 0; i--) {
            if (navTrace[i] != null) {
                navReturn = navTrace[i];
                break;
            }
        }
        return new Vector2i((navReturn.nextNav.x - frm.getCenterX()) ,
                (navReturn.nextNav.y - frm.getCenterY()));
    }

    public NavReturn navigateTo(NavPixel frm, NavPixel to, int stepCount, NavReturn[] navTrace) {
        if (frm.i == to.i && frm.j == to.j) {

            return new NavReturn(stepCount, to);
        }

        stepCount++;

        NavReturn navReturn = new NavReturn(Integer.MAX_VALUE, null),
                tempNavReturn;
        NavReturn[] navTraceReturn = null, tempNavTrace;
        NavPixel tempNavPixel = null;

        if (frm.i > 0) {
            tempNavPixel = navPixels[frm.i - 1][frm.j];
            if (tempNavPixel.visitedTime > stepCount
                    && !getPart(tempNavPixel.i / (navPixelsRowCount / 11), tempNavPixel.j / (navPixelsRowCount / 11) ).getType().equals("Wall")) {

                tempNavPixel.visitedTime = stepCount;

                tempNavTrace = (navTrace == null || navTrace.length - stepCount <= 0)
                        ? null
                        : new NavReturn[navTrace.length - stepCount];

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, tempNavTrace);

                if (navReturn.stepCount > tempNavReturn.stepCount
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;
                    navTraceReturn = tempNavTrace;
                }
            }
        }

        if (frm.i  < navPixelsRowCount  - 1) {
            tempNavPixel = navPixels[frm.i + 1][frm.j];
            if (tempNavPixel.visitedTime > stepCount
                    && !getPart(tempNavPixel.i / (navPixelsRowCount / 11), tempNavPixel.j / (navPixelsRowCount / 11) ).getType().equals("Wall")) {

                tempNavPixel.visitedTime = stepCount;
                tempNavTrace = (navTrace == null || navTrace.length - stepCount <= 0)
                        ? null
                        : new NavReturn[navTrace.length - stepCount];

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, tempNavTrace);

                if (navReturn.stepCount > tempNavReturn.stepCount
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;
                    navTraceReturn = tempNavTrace;
                }
            }
        }

        if (frm.j > 0) {
            tempNavPixel = navPixels[frm.i][frm.j - 1];
            if (tempNavPixel.visitedTime > stepCount
                    && !getPart(tempNavPixel.i / (navPixelsRowCount / 11), tempNavPixel.j / (navPixelsRowCount / 11) ).getType().equals("Wall")) {

                tempNavPixel.visitedTime = stepCount;
                tempNavTrace = (navTrace == null || navTrace.length - stepCount <= 0)
                        ? null
                        : new NavReturn[navTrace.length - stepCount];

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, tempNavTrace);

                if (navReturn.stepCount > tempNavReturn.stepCount
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;
                    navTraceReturn = tempNavTrace;
                }
            }
        }

        if (frm.j < navPixelsRowCount - 1) {
            tempNavPixel = navPixels[frm.i][frm.j + 1];
            if (tempNavPixel.visitedTime > stepCount
                    && !getPart(tempNavPixel.i / (navPixelsRowCount / 11), tempNavPixel.j / (navPixelsRowCount / 11) ).getType().equals("Wall")) {

                tempNavPixel.visitedTime = stepCount;
                tempNavTrace = (navTrace == null || navTrace.length - stepCount <= 0)
                        ? null
                        : new NavReturn[navTrace.length - stepCount];

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, tempNavTrace);

                if (navReturn.stepCount > tempNavReturn.stepCount
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;
                    navTraceReturn = tempNavTrace;
                }
            }
        }

        if (navTrace != null && stepCount -1  < navTrace.length ) {
            navTrace[stepCount -1] = navReturn;

            if (navTraceReturn != null) {
                int i = stepCount;
                for (NavReturn temp : navTraceReturn) {
                    navTrace[i] = temp;
                    i++;
                }
            }
        }

        return navReturn;
    }


    private class NavPixel {
        int i, j;
        int x, y;
        int visitedTime;

        NavPixel(int i, int j) {
            this.i = i;
            this.j = j;
            this.x = j * (getWindow().getSize().x / navPixelsRowCount) + (getWindow().getSize().x / navPixelsRowCount) / 2;
            this.y = i * (getWindow().getSize().y / navPixelsRowCount) + (getWindow().getSize().y / navPixelsRowCount) / 2;
            this.visitedTime = Integer.MAX_VALUE;
        }

        public Vector2i getLevelPartXY() {
            return new Vector2i( i / navPixelsRowCount, j / navPixelsRowCount );
        }
    }

    private class NavReturn {
        int stepCount;
        NavPixel nextNav;

        NavReturn(int stepCount, NavPixel nextNav) {
            this.stepCount = stepCount;
            this.nextNav = nextNav;
        }
    }
}
