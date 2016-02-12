/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup_originals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import game.*;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2i;

/**
 *
 * @author newby
 */
public class Button extends Rect {
    private int heightt;
    private int widthh;
    private String name;

    private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
    private Actor menu;

    private static String JavaVersion =
            Runtime.class.getPackage( ).getImplementationVersion( );
    private static String JdkFontPath =
            "C:\\Program Files\\Java\\jdk" + JavaVersion +
                    "\\jre\\lib\\fonts\\";
    private static String JreFontPath =
            "C:\\Program Files\\Java\\jre" + JavaVersion +
                    "\\lib\\fonts\\";

    private static String FontFile  = "LucidaSansRegular.ttf";
    private String FontPath; // Where fonts were found

    private int i = 0;

    public Button(int x, int y, int height, int width, Color c, int transparency, String textt, int size) {

        super(x, y, height, width, c, transparency);

        if ((new File(game.Constants.JRE_FONT_PATH)).exists())
            FontPath = game.Constants.JRE_FONT_PATH;
        else
            FontPath = game.Constants.JDK_FONT_PATH;


        Font sansRegular = new Font( );
        try {
            sansRegular.loadFromFile(
                    Paths.get(FontPath+FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        Text text = new Text (textt, sansRegular, size);
        text.setColor(Color.BLACK);
        //text.setStyle(Text.BOLD | Text.UNDERLINED);

        FloatRect textBounds = text.getLocalBounds( );
        // Find middle and set as origin/ reference point
        text.setOrigin(textBounds.width / 2,
                textBounds.height / 2);
        //obj = text;
        objs.add(text);

        objs.add(super.obj);
        widthh = width;
        heightt = height;
        name = textt;

    }

    void draw(RenderWindow w) {
        for (Transformable tra : objs) {
            w.draw((Drawable)tra);
        }
    }

    void performMove( ) {
        for (Transformable tra : objs) {
            if(tra instanceof Text){
                tra.setPosition(x+(heightt / 2),y + (widthh /2));
            }
            else{
                tra.setPosition(x,y);
            }
        }
    }

    public boolean newWithin(org.jsfml.window.event.Event e){

        Vector2i v = e.asMouseButtonEvent().position;
        //int j = (this.x - (xSize / 2));
        //int g = (this.x + (xSize / 2));
        //int h = (this.y - (ySize / 2));
        //int k = (this.y + (ySize / 2));
        //System.out.format("X : %d  Y : %d  This but x : %d   This but y : %d \n", v.x, v.y, this.x, this.y);
        //System.out.format("Height : %d, Width : %d \n", heightt, widthh);
        if(this.x < v.x && v.x < this.x+(heightt)&& this.y < v.y && v.y < this.y+(widthh)){ //NEEDSS MASSIVE WORK

            return true;
        }
        return false;
    }

    void clicked(MainMenu mainMenu, CharMenu charMenu, MapMenu mapMenu){

        if(name.equals("NEW GAME")){
            mainMenu.unload(); charMenu.load(); System.out.println("NEW GAME clicked");
        }
        else if(name.equals("CREATE")){
            charMenu.unload(); mapMenu.load(); System.out.println("Create clicked");
        }
        else if(name.equals(">>")){
            System.out.format("%d", this.i);
            if(this.i < 2){
                this.i++;
                charMenu.moveRight(i);
            }
        }
        else if(name.equals("<<")){
            System.out.format("%d", this.i);
            if(this.i > 0){
                this.i--;
                charMenu.moveLeft(i);
            }
        }
        else if(name.equals("BACK")){
            mapMenu.unload(); charMenu.load(); System.out.println("Create clicked");
        }
    }

}
