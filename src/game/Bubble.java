/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

/**
 *
 * @author newby
 */
public class Bubble extends Entity implements CollidingEntity {

    public Bubble(RenderWindow w, String name, int x, int y, int radius, Color fillC, Color lineC, float pt, int transparency) {
        super(w, name);

        CircleShape circle = new CircleShape(radius);
        circle.setFillColor(new Color(fillC, transparency));
        circle.setOutlineColor(lineC);
        circle.setOutlineThickness(pt);
        circle.setOrigin(radius, radius);

        super.setWidthHeight(radius * 2, radius * 2);
        super.setCenterX(x);
        super.setCenterY(y);

        super.addTransformable(circle, 0, 0, radius, radius);
    }

    // Default method typically assumes a rectangle,
    // so do something a little different
    @Override
    public boolean colliding(int px, int py) {

        // In this example, For simplicty...
        // - We just treat circle as a box
        // - x, y is the top corner rather than the centre
        // - We use points x,y and px, py not bounding boxes
        return (px > getTopLeftX() && px < getTopLeftX() + 2 * getWidth() && py > getTopLeftY() && py < getTopLeftY() + 2 * getWidth());

    }

    @Override
    public boolean colliding(Event e) {
        return false;
    }

    void clicked(){
        DebugPrinter.debugPrint(this, "Clicked Node");
    }
}
	
