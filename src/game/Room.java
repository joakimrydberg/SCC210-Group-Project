package game;

import abstract_classes.Entity;
import components.RoomEntity;
import components.mobs.EnemyWarrior;
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
    private static int navPixelsRowCount = 11 * 2;
    private NavPixel[][] navPixels = new NavPixel[navPixelsRowCount][navPixelsRowCount];
    private static final int TRACE_LIM = 0;
    private static final int DIAGONAL = 1, STRAIGHT = 0;  //todo do not remove
    private int lastMoveDirection = 5;  //todo do not remove
    private int frmWidth = 64,
            frmHeight = 128;

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

        Warrior p = new Warrior();
        //p.setClass(Player.classType);



        p.addMovementListener(this);
        create(tiles);

        EnemyWarrior enemyWarrior = new EnemyWarrior(this, p);
        EnemyWarrior enemyWarrior1 = new EnemyWarrior(this, p);
        EnemyWarrior enemyWarrior2 = new EnemyWarrior(this, p);

        addEntity(p);
        addEntity(enemyWarrior);
        addEntity(enemyWarrior2);
        addEntity(enemyWarrior1);

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


    public ArrayList<Vector2i> navigateTo(Entity frm, Entity to) {
        frmHeight = frm.getHeight();
        frmWidth = frm.getWidth();

        NavPixel navFrm = new NavPixel(0, 0),
                navTo = new NavPixel(0, 0);

        for (int i = 0; i < navPixels.length; i++) {
            for (int j = 0; j < navPixels[0].length; j++) {
                navPixels[i][j] = new NavPixel(i, j);

                if (    (Math.sqrt(Math.pow(navPixels[i][j].x - to.getCenterX(), 2) + Math.pow(navPixels[i][j].y - to.getCenterY(), 2))
                        < Math.sqrt(Math.pow(navTo.x - to.getCenterX(), 2) + Math.pow(navTo.y - to.getCenterY(), 2)))
                        && !isInCollidable(navPixels[i][j])
                        ) {
                    navTo = navPixels[i][j];
                }

                if (    (Math.sqrt(Math.pow(navPixels[i][j].x - frm.getCenterX(), 2) + Math.pow(navPixels[i][j].y - frm.getCenterY(), 2))
                        < Math.sqrt(Math.pow(navFrm.x - frm.getCenterX(), 2) + Math.pow(navFrm.y - frm.getCenterY(), 2)))
                        && !isInCollidable(navPixels[i][j])
                        ) {
                    navFrm = navPixels[i][j];
                }
            }
        }

        NavReturn[] navTrace = new NavReturn[TRACE_LIM];

        for (int i = 0; i < navTrace.length; i++)
            navTrace[i] = null;

        NavReturn navReturn = navigateTo(navFrm, navTo, 0, 0);
        NavPixel navPixel = navReturn.nextNav;

 //       if (navPixel == null)
     //       return new Vector2i(0, 0);

//        //TODO do not remove
//        for (int i = 0; i < navReturn.navTrace.length; i++)  {
//            if (navReturn.navTrace[i] != null)
//                navPixel = navReturn.navTrace[i];
//        }

        return navReturn.navTrace;
    }




    public NavReturn navigateTo(NavPixel frm, NavPixel to, int stepCount, double timer) {
        if (frm.i == to.i && frm.j == to.j) {
            return new NavReturn(stepCount, timer, to);
        }

        stepCount++;

        NavReturn navReturn = new NavReturn(Integer.MAX_VALUE, Double.MAX_VALUE, null),
                tempNavReturn;
        NavPixel tempNavPixel;

        //north
        if (frm.i > 0) {
            tempNavPixel = navPixels[frm.i - 1][frm.j];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    navReturn.addNavPixel(navReturn.nextNav);
                }
            }
        }

        //south
        if (frm.i  < navPixelsRowCount  - 1) {
            tempNavPixel = navPixels[frm.i + 1][frm.j];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    navReturn.addNavPixel(navReturn.nextNav);
                }
            }
        }

        //west
        if (frm.j > 0) {
            tempNavPixel = navPixels[frm.i][frm.j - 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    navReturn.addNavPixel(navReturn.nextNav);
                }
            }
        }

        //east
        if (frm.j < navPixelsRowCount - 1) {
            tempNavPixel = navPixels[frm.i][frm.j + 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    navReturn.addNavPixel(navReturn.nextNav);
                }
            }
        }

//        //north east
//        if (frm.i > 0
//                && frm.j < navPixelsRowCount - 1) {
//
//            tempNavPixel = navPixels[frm.i - 1][frm.j + 1];
//            if (tempNavPixel.visitedTime > timer
//                    && !isInCollidable(tempNavPixel)) {
//
//                tempNavPixel.visitedTime = timer;
//
//                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));
//
//                if (navReturn.timer > tempNavReturn.timer
//                        && tempNavReturn.nextNav != null) {
//
//                    navReturn = tempNavReturn;
//                    navReturn.nextNav = tempNavPixel;
//
//                    if (stepCount < TRACE_LIM)
//                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
//                }
//            }
//        }
//
//        // south east
//        if (frm.i  < navPixelsRowCount  - 1
//                && frm.j < navPixelsRowCount - 1) {
//
//            tempNavPixel = navPixels[frm.i + 1][frm.j + 1];
//            if (tempNavPixel.visitedTime > timer
//                    && !isInCollidable(tempNavPixel)) {
//
//                tempNavPixel.visitedTime = timer;
//
//                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));
//
//                if (navReturn.timer > tempNavReturn.timer
//                        && tempNavReturn.nextNav != null) {
//
//                    navReturn = tempNavReturn;
//                    navReturn.nextNav = tempNavPixel;
//
//                    if (stepCount < TRACE_LIM)
//                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
//                }
//            }
//        }
//
//        //north west
//        if (frm.i > 0
//                && frm.j > 0) {
//
//            tempNavPixel = navPixels[frm.i - 1][frm.j - 1];
//            if (tempNavPixel.visitedTime > timer
//                    && !isInCollidable(tempNavPixel)) {
//
//                tempNavPixel.visitedTime = timer;
//
//                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));
//
//                if (navReturn.timer > tempNavReturn.timer
//                        && tempNavReturn.nextNav != null) {
//
//                    navReturn = tempNavReturn;
//                    navReturn.nextNav = tempNavPixel;
//
//                    if (stepCount < TRACE_LIM)
//                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
//                }
//            }
//        }
//
//        //south west
//        if (frm.i  < navPixelsRowCount  - 1
//                && frm.j > 0) {
//
//            tempNavPixel = navPixels[frm.i + 1][frm.j - 1];
//            if (tempNavPixel.visitedTime > timer
//                    && !isInCollidable(tempNavPixel)) {
//
//                tempNavPixel.visitedTime = timer;
//
//                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));
//
//                if (navReturn.timer > tempNavReturn.timer
//                        && tempNavReturn.nextNav != null) {
//
//                    navReturn = tempNavReturn;
//                    navReturn.nextNav = tempNavPixel;
//
//                    if (stepCount < TRACE_LIM)
//                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
//                }
//            }
//        }

        return navReturn;
    }

    private boolean isInCollidable(NavPixel navPixel) {
        Vector2i wSize = getWindow().getSize();
        // newX, newY + getHeight() / 6, getWidth() / 2, getHeight() / 4
        final int x  = navPixel.x,
                y = navPixel.y + frmHeight / 6,
                w = frmWidth / 2,
                h =  frmHeight / 4;

        final int left = x - w / 2,
                right = x + w / 2,
                top = y - h / 2,
                bottom = y + h / 2;

        //return false if outside of window
        if  (left < 0 || right < 0 || top > wSize.x || bottom > wSize.y)
            return true;

        Vector2i partSize = getPartSize();
        LevelPart partInside = getPart(navPixel.i / (navPixelsRowCount / 11), navPixel.j / (navPixelsRowCount / 11) );
        LevelPart part;

        for (int i = partInside.getRowNo(); i < 3 + partInside.getRowNo(); i++){
            for (int j = partInside.getColNo(); j < 3 + partInside.getColNo(); j++) {
                if (i < 11 && j < 11) {
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

                            return true;
                        }
                    }
                }
            }
        }

        return false;
        //return getPart(navPixel.i / (navPixelsRowCount / 11), navPixel.j / (navPixelsRowCount / 11) ).getType().equals("Wall");
    }

    private class NavPixel {
        int i, j;
        int x, y;
        double visitedTime;

        NavPixel(int i, int j) {
            this.i = i;
            this.j = j;
            this.x = j * (getWindow().getSize().x / navPixelsRowCount) + (getWindow().getSize().x / navPixelsRowCount) / 2;
            this.y = i * (getWindow().getSize().y / navPixelsRowCount) + (getWindow().getSize().y / navPixelsRowCount) / 2;
            this.visitedTime = Double.MAX_VALUE;
        }


    }

    private class NavReturn {
        int stepCount;
        double timer;
        NavPixel nextNav;
        ArrayList<Vector2i> navTrace = new ArrayList<>();

        NavReturn(int stepCount, double timer, NavPixel nextNav) {
            this.stepCount = stepCount;
            this.timer = timer;
            this.nextNav = nextNav;
        }

        void addNavPixel(NavPixel navPixel) {
            navTrace.add(new Vector2i(navPixel.x, navPixel.y));
        }

    }

//    public Vector2i navigateTo(Entity frm, Entity to) {
//        frmHeight = frm.getHeight();
//        frmWidth = frm.getWidth();
//
//        NavPixel navFrm = new NavPixel(0, 0),
//                navTo = new NavPixel(0, 0);
//
//        for (int i = 0; i < navPixels.length; i++) {
//            for (int j = 0; j < navPixels[0].length; j++) {
//                navPixels[i][j] = new NavPixel(i, j);
//
//                if (    (Math.sqrt(Math.pow(navPixels[i][j].x - to.getCenterX(), 2) + Math.pow(navPixels[i][j].y - to.getCenterY(), 2))
//                        < Math.sqrt(Math.pow(navTo.x - to.getCenterX(), 2) + Math.pow(navTo.y - to.getCenterY(), 2)))
//                        && !isInCollidable(navPixels[i][j])
//                        ) {
//                    navTo = navPixels[i][j];
//                }
//
//                if (    (Math.sqrt(Math.pow(navPixels[i][j].x - frm.getCenterX(), 2) + Math.pow(navPixels[i][j].y - frm.getCenterY(), 2))
//                        < Math.sqrt(Math.pow(navFrm.x - frm.getCenterX(), 2) + Math.pow(navFrm.y - frm.getCenterY(), 2)))
//                        && !isInCollidable(navPixels[i][j])
//                        ) {
//                    navFrm = navPixels[i][j];
//                }
//            }
//        }
//
//        NavReturn[] navTrace = new NavReturn[TRACE_LIM];
//
//        for (int i = 0; i < navTrace.length; i++)
//            navTrace[i] = null;
//
//        NavReturn navReturn = navigateTo(navFrm, navTo, 0, 0);
//        NavPixel navPixel = navReturn.nextNav;
//
//        if (navPixel == null)
//            return new Vector2i(0, 0);
//
//        //TODO do not remove
//        for (int i = 0; i < navReturn.navTrace.length; i++)  {
//            if (navReturn.navTrace[i] != null)
//                navPixel = navReturn.navTrace[i];
//        }
//
//        return new Vector2i((navPixel.x - frm.getCenterX()) ,
//                (navPixel.y - frm.getCenterY()));
//    }
//
//
//
//
//    public NavReturn navigateTo(NavPixel frm, NavPixel to, int stepCount, double timer) {
//        if (frm.i == to.i && frm.j == to.j) {
//            return new NavReturn(stepCount, timer, to);
//        }
//
//        stepCount++;
//
//        NavReturn navReturn = new NavReturn(Integer.MAX_VALUE, Double.MAX_VALUE, null),
//                tempNavReturn;
//        NavPixel tempNavPixel;
//
//        //north
//        if (frm.i > 0) {
//            tempNavPixel = navPixels[frm.i - 1][frm.j];
//            if (tempNavPixel.visitedTime > timer
//                    && !isInCollidable(tempNavPixel)) {
//
//                tempNavPixel.visitedTime = timer;
//
//                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer +1);
//
//                if (navReturn.timer > tempNavReturn.timer
//                        && tempNavReturn.nextNav != null) {
//
//                    navReturn = tempNavReturn;
//                    navReturn.nextNav = tempNavPixel;
//
//                    if (stepCount < TRACE_LIM)
//                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
//                }
//            }
//        }
//
//        //south
//        if (frm.i  < navPixelsRowCount  - 1) {
//            tempNavPixel = navPixels[frm.i + 1][frm.j];
//            if (tempNavPixel.visitedTime > timer
//                    && !isInCollidable(tempNavPixel)) {
//
//                tempNavPixel.visitedTime = timer;
//
//                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);
//
//                if (navReturn.timer > tempNavReturn.timer
//                        && tempNavReturn.nextNav != null) {
//
//                    navReturn = tempNavReturn;
//                    navReturn.nextNav = tempNavPixel;
//
//                    if (stepCount < TRACE_LIM)
//                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
//                }
//            }
//        }
//
//        //west
//        if (frm.j > 0) {
//            tempNavPixel = navPixels[frm.i][frm.j - 1];
//            if (tempNavPixel.visitedTime > timer
//                    && !isInCollidable(tempNavPixel)) {
//
//                tempNavPixel.visitedTime = timer;
//
//                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);
//
//                if (navReturn.timer > tempNavReturn.timer
//                        && tempNavReturn.nextNav != null) {
//
//                    navReturn = tempNavReturn;
//                    navReturn.nextNav = tempNavPixel;
//
//                    if (stepCount < TRACE_LIM)
//                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
//                }
//            }
//        }
//
//        //east
//        if (frm.j < navPixelsRowCount - 1) {
//            tempNavPixel = navPixels[frm.i][frm.j + 1];
//            if (tempNavPixel.visitedTime > timer
//                    && !isInCollidable(tempNavPixel)) {
//
//                tempNavPixel.visitedTime = timer;
//
//                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);
//
//                if (navReturn.timer > tempNavReturn.timer
//                        && tempNavReturn.nextNav != null) {
//
//                    navReturn = tempNavReturn;
//                    navReturn.nextNav = tempNavPixel;
//
//                    if (stepCount < TRACE_LIM)
//                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
//                }
//            }
//        }
//
////        //north east
////        if (frm.i > 0
////                && frm.j < navPixelsRowCount - 1) {
////
////            tempNavPixel = navPixels[frm.i - 1][frm.j + 1];
////            if (tempNavPixel.visitedTime > timer
////                    && !isInCollidable(tempNavPixel)) {
////
////                tempNavPixel.visitedTime = timer;
////
////                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));
////
////                if (navReturn.timer > tempNavReturn.timer
////                        && tempNavReturn.nextNav != null) {
////
////                    navReturn = tempNavReturn;
////                    navReturn.nextNav = tempNavPixel;
////
////                    if (stepCount < TRACE_LIM)
////                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
////                }
////            }
////        }
////
////        // south east
////        if (frm.i  < navPixelsRowCount  - 1
////                && frm.j < navPixelsRowCount - 1) {
////
////            tempNavPixel = navPixels[frm.i + 1][frm.j + 1];
////            if (tempNavPixel.visitedTime > timer
////                    && !isInCollidable(tempNavPixel)) {
////
////                tempNavPixel.visitedTime = timer;
////
////                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));
////
////                if (navReturn.timer > tempNavReturn.timer
////                        && tempNavReturn.nextNav != null) {
////
////                    navReturn = tempNavReturn;
////                    navReturn.nextNav = tempNavPixel;
////
////                    if (stepCount < TRACE_LIM)
////                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
////                }
////            }
////        }
////
////        //north west
////        if (frm.i > 0
////                && frm.j > 0) {
////
////            tempNavPixel = navPixels[frm.i - 1][frm.j - 1];
////            if (tempNavPixel.visitedTime > timer
////                    && !isInCollidable(tempNavPixel)) {
////
////                tempNavPixel.visitedTime = timer;
////
////                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));
////
////                if (navReturn.timer > tempNavReturn.timer
////                        && tempNavReturn.nextNav != null) {
////
////                    navReturn = tempNavReturn;
////                    navReturn.nextNav = tempNavPixel;
////
////                    if (stepCount < TRACE_LIM)
////                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
////                }
////            }
////        }
////
////        //south west
////        if (frm.i  < navPixelsRowCount  - 1
////                && frm.j > 0) {
////
////            tempNavPixel = navPixels[frm.i + 1][frm.j - 1];
////            if (tempNavPixel.visitedTime > timer
////                    && !isInCollidable(tempNavPixel)) {
////
////                tempNavPixel.visitedTime = timer;
////
////                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));
////
////                if (navReturn.timer > tempNavReturn.timer
////                        && tempNavReturn.nextNav != null) {
////
////                    navReturn = tempNavReturn;
////                    navReturn.nextNav = tempNavPixel;
////
////                    if (stepCount < TRACE_LIM)
////                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
////                }
////            }
////        }
//
//        return navReturn;
//    }
//
//    private boolean isInCollidable(NavPixel navPixel) {
//        Vector2i wSize = getWindow().getSize();
//       // newX, newY + getHeight() / 6, getWidth() / 2, getHeight() / 4
//        final int x  = navPixel.x,
//                y = navPixel.y + frmHeight / 6,
//                w = frmWidth / 2,
//                h =  frmHeight / 4;
//
//        final int left = x - w / 2,
//                right = x + w / 2,
//                top = y - h / 2,
//                bottom = y + h / 2;
//
//        //return false if outside of window
//        if  (left < 0 || right < 0 || top > wSize.x || bottom > wSize.y)
//            return true;
//
//        Vector2i partSize = getPartSize();
//        LevelPart partInside = getPart(navPixel.i / (navPixelsRowCount / 11), navPixel.j / (navPixelsRowCount / 11) );
//        LevelPart part;
//
//        for (int i = partInside.getRowNo(); i < 3 + partInside.getRowNo(); i++){
//            for (int j = partInside.getColNo(); j < 3 + partInside.getColNo(); j++) {
//                if (i < 11 && j < 11) {
//                    part = getPart(i, j);
//
//                    if (part.getType().equals("Wall")) {
//                        final int partLeft = j * partSize.x,
//                                partRight = (j + 1) * partSize.x,
//                                partBottom = (i + 1) * partSize.y,
//                                partTop = i * partSize.y;
//
//                        //col * w + w/2, row * h + h/2
//
//                        if (left < partRight
//                                && right > partLeft
//                                && bottom > partTop
//                                && top < partBottom) {
//
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
//
//        return false;
//        //return getPart(navPixel.i / (navPixelsRowCount / 11), navPixel.j / (navPixelsRowCount / 11) ).getType().equals("Wall");
//    }
//
//    private class NavPixel {
//        int i, j;
//        int x, y;
//        double visitedTime;
//
//        NavPixel(int i, int j) {
//            this.i = i;
//            this.j = j;
//            this.x = j * (getWindow().getSize().x / navPixelsRowCount) + (getWindow().getSize().x / navPixelsRowCount) / 2;
//            this.y = i * (getWindow().getSize().y / navPixelsRowCount) + (getWindow().getSize().y / navPixelsRowCount) / 2;
//            this.visitedTime = Double.MAX_VALUE;
//        }
//
//        public Vector2i getLevelPartXY() {
//            return new Vector2i( i / navPixelsRowCount, j / navPixelsRowCount );
//        }
//    }
//
//    private class NavReturn {
//        int stepCount;
//        double timer;
//        NavPixel nextNav;
//        NavPixel[] navTrace = new NavPixel[TRACE_LIM];
//
//        NavReturn(int stepCount, double timer, NavPixel nextNav) {
//            this.stepCount = stepCount;
//            this.timer = timer;
//            this.nextNav = nextNav;
//        }
//    }
//


    //==================================================================================================================



/* //TODO do not remove
    public NavReturn navigateTo(NavPixel frm, NavPixel to, int stepCount, double timer) {
        if (frm.i == to.i && frm.j == to.j) {

            return new NavReturn(stepCount, timer, to);
        }

        stepCount++;

        NavReturn navReturn = new NavReturn(Integer.MAX_VALUE, Double.MAX_VALUE, null),
                tempNavReturn;
        NavPixel tempNavPixel;

        if (lastMoveDirection == DIAGONAL) {
            navReturn = doStraight(navReturn, null, frm, to, stepCount, timer);

            tempNavReturn = doDiagonals(navReturn, null, frm, to, stepCount, timer);

            if (navReturn.timer > tempNavReturn.timer
                    && tempNavReturn.nextNav != null) {

                navReturn = tempNavReturn;
                //navReturn.nextNav = tempNavPixel;

                if (stepCount < TRACE_LIM)
                    navReturn.navTrace[stepCount-1] = navReturn.nextNav;
            }
            lastMoveDirection = STRAIGHT;
        } else {
            navReturn = doDiagonals(navReturn, null, frm, to, stepCount, timer);

            tempNavReturn = doStraight(navReturn, null, frm, to, stepCount, timer);

            if (navReturn.timer > tempNavReturn.timer
                    && tempNavReturn.nextNav != null) {

                navReturn = tempNavReturn;
                //navReturn.nextNav = tempNavPixel;

                if (stepCount < TRACE_LIM)
                    navReturn.navTrace[stepCount-1] = navReturn.nextNav;
            }
           // doStraight(navReturn, null, frm, to, stepCount, timer);
            lastMoveDirection = DIAGONAL;
        }


        return navReturn;
    }

    private NavReturn doStraight(NavReturn navReturn, NavPixel tempNavPixel, NavPixel frm, NavPixel to, int stepCount, double timer) {
        NavReturn tempNavReturn;

        //north
        if (frm.i > 0) {
            tempNavPixel = navPixels[frm.i - 1][frm.j];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer +1);

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    if (stepCount < TRACE_LIM)
                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
                }
            }
        }

        //south
        if (frm.i  < navPixelsRowCount  - 1) {
            tempNavPixel = navPixels[frm.i + 1][frm.j];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    if (stepCount < TRACE_LIM)
                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
                }
            }
        }

        //west
        if (frm.j > 0) {
            tempNavPixel = navPixels[frm.i][frm.j - 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    if (stepCount < TRACE_LIM)
                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
                }
            }
        }

        //east
        if (frm.j < navPixelsRowCount - 1) {
            tempNavPixel = navPixels[frm.i][frm.j + 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + 1);

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    if (stepCount < TRACE_LIM)
                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
                }
            }
        }

        return navReturn;
    }

    private NavReturn doDiagonals(NavReturn navReturn, NavPixel tempNavPixel, NavPixel frm, NavPixel to, int stepCount, double timer) {
        NavReturn tempNavReturn;

        //north east
        if (frm.i > 0
                && frm.j < navPixelsRowCount - 1) {

            tempNavPixel = navPixels[frm.i - 1][frm.j + 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    if (stepCount < TRACE_LIM)
                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
                }
            }
        }

        // south east
        if (frm.i  < navPixelsRowCount  - 1
                && frm.j < navPixelsRowCount - 1) {

            tempNavPixel = navPixels[frm.i + 1][frm.j + 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    if (stepCount < TRACE_LIM)
                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
                }
            }
        }

        //north west
        if (frm.i > 0
                && frm.j > 0) {

            tempNavPixel = navPixels[frm.i - 1][frm.j - 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    if (stepCount < TRACE_LIM)
                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
                }
            }
        }

        //south west
        if (frm.i  < navPixelsRowCount  - 1
                && frm.j > 0) {

            tempNavPixel = navPixels[frm.i + 1][frm.j - 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;

                tempNavReturn = navigateTo(tempNavPixel, to, stepCount, timer + Math.sqrt(2));

                if (navReturn.timer > tempNavReturn.timer
                        && tempNavReturn.nextNav != null) {

                    navReturn = tempNavReturn;
                    navReturn.nextNav = tempNavPixel;

                    if (stepCount < TRACE_LIM)
                        navReturn.navTrace[stepCount-1] = navReturn.nextNav;
                }
            }
        }

        return navReturn;
    }
*/


}
