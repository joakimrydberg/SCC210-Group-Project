/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import interfaces.InteractingEntity;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author newby
 */
public class Image extends Entity implements InteractingEntity {


    public Image(RenderWindow w, int x, int y, int width, int height,  String textureFile) {
        super(w, textureFile);
        //
        // Load image/ texture
        //
        Texture imgTexture = new Texture( );
        try {
            imgTexture.loadFromFile(Paths.get(textureFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        imgTexture.setSmooth(true);

        Sprite img = new Sprite(imgTexture);

        Vector2i imgSize = imgTexture.getSize();
        int widthTemp = (width < 0) ? imgSize.x : width;
        int heightTemp = (height < 0) ? imgSize.y : height;

        setWidthHeight(widthTemp, heightTemp);

        setCenterX(x);
        setCenterY(y);

        addTransformable(img, 0, 0, 0, 0); //not supplying w and h so the origin will be 0

        if (width >= 0)
            img.scale((float)(widthTemp / (imgSize.x/1.0)), (float)(heightTemp / (imgSize.y / 1.0)));


    }

    public Image(RenderWindow w, int x, int y, String textureFile) {
        this(w, x, y, -1, -1, textureFile);
    }



    @Override
    public boolean checkWithin(Event e) {
        Vector2i v = e.asMouseButtonEvent().position;
        return checkWithin(v.x, v.y);
    }

    @Override
    public boolean checkWithin(int x, int y) {
        return (getTopLeftX() < x && x < getTopLeftX() + getWidth()
                && getTopLeftY() < y && y < getTopLeftY() + getHeight());
    }

    public void clicked(CharMenu menu){

        if (this.getName().contains("magic")){
            DebugPrinter.debugPrint(this, "magic selected");
            menu.setStats(0, 10, 0, 3, 2);
        }
        else if (this.getName().contains("ranged")){
            DebugPrinter.debugPrint(this, "ranged selected");
            menu.setStats(0, 0, 5, 5, 5);
        }
        else if (this.getName().contains("strength")){
            DebugPrinter.debugPrint(this, "Strength selected");
            menu.setStats(5, 0, 0, 5, 5);
        }
    }
    public void clicked(MainMenu mainMenu, CharMenu menu, MapMenu map){

        if (this.getName().contains("magic")){
            DebugPrinter.debugPrint(this, "magic selected");
            menu.setStats(0, 10, 0, 3, 2);

        }
        else if (this.getName().contains("ranged")){
            DebugPrinter.debugPrint(this, "ranged selected");
            menu.setStats(0, 0, 5, 5, 5);
        }
        else if (this.getName().contains("strength")){
            DebugPrinter.debugPrint(this, "Strength selected");
            menu.setStats(5, 0, 0, 5, 5);
        }
    }

    public void hide(){


            hidden = true;

    }
    public void show(){

            hidden = false;

    }





}
