/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup_originals;

import java.io.IOException;
import java.nio.file.Paths;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 *
 * @author newby
 */
public class Image extends Actor {

    private int xSize;
    private int ySize;
    private String name;

    public Image(int x, int y, int r, String textureFile) {
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

        this.x = x;
        this.y = y;
        this.r = r;
        this.ySize = imgTexture.getSize().y;
        this.xSize = imgTexture.getSize().x;
        String split[] = textureFile.split(".");
        name  = textureFile;


        obj = img;
    }

    //@Override
    public boolean newWithin(org.jsfml.window.event.Event e){

        Vector2i v = e.asMouseButtonEvent().position;
        int j = (this.x - (xSize / 2));
        int g = (this.x + (xSize / 2));
        int h = (this.y - (ySize / 2));
        int k = (this.y + (ySize / 2));
        if(j < v.x && v.x < g && h < v.y && v.y < k){
            return true;
        }

        return false;

    }

    public void clicked(CharMenu menu){

        if(name.contains("magic")){
            System.out.println("magic selected");
            menu.setStats(0, 10, 0, 3, 2);
        }
        else if(name.contains("ranged")){
            System.out.println("ranged selected");
            menu.setStats(0, 0, 5, 5, 5);
        }
        else if(name.contains("Strength")){
            System.out.println("Strength selected");
            menu.setStats(5, 0, 0, 5, 5);
        }

    }


}
