/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup_originals;

import game.DebugPrinter;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;

/**
 *
 * @author newby
 */
public class Bubble extends Actor {
    private int radius;

    public Bubble(int x, int y, int radius, Color c, int transparency) {
        CircleShape circle = new CircleShape(radius);
        circle.setFillColor(new Color(c, transparency));
        circle.setOrigin(radius, radius);

        this.x = x;
        this.y = y;
        this.radius = radius;

        obj = circle;
    }

    // Default method typically assumes a rectangle,
    // so do something a little different
    @Override
    public boolean within (int px, int py) {

        // In this example, For simplicty...
        // - We just treat circle as a box
        // - x, y is the top corner rather than the centre
        // - We use points x,y and px, py not bounding boxes
        if (px > x && px < x + 2 * radius &&
                py > y && py < y + 2 * radius) {
            return true;
        }
        else {
            return false;
        }
    }

    void clicked(){
        DebugPrinter.debugPrint(this, "Clicked Node");
    }
}
	
