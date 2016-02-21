package tools;

import abstract_classes.Entity;
import game.LevelPart;
import game.Room;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by millsaj on 21/02/2016.
 */
public class Navigator {
    private static int navPixelsRowCount = 11 * 2;
    private NavPixel[][] navPixels = null;
    private static final int TRACE_LIM = 0;
    private static final int DIAGONAL = 1, STRAIGHT = 0;  //todo do not remove
    private int lastMoveDirection = 5;  //todo do not remove
    private int frmWidth = 64,
            frmHeight = 128;
   // private int toX, toY;
    private Room room;

    public Navigator(Room room) {
        super();

        this.room = room;
    }

    private void populateNavPixels() {
        navPixels = new NavPixel[navPixelsRowCount][navPixelsRowCount];

        for (int i = 0; i < navPixels.length; i++) {
            for (int j = 0; j < navPixels[0].length; j++) {
                navPixels[i][j] = new NavPixel(i, j);
            }
        }
    }

    private NavPixel[] getNavPixels(Vector2i entity1, Vector2i entity2, boolean checkForCollisions) {
        if (navPixels == null) {
            throw new RuntimeException("Must set navPixels with populateNavPixels");
        }

        NavPixel[] navs = {new NavPixel(0, 0),
                new NavPixel(0, 0)};

        for (NavPixel[] navPixel : navPixels) {
            for (int j = 0; j < navPixels[0].length; j++) {
                if (entity1 != null) {
                    if ((Math.sqrt(Math.pow(navPixel[j].x - entity1.x, 2) + Math.pow(navPixel[j].y - entity1.y, 2))
                            < Math.sqrt(Math.pow(navs[0].x - entity1.x, 2) + Math.pow(navs[0].y - entity1.y, 2)))
                            && (!checkForCollisions || !isInCollidable(navPixel[j]))
                            ) {
                        navs[0] = navPixel[j];
                    }
                }

                if (entity2 != null) {
                    if ((Math.sqrt(Math.pow(navPixel[j].x - entity2.x, 2) + Math.pow(navPixel[j].y - entity2.y, 2))
                            < Math.sqrt(Math.pow(navs[1].x - entity2.x, 2) + Math.pow(navs[1].y - entity2.y, 2)))
                            && (!checkForCollisions || !isInCollidable(navPixel[j]))
                            ) {
                        navs[1] = navPixel[j];
                    }
                }
            }
        }

        return navs;
    }

    public ArrayList<Vector2i> navigateDistanceAway(Entity frm, Entity flee, int distance, boolean lineOfSight) {
        frmHeight = frm.getHeight();
        frmWidth = frm.getWidth();

        populateNavPixels();
        NavPixel[] tempToFrm = getNavPixels(new Vector2i(frm.getCenterX(), frm.getCenterY()),
                new Vector2i(flee.getCenterX(), flee.getCenterY()), true);

        final NavPixel navFrm = tempToFrm[0],
                navFlee = tempToFrm[1];

        Predicate<NavPixel> navPixelPredicate;
        if (lineOfSight) {
            navPixelPredicate = (NavPixel currPixel) ->
                    Math.sqrt(Math.pow(currPixel.x - navFlee.x, 2) + Math.pow(currPixel.y - navFlee.y, 2)) >= distance
                            && inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navFlee.x, navFlee.y),
                            new Vector2f((navFlee.x - currPixel.x) / 30, (navFlee.y - currPixel.y) / 30));
        } else {
            navPixelPredicate = (NavPixel currPixel) ->
                    Math.sqrt(Math.pow(currPixel.x - navFlee.x, 2) + Math.pow(currPixel.y - navFlee.y, 2)) >= distance;
        }


        NavReturn navReturn = navigateTo(navFrm, navPixelPredicate, 0, 0);
//
        if (lineOfSight && navReturn.navTrace.size() == 0) {
            navPixelPredicate = (NavPixel currPixel) ->
                    inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navFlee.x, navFlee.y),
                            new Vector2f((navFlee.x - currPixel.x) / 30, (navFlee.y - currPixel.y) / 30));
            navReturn = navigateTo(navFrm, navPixelPredicate, 0, 0);
        }

        navPixels = null;
        return navReturn.navTrace;
    }


    public ArrayList<Vector2i> navigateDistanceTo(Entity frm, Entity to, int distance, boolean lineOfSight) {
        frmHeight = frm.getHeight();
        frmWidth = frm.getWidth();

        populateNavPixels();
        NavPixel[] tempToFrm = getNavPixels(new Vector2i(frm.getCenterX(), frm.getCenterY()),
                new Vector2i(to.getCenterX(), to.getCenterY()), true);

        final NavPixel navFrm = tempToFrm[0],
                navTo = tempToFrm[1];

        Predicate<NavPixel> navPixelPredicate;
        if (lineOfSight) {
            navPixelPredicate = (NavPixel currPixel) ->
                    Math.sqrt(Math.pow(currPixel.x - navTo.x, 2) + Math.pow(currPixel.y - navTo.y, 2)) <= distance
                            && inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navTo.x, navTo.y),
                            new Vector2f((navTo.x - currPixel.x) / 30, (navTo.y - currPixel.y) / 30));
        } else {
            navPixelPredicate = (NavPixel currPixel) ->
                    Math.sqrt(Math.pow(currPixel.x - navTo.x, 2) + Math.pow(currPixel.y - navTo.y, 2)) <= distance;
        }

        NavReturn navReturn = navigateTo(navFrm, navPixelPredicate, 0, 0);

        if (lineOfSight && navReturn.navTrace.size() == 0) {
            navPixelPredicate = (NavPixel currPixel) ->
                    inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navTo.x, navTo.y),
                            new Vector2f((navTo.x - currPixel.x) / 30, (navTo.y - currPixel.y) / 30));
            navReturn = navigateTo(navFrm, navPixelPredicate, 0, 0);
        }

        navPixels = null;
        return navReturn.navTrace;
    }

//    public ArrayList<Vector2i> navigateTo(Entity frm, Entity to) {
//        frmHeight = frm.getHeight();
//        frmWidth = frm.getWidth();
//
//        NavPixel[] tempToFrm = getNavPixels(frm, to);
//
//        final NavPixel navFrm = tempToFrm[0],
//                navTo = tempToFrm[1];
//
//        NavReturn navReturn = navigateTo(navFrm,
//                (NavPixel frmPixel) -> frmPixel.i == navTo.i && frmPixel.j == navTo.j,
//                0,
//                0);
//
//        return navReturn.navTrace;
//    }
//



    public NavReturn navigateTo(NavPixel frm, Predicate<NavPixel> to, int stepCount, double timer) {
        if (to.test(frm)) {
            return new NavReturn(stepCount, timer, frm);
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

        //north east
        if (frm.i > 0
                && frm.j < navPixelsRowCount - 1) {

            tempNavPixel = navPixels[frm.i - 1][frm.j + 1];
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
        } //

        // south east
        if (frm.i  < navPixelsRowCount  - 1
                && frm.j < navPixelsRowCount - 1) {

            tempNavPixel = navPixels[frm.i + 1][frm.j + 1];
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

        //north west
        if (frm.i > 0
                && frm.j > 0) {

            tempNavPixel = navPixels[frm.i - 1][frm.j - 1];
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

        //south west
        if (frm.i  < navPixelsRowCount  - 1
                && frm.j > 0) {

            tempNavPixel = navPixels[frm.i + 1][frm.j - 1];
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

        return navReturn;
    }

    private boolean inLineOfSight(Vector2f frm, Vector2f to, Vector2f splitDist) {
        if (isInCollidable(getNavPixels(new Vector2i((int)frm.x, (int)frm.y), null, false)[0])) {
            return false;
        }

        if (Math.abs(frm.x - to.x) < 30 && Math.abs(frm.y - to.y) < 30 ) {
            return true;
        }

        return inLineOfSight(new Vector2f(frm.x + splitDist.x, frm.y + splitDist.y), to, splitDist);
    }

    private boolean isInCollidable(NavPixel navPixel) {
        Vector2i wSize = room.getWindow().getSize();
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

        Vector2i partSize = room.getPartSize();
        LevelPart partInside = room.getPart(navPixel.i / (navPixelsRowCount / 11), navPixel.j / (navPixelsRowCount / 11));
        LevelPart part;

        for (int i = partInside.getRowNo(); i < 3 + partInside.getRowNo(); i++){
            for (int j = partInside.getColNo(); j < 3 + partInside.getColNo(); j++) {
                if (i < 11 && j < 11) {
                    part = room.getPart(i, j);

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
            this.x = j * (room.getWindow().getSize().x / navPixelsRowCount) + (room.getWindow().getSize().x / navPixelsRowCount) / 2;
            this.y = i * (room.getWindow().getSize().y / navPixelsRowCount) + (room.getWindow().getSize().y / navPixelsRowCount) / 2;
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


