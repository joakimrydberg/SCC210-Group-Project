/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.*;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

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

        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2,
                centerY = windowSize.y / 2;

        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();

        //adding the map image to the screen
        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file
            String a = (dir.toString() + SEP + "assets" + SEP + "art" + SEP + "game-map.png");

            addEntity(new Image(w, centerX, centerY, 0, a));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //addEntity(new Rect(w, null, 10 + centerX, 10 + centerY, 50, 30, Color.WHITE, 300));
        addEntity(new Button(w, 50, 25, 80, 30, Color.WHITE, 200 , "BACK", 15 ));

        //things josh added to the nodes .. how come ? it draws them at the wrong position
        /*
        nodes[0] = new Bubble(w, null, 200 + centerX, 600 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[1] = new Bubble(w, null, 200 + centerX, 500 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[2] = new Bubble(w, null, 100 + centerX, 300 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[3] = new Bubble(w, null, 500 + centerX, 500 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[4] = new Bubble(w, null, 550 + centerX, 400 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[5] = new Bubble(w, null, 350 + centerX, 300 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[6] = new Bubble(w, null, 500 + centerX, 100 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[7] = new Bubble(w, null, 700 + centerX, 400 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[8] = new Bubble(w, null, 700 + centerX, 200 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[9] = new Bubble(w, null, 800 + centerX, 300 + centerY, 10, Color.WHITE, Color.BLACK, 4, 300);
        */

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
        nodes[9] = new Bubble(w, null, 800 , 300, 10, Color.WHITE, Color.BLACK, 4, 300);

        // ***CURRENTLY ISSUES WITH LINE ANGLING THEREFORE CONNECTIONS ARE COMMENTED OUT FOR NOW****
        //draw lines connecting the nodes
        drawLine(w, nodes[0], nodes[1], Color.BLACK);

        drawLine(w, nodes[1], nodes[2], Color.BLACK);
        drawLine(w, nodes[1], nodes[3], Color.BLACK);

        drawLine(w, nodes[3], nodes[4], Color.BLACK);

        drawLine(w, nodes[4], nodes[5], Color.BLACK);
        drawLine(w, nodes[4], nodes[7], Color.BLACK);

        drawLine(w, nodes[5], nodes[6], Color.BLACK);

        drawLine(w, nodes[7], nodes[8], Color.BLACK);
        drawLine(w, nodes[7], nodes[9], Color.BLACK);

        //adding the nodes to the screen in a loop
        for(int i = 0; i < 10; i++){
            addEntity(nodes[i]);
        }

        // ****NEEDS AMENDING AS THE LOCKS ARE JUST SUPERFICIAL AT THIS POINT****
        //add locked images to each node
        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file
            String b = (dir.toString() + SEP + "assets" + SEP + "art" + SEP + "lock.png");

            for(int i = 0; i < 10; i++){
                Image img = new Image(w, 0 , 0 , 0, b);

                //debugging
                //System.out.println("node[" + i + "]: " + nodes[i].getCenterX() + ", " + nodes[i].getCenterY());

                img.setCenterX(nodes[i].getCenterX());
                img.setCenterY(nodes[i].getCenterY());

                //debugging
                //System.out.println("image " + i + ": " + img.getCenterX() + ", " + img.getCenterY());

                addEntity(img);

                //why on earth do I need these both before and afterwards to work ? I wont edit this out till i know entity is sorted
                img.setCenterX(nodes[i].getCenterX());
                img.setCenterY(nodes[i].getCenterY());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
        //new line for drawing Rect which puts the x and y parameters directly between the 2 nodes
        Rect line = new Rect(w, null, (p1.getCenterX() + p2.getCenterX()) / 2, (p1.getCenterY() + p2.getCenterY()) / 2, (int)length, 5, c, 300);

        //line that no longer works as rect x param is now centre and not the top-left .. will this be chnaging again ?
        //Rect line = new Rect(w, null, p1.getCenterX(), p1.getCenterY(), (int)length, 5, c, 300);

        addEntity(line);

        //rotate the line to point at the second node
        if (p2.getCenterX() - p1.getCenterX() == 0){ //if there is no change in x; points are directly above / below each other
            line.rotate(270); //rotate the line to point upwards
        }
        else {
            int l1 = p2.getCenterY()- p1.getCenterY();
            int l2 = p2.getCenterX() - p1.getCenterX();

            //float trigAngle = (float)Math.toDegrees(Math.atan((p2.getCenterY() - p1.getCenterY()) / (p2.getCenterX() - p1.getCenterX())));
            //float trigAngle = (float)Math.toDegrees(Math.atan(l1 / l2));
            float trigAngle = (float)Math.toDegrees(Math.atan2(l1, l2));

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

    //a random int between two integers, i may still need this, will wait and see (Ross)
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
