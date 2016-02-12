/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.*;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Ross Newby
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

        //adding the map image to the screen
        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file
            String a = (dir.toString() + SEP + "assets" + SEP + "art" + SEP + "game-map.png");

            add(new Image(w, 0, 0, 0, a));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        add(new Rect(w, null, 10, 10, 50, 30, Color.WHITE, 300));
        add(new Button(w, 15, 15, 100, 50, Color.WHITE, 200 , "BACK", 15 ));

        //creating the nodes
        nodes[0] = new Bubble(w, null, 200, 600, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[1] = new Bubble(w, null, 200, 500, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[2] = new Bubble(w, null, 100, 300, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[3] = new Bubble(w, null, 500, 500, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[4] = new Bubble(w, null, 550, 400, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[5] = new Bubble(w, null, 350, 300, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[6] = new Bubble(w, null, 500, 100, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[7] = new Bubble(w, null, 700, 400, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[8] = new Bubble(w, null, 700, 200, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[9] = new Bubble(w, null, 800, 300, 10, Color.WHITE, Color.BLACK, 4, 300);

        // ***CURRENTLY ISSUES WITH LINE ANGLING THEREFORE CONNECTIONS ARE COMMENTED OUT FOR NOW****
        //draw lines connecting the nodes
        drawLine(w, nodes[0], nodes[1], Color.BLACK);

        drawLine(w, nodes[1], nodes[2], Color.BLACK);
        //drawLine(w, nodes[1], nodes[3], Color.BLACK);

        //drawLine(w, nodes[3], nodes[4], Color.BLACK);

        //drawLine(w, nodes[4], nodes[5], Color.BLACK);
        //drawLine(w, nodes[4], nodes[7], Color.BLACK);

        //drawLine(w, nodes[5], nodes[6], Color.BLACK);

        //drawLine(w, nodes[7], nodes[8], Color.BLACK);
        //drawLine(w, nodes[7], nodes[9], Color.BLACK);

        //adding the nodes to the screen in a loop
        for(int i = 0; i < 10; i++){
            add(nodes[i]);
        }

        // ****NEEDS AMENDING AS THE LOCKS ARE JUST SUPERFICIAL AT THIS POINT****
        //add locked images to each node
        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file
            String b = (dir.toString() + SEP + "assets" + SEP + "art" + SEP + "lock.png");

            for(int i = 0; i < 10; i++){
                Image img = new Image(w, 0, 0, 0, b);
                img.setCenterX(nodes[i].getCenterX());
                img.setCenterY(nodes[i].getCenterY());
                add(img);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
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

    /*
     * This method uses trigonometry to draw a solid line between two nodes
     */
    public void drawLine(RenderWindow w, Bubble p1, Bubble p2, Color c){
        //find the length the line should be using pythagoras theurum
        double length = Math.sqrt(Math.pow(p2.getCenterY() - p1.getCenterY(), 2) + Math.pow(p2.getCenterX() - p1.getCenterX(), 2));

        //create the line from the starting node
        Rect line = new Rect(w, null, p1.getCenterX(), p1.getCenterY(), (int)length, 5, c, 300);
        add(line);

        //rotate the line to point at the second node
        if (p2.getCenterX() - p1.getCenterX() == 0){ //if there is no change in x
            line.rotate(270); //rotate the line to point upwards
        }
        else {
            float trigAngle = (float)Math.toDegrees(Math.atan((p2.getCenterY() - p1.getCenterY()) / (p2.getCenterX() - p1.getCenterX())));
            System.out.println("Rotating by: " + trigAngle);

            // EDITING NEEDED TO ACCOUNT FOR NODES THAT ARE NOT BETWEEN NORTH - EAST OF P1
            /*if (p2.getCenterX() < p1.getCenterX() && p2.getCenterY() <= p1.getCenterY()){
                line.rotate(trigAngle + 180);
            }
            else if (p2.getCenterX() >= p1.getCenterX() && p2.getCenterY() < p1.getCenterY()){
                line.rotate(trigAngle);
            }*/

            line.rotate(trigAngle);
        }
    }

    /*
     *
     */
    public void load(){
        loaded++;
    }

    /*
     *
     */
    public void unload(){
        loaded--;
    }

    //a random int between two integers, i may still need this, wi/l wait and see (Ross)
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
