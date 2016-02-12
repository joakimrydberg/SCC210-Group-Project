/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup_originals;

/**
 *
 * @author newby
 */

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;

import java.util.ArrayList;

public abstract class Actor {
    private ArrayList<Actor> actors = new ArrayList<Actor>( );

    Transformable obj;   // Base object

    int x  = 0;	// Current X-coordinate
    int y  = 0;	// Current Y-coordinate

    int r  = 0;	// Change in rotation per cycle
    int dx = 0;	// Change in X-coordinate per cycle
    int dy = 0;	// Change in Y-coordinate per cycle

    //
    // Is point x, y within area occupied by this object?
    //
    // This should really be done with bounding boxes not points
    // ...we should check whether any point in bounding box
    //    is covered by this object
    //
    boolean within (int x, int y) {
        // Should check object bounds here
        // -- we'd normally assume a simple rectangle
        //    ...and override as necessary
        return false;
    }

    boolean newWithin(org.jsfml.window.event.Event event){
        return false;
    }

    void clicked(){

    }

    void clicked(MainMenu mainMenu, CharMenu charMenu, MapMenu mapMenu){

    }

    void clicked(Actor menu){

    }

    boolean mouseOn () {
        return false;
    }

    //
    // Work out where object should be for next frame
    //
    void calcMove(int minx, int miny, int maxx, int maxy) {
        // Add deltas to x and y position
        x += dx;
        y += dy;

        // Check we've not hit screen bounds
        // x and y would normally be the object's centre point
        // rather than its top corner so we can more easily
        if (x <= minx || x >= maxx) { dx *= -1; x += dx; }
        if (y <= miny || y >= maxy) { dy *= -1; y += dy; }

        // Check we've not collided with any other actor
        for (Actor a : actors) {
            if (a.obj != obj && a.within(x, y)) {
                dx *= -1; x += dx;
                dy *= -1; y += dy;
            }
        }
    }

    // Rotate and reposition the object
    void performMove( ) {
        obj.rotate(r);
        obj.setPosition(x,y);
    }

    //
    // Render the object at its new position
    //
    void draw(RenderWindow w) {
        w.draw((Drawable)obj);
    }

    void load(){

    }

    void unload(){

    }

    void moveRight(int i){

    }

    void moveLeft(int i){

    }

}

