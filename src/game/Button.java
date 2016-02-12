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
    private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
    private Entity menu;

    private static String FontFile  = "LucidaSansRegular.ttf";
    private String FontPath; // Where fonts were found

    private int i = 0;

    public Button(RenderWindow w, int x, int y, int height, int width, Color c, int transparency, String text, int size) {
        super(w, text, x, y, height, width, c, transparency);

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

        FloatRect textBounds = textObj.getLocalBounds( );
        // Find middle and set as origin/ reference point
        textObj.setOrigin(textBounds.width / 2,
                textBounds.height / 2);

        super.setTransformable(textObj);
        objs.add(super.getTransformable());
    }

    void draw(RenderWindow w) {
        for (Transformable tra : objs) {
            w.draw((Drawable)tra);
        }
    }

/*  Needed?
    void performMove( ) {
        for (Transformable tra : objs) {
            if(tra instanceof Text){
                tra.setPosition(getCenterX(), getCenterY());
            }
            else{
                tra.setPosition(getTopLeftX(), getTopLeftY());
            }
        }
    }
*/

    @Override
    public boolean colliding(Event e){
        Vector2i v = e.asMouseButtonEvent().position;
        return colliding(v.x, v.y);
    }

    @Override
    public boolean colliding(int x, int y) {
        //int j = (this.x - (xSize / 2));
        //int g = (this.x + (xSize / 2));
        //int h = (this.y - (ySize / 2));
        //int k = (this.y + (ySize / 2));
        System.out.format("X : %d  Y : %d  This but x : %d   This but y : %d \n", x, y, getTopLeftX(), getTopLeftY());
        //System.out.format("Height : %d, Width : %d \n", heightt, widthh);

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