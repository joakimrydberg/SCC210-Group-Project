/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup_originals;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import game.Constants;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;

/**
 *
 * @author newby
 */
public class MapMenu extends Actor{
    public int loaded = 0;
    private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
    public ArrayList<Actor> act = new ArrayList<Actor>( );
    private Bubble[] nodes = new Bubble[10];

    /**
     *
     */
    public MapMenu(){
                /*read and display map*/
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file
            String a = (dir.toString() + Constants.SEP + "assets" + Constants.SEP + "art" + Constants.SEP + "game-map.jpg");
            add(new Image(1024 / 2, 768 / 2, 0, a));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        add(new Button(10, 10, 100, 50, Color.WHITE, 200 , "BACK", 15 ));
                
                /*creating the nodes*/
        nodes[0] = new Bubble(200, 600, 10, Color.BLACK, 300);
        nodes[1] = new Bubble(200, 500, 10, Color.BLACK, 300);
        nodes[2] = new Bubble(500, 500, 10, Color.BLACK, 300);
        nodes[3] = new Bubble(550, 400, 10, Color.BLACK, 300);
        nodes[4] = new Bubble(350, 300, 10, Color.BLACK, 300);
        nodes[5] = new Bubble(500, 100, 10, Color.BLACK, 300);
        nodes[6] = new Bubble(700, 200, 10, Color.BLACK, 300);
        nodes[7] = new Bubble(700, 400, 10, Color.BLACK, 300);
        nodes[8] = new Bubble(800, 300, 10, Color.BLACK, 300);
        nodes[9] = new Bubble(100, 300, 10, Color.BLACK, 300);
        for(int i = 0; i < 10; i++){
            add(nodes[i]);
        }
    }

    public void add(Actor a){
        objs.add(a.obj);
        act.add(a);
    }
    void draw(RenderWindow w) {
        for (Actor a : act) {
            if(loaded==1){
                a.draw(w);
            }
        }
    }
    void performMove(){
        for(Actor a : act){
            a.performMove();
        }
    }
    void load(){
        loaded++;
    }
    void unload(){
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
