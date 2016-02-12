/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;

/**
 *
 * @author ryan
 */
public class MainMenu extends EntityHolder {

    public int loaded = 0;

    public MainMenu(RenderWindow window) {
        super(window, "Main Menu");

        final Vector2i windowSize = window.getSize();
        final int centerX = windowSize.y / 2, centerY = windowSize.x / 2;

        addEntity(new Rect(window, null, 0, 0, windowSize.x, windowSize.y, Color.BLACK, 128));
        addEntity(new Button(window, centerX, centerY, 250, 100, Color.WHITE, 100, "NEW GAME", 22));
    }

    @Override
    public void draw() {
        super.draw();

        ArrayList<Entity> entities = getEntities();
        for (Entity a : entities) {
            if(loaded==1){
                a.draw();
            }
        }
    }

   /*

   void performMove(){
        for(MovingEntity a : act){
            a.move();
        }
    }*/

    public void load(){
        loaded++;
    }

    public void unload(){
        loaded = 0;
    }

}
