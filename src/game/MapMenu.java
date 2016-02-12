/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author newby
 */
public class MapMenu extends EntityHolder {
    private final static String SEP = Constants.SEP;
    public int loaded = 0;
    private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
    private Bubble[] nodes = new Bubble[10];

    /**
     *
     */
    public MapMenu(RenderWindow w) {
        super(w, "Map");

        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();

        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file
            String a = (dir.toString() + SEP + "assets" + SEP + "art" + SEP + "game-map.jpg");

            add(new Image(w, 1024 / 2, 768 / 2, 0, a));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        add(new Button(w, 10, 10, 100, 50, Color.WHITE, 200 , "BACK", 15 ));
                
                /*creating the nodes*/
        nodes[0] = new Bubble(w, null, 200, 600, 10, Color.BLACK, 300);
        nodes[1] = new Bubble(w, null, 200, 500, 10, Color.BLACK, 300);
        nodes[2] = new Bubble(w, null, 500, 500, 10, Color.BLACK, 300);
        nodes[3] = new Bubble(w, null, 550, 400, 10, Color.BLACK, 300);
        nodes[4] = new Bubble(w, null, 350, 300, 10, Color.BLACK, 300);
        nodes[5] = new Bubble(w, null, 500, 100, 10, Color.BLACK, 300);
        nodes[6] = new Bubble(w, null, 700, 200, 10, Color.BLACK, 300);
        nodes[7] = new Bubble(w, null, 700, 400, 10, Color.BLACK, 300);
        nodes[8] = new Bubble(w, null, 800, 300, 10, Color.BLACK, 300);
        nodes[9] = new Bubble(w, null, 100, 300, 10, Color.BLACK, 300);

        for(int i = 0; i < 10; i++){
            add(nodes[i]);
        }
    }

    public void add(Entity a){
        objs.add(a.getTransformable());
        addEntity(a);
    }

    public void draw() {
        for (Entity a : getEntities()) {
            if(loaded==1){
                a.draw();
            }
        }
    }

/*  //Do we need this
    public void performMove(){
        for(Actor a : act){
            a.performMove();
        }
    }*/

    public void load(){
        loaded++;
    }

    public void unload(){
        loaded--;
    }

    private int randomInt(int aStart, int aEnd){
        Random rand = new Random();
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * rand.nextDouble());
        int randomNumber =  (int)(fraction + aStart);
        return(randomNumber);
    }
}
