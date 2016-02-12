/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author newby
 */
public class Image extends Entity implements CollidingEntity {

    public Image(RenderWindow w, int x, int y, int r, String textureFile) {
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
        img.setOrigin(Vector2f.div(
                new Vector2f(imgTexture.getSize()), 2));

        setWidthHeight(imgTexture.getSize().x, imgTexture.getSize().y);

        setTopLeftX(x);
        setTopLeftY(y);

        String split[] = textureFile.split(".");

        setTransformable(img);
    }

    @Override
    public boolean colliding(Event e) {
        Vector2i v = e.asMouseButtonEvent().position;
        return colliding(v.x, v.y);
    }

    @Override
    public boolean colliding(int x, int y) {
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
        else if (this.getName().contains("Strength")){
            DebugPrinter.debugPrint(this, "Strength selected");
            menu.setStats(5, 0, 0, 5, 5);
        }
    }
}
