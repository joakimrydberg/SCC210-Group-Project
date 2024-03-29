package tools;

import abstract_classes.Entity;
import game.LevelPart;
import game.Room;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by millsaj on 21/02/2016.
 */
public class Navigator {
    private static int navPixelsRowCount = 11 * 2;
    private NavPixel[][] navPixels = null;
    private static final int TRACE_LIM = 0;
    private static final int DIAGONAL = 1, STRAIGHT = 0;  //todo do not remove | edit: can probably remove
    private int lastMoveDirection = 5;  //todo do not remove | edit: can probably remove
    private int frmWidth = 64,
            frmHeight = 128;
   // private int toX, toY;
    private Room room;

    public Navigator(Room room) {
        super();
        //populateNavPixels();
        this.room = room;
    }

    public void populateNavPixels() {
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

    //todo do not remove, experimental
//    private void updateCurrPos(ArrayList<Vector2i> newPath) {
//        if (newPath != null) {
//            for (int i = 0; i < newPath.size(); i++) {
//                Vector2i frm = newPath.get(i);
//
//                for (int j = newPath.size() - 1; j > i + 1; j--) {
//                    Vector2i to = newPath.get(j);
//                    if (inLineOfSight(new Vector2f(frm.x, frm.y),
//                            new Vector2f(to.x, to.y))) {
//
//                        for (int k = i + 1; k < j; k++) {
//                            newPath.remove(newPath.get(i+1));
//                        }
//                        break;
//                    }
//                }
//            }
//        }
//    }

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
                            && inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navFlee.x, navFlee.y));
        } else {
            navPixelPredicate = (NavPixel currPixel) ->
                    Math.sqrt(Math.pow(currPixel.x - navFlee.x, 2) + Math.pow(currPixel.y - navFlee.y, 2)) >= distance;
        }


        NavReturn navReturn = navigateTo(navFrm, navPixelPredicate, 0, 0);
//
        if (lineOfSight && navReturn.navTrace.size() == 0) {
            navPixelPredicate = (NavPixel currPixel) ->
                    inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navFlee.x, navFlee.y));
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
                            && inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navTo.x, navTo.y));
        } else {
            navPixelPredicate = (NavPixel currPixel) ->
                    Math.sqrt(Math.pow(currPixel.x - navTo.x, 2) + Math.pow(currPixel.y - navTo.y, 2)) <= distance
                            || inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navTo.x, navTo.y));  //only for navigateTo
        }

        NavReturn navReturn = navigateTo(navFrm, navPixelPredicate, 0, 0);

        if (lineOfSight && navReturn.navTrace.size() == 0) {
            navPixelPredicate = (NavPixel currPixel) ->
                    inLineOfSight(new Vector2f(currPixel.x, currPixel.y), new Vector2f(navTo.x, navTo.y));
            navReturn = navigateTo(navFrm, navPixelPredicate, 0, 0);
        }

        navReturn.navTrace.add(0, new Vector2i(to.getCenterX(), to.getCenterY())); //only for navigateTo

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

    public NavReturn navigateTo(NavPixel frm, Predicate<NavPixel> cond, int stepCount, double timer) {
        if (cond.test(frm)) {
            return new NavReturn(stepCount, timer, frm);
        }

        stepCount++;

        NavReturn navReturn = new NavReturn(Integer.MAX_VALUE, Double.MAX_VALUE, null),
                tempNavReturn;
        NavPixel tempNavPixel;
        double timerAddition = 1;
//        if (inLineOfSight(new Vector2f(frm.x, frm.y), new Vector2f(to.x, to.y)))
//

        //north
        if (frm.i > 0) {
            tempNavPixel = navPixels[frm.i - 1][frm.j];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;
                tempNavReturn = navigateTo(tempNavPixel, cond, stepCount, timer + timerAddition);

                if (navReturn.timer > tempNavReturn.timer) {

                    navReturn = tempNavReturn;
                    navReturn.addNavPixel(tempNavPixel);
                }
            }
        }

        //south
        if (frm.i  < navPixelsRowCount  - 1) {
            tempNavPixel = navPixels[frm.i + 1][frm.j];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;
                tempNavReturn = navigateTo(tempNavPixel, cond, stepCount, timer + timerAddition);

                if (navReturn.timer > tempNavReturn.timer) {

                    navReturn = tempNavReturn;
                    navReturn.addNavPixel(tempNavPixel);
                }
            }
        }

        //west
        if (frm.j > 0) {
            tempNavPixel = navPixels[frm.i][frm.j - 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;
                tempNavReturn = navigateTo(tempNavPixel, cond, stepCount, timer + timerAddition);

                if (navReturn.timer > tempNavReturn.timer) {

                    navReturn = tempNavReturn;
                    navReturn.addNavPixel(tempNavPixel);
                }
            }
        }

        //east
        if (frm.j < navPixelsRowCount - 1) {
            tempNavPixel = navPixels[frm.i][frm.j + 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;
                tempNavReturn = navigateTo(tempNavPixel, cond, stepCount, timer + timerAddition);

                if (navReturn.timer > tempNavReturn.timer) {

                    navReturn = tempNavReturn;
                    navReturn.addNavPixel(tempNavPixel);
                }
            }
        }


        //================================================================================

        timerAddition = Math.sqrt(2);

        //==================================================================================


        //north east
        if (frm.i > 0
                && frm.j < navPixelsRowCount - 1) {

            tempNavPixel = navPixels[frm.i - 1][frm.j + 1];
            if (tempNavPixel.visitedTime > timer
                    && !isInCollidable(tempNavPixel)) {

                tempNavPixel.visitedTime = timer;
                tempNavReturn = navigateTo(tempNavPixel, cond, stepCount, timer + timerAddition);

                if (navReturn.timer > tempNavReturn.timer) {

                    navReturn = tempNavReturn;
                    navReturn.addNavPixel(tempNavPixel);
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
                tempNavReturn = navigateTo(tempNavPixel, cond, stepCount, timer + timerAddition);

                if (navReturn.timer > tempNavReturn.timer) {

                    navReturn = tempNavReturn;
                    navReturn.addNavPixel(tempNavPixel);
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
                tempNavReturn = navigateTo(tempNavPixel, cond, stepCount, timer + timerAddition);

                if (navReturn.timer > tempNavReturn.timer) {

                    navReturn = tempNavReturn;
                    navReturn.addNavPixel(tempNavPixel);
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
                tempNavReturn = navigateTo(tempNavPixel, cond, stepCount, timer + timerAddition);

                if (navReturn.timer > tempNavReturn.timer) {

                    navReturn = tempNavReturn;
                    navReturn.addNavPixel(tempNavPixel);
                }
            }
        }

        return navReturn;
    }

    public boolean inLineOfSight(Vector2f frm, Vector2f to) {
        return inLineOfSight(frm, to, null);
    }

    public boolean inLineOfSight(Vector2f frm, Vector2f to, Consumer<Vector2f> consumer) {
        if (isInCollidable(getNavPixels(new Vector2i((int)frm.x, (int)frm.y), null, false)[0])) {
            return false;
        }

        if (Math.abs(frm.x - to.x) < 30 && Math.abs(frm.y - to.y) < 30 ) {
            return true;
        }

        if (consumer != null) {
            consumer.accept(frm);
        }

        return inLineOfSight(new Vector2f(frm.x + ((to.x - frm.x) / 30), frm.y + ((to.y - frm.y) / 30)), to, consumer);
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
                    //part.setType();

                    if (part.getType().equals("Wall") || part.getType().equals("Blank")) {
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
        ArrayList<Vector2i> navTrace = new ArrayList<>();

        NavReturn(int stepCount, double timer, NavPixel nextNav) {
            this.stepCount = stepCount;
            this.timer = timer;

        }

        void addNavPixel(NavPixel navPixel) {
            navTrace.add(new Vector2i(navPixel.x, navPixel.y));
        }

    }

}


