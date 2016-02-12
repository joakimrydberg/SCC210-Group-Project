/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author newby
 */
public class Button extends Rect implements CollidingEntity {
    private Entity menu;

    private static String FontFile  = "LucidaSansRegular.ttf";
    private String FontPath; // Where fonts were found

    private int i = 0;

    public Button(RenderWindow w, int x, int y, int width, int height, Color c, int transparency, String text, int size) {
        super(w, text, x, y, width, height, c, transparency);

        if ((new File(Constants.JRE_FONT_PATH)).exists( ))
            FontPath = Constants.JRE_FONT_PATH;
        else
            FontPath = Constants.JDK_FONT_PATH;


        Font sansRegular = new Font( );
        try {
            sansRegular.loadFromFile(
                    Paths.get(FontPath+FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        Text textObj = new Text (text, sansRegular, size);
        textObj.setColor(Color.BLACK);
        //text.setStyle(Text.BOLD | Text.UNDERLINED);

        final FloatRect textBounds = textObj.getLocalBounds();

        addTransformable(textObj, width / 2, height / 2, (int)textBounds.width, (int)textBounds.height);
    }

    @Override
    public boolean colliding(Event e){
        Vector2i v = e.asMouseButtonEvent().position;
        return colliding(v.x, v.y);
    }

    @Override
    public boolean colliding(int x, int y) {
        return ( getTopLeftX() < x && x < getTopLeftX() + (getHeight()) && getTopLeftY() < y && y < getTopLeftY()+(getWidth())); //TODO NEEDSS MASSIVE WORK
    }

    public void clicked(MainMenu mainMenu, CharMenu charMenu, MapMenu mapMenu){

        if(getName().equals("NEW GAME")){
             mainMenu.unload();
            charMenu.load();
            System.out.println("NEW GAME clicked");
        }
        else if(getName().equals("CREATE")){
            charMenu.unload(); mapMenu.load(); System.out.println("Create clicked");
        }
        else if(getName().equals(">>")){
            System.out.format("%d", this.i);
            if(this.i < 2){
                this.i++;
                charMenu.moveRight(i);
            }
        }
        else if(getName().equals("<<")){
            System.out.format("%d", this.i);
            if(this.i > 0){
                this.i--;
                charMenu.moveLeft(i);
            }
        }
        else if(getName().equals("BACK")){
            mapMenu.unload(); charMenu.load(); System.out.println("Create clicked");
        }
    }

    public void clicked(Vector2i v){

    }
}
