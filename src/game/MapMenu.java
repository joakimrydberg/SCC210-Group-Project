/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2i;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Ross Newby
 */
public class MapMenu extends Menu {
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

            addEntity(new Image(w, centerX, centerY, windowSize.x, windowSize.y, a));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //addEntity(new Rect(w, null, 10 + centerX, 10 + centerY, 50, 30, Color.WHITE, 300));
        Button backButton = new Button(w, 50, 25, 80, 30, Color.WHITE, 200 , "BACK", 15 );
        backButton.addClickListener(this);
        addEntity(backButton);

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

        //draw lines connecting the nodes
        drawDottedLine(w, nodes[0], nodes[1], Color.BLACK);

        drawDottedLine(w, nodes[1], nodes[2], Color.BLACK);
        drawDottedLine(w, nodes[1], nodes[3], Color.BLACK);

        drawDottedLine(w, nodes[3], nodes[4], Color.BLACK);

        drawDottedLine(w, nodes[4], nodes[5], Color.BLACK);
        drawDottedLine(w, nodes[4], nodes[7], Color.BLACK);

        drawDottedLine(w, nodes[5], nodes[6], Color.BLACK);

        drawDottedLine(w, nodes[7], nodes[8], Color.BLACK);
        drawDottedLine(w, nodes[7], nodes[9], Color.BLACK);

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
                Image img = new Image(w, 0, 0, b);

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

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof  Button ) {
            Entity button = (Button) clickable;
            if (button.getName().equals("BACK")) {
                this.unload();
                new CharMenu(getWindow()).load();
                System.out.println("Create clicked");
            }
        }
    }

    /*
     * This method uses trigonometry to draw a solid line between two nodes
     */
    public void drawLine(RenderWindow w, Bubble p1, Bubble p2, Color c){
        double length = Math.sqrt(Math.pow(p2.getCenterY() - p1.getCenterY(), 2) + Math.pow(p2.getCenterX() - p1.getCenterX(), 2)); //find the length the line should be using pythagoras theorem
        Rect line = new Rect(w, null, (p1.getCenterX() + p2.getCenterX()) / 2, (p1.getCenterY() + p2.getCenterY()) / 2, (int)length, 5, c, 300); //create the line between the two nodes

        //line that no longer works as rect x param is now centre and not the top-left .. will this be chnaging again ?
        //Rect line = new Rect(w, null, p1.getCenterX(), p1.getCenterY(), (int)length, 5, c, 300);

        addEntity(line);

        //rotate the line using trigonometry
        int l1 = p2.getCenterY()- p1.getCenterY();
        int l2 = p2.getCenterX() - p1.getCenterX();

        float trigAngle = (float)Math.toDegrees(Math.atan2(l1, l2)); //uses trig method 'SOH CAH TOA'
        line.rotate(trigAngle);
    }

    /*
     * This method uses trigonometry to draw a dotted line between two nodes
     */
    public void drawDottedLine(RenderWindow w, Bubble p1, Bubble p2, Color c){
        double length = Math.sqrt(Math.pow(p2.getCenterY() - p1.getCenterY(), 2) + Math.pow(p2.getCenterX() - p1.getCenterX(), 2)); //find the length the line should be using pythagoras theorem
        int noOfLines = (int)length / 20; //has a line every 20p

        Rect lines[] = new Rect[noOfLines];

        double xInterval = (p2.getCenterX() - p1.getCenterX()) / noOfLines;
        double yInterval = (p2.getCenterY() - p1.getCenterY()) / noOfLines;

        //creating the lines at intervals between the 2 nodes
        for(int i = 0, j = 1; i < noOfLines; i++, j++){
            lines[i] = new Rect(w, null, (int)(p1.getCenterX() + (xInterval * j)), (int)(p1.getCenterY() + (yInterval * j)), 10, 5, c, 300);
        }

        //adding the lines to the screen in a loop
        for(int i = 0; i < noOfLines; i++){
            addEntity(lines[i]);
        }

         //rotate the lines using trigonometry
        int l1 = p2.getCenterY()- p1.getCenterY();
        int l2 = p2.getCenterX() - p1.getCenterX();

        float trigAngle = (float)Math.toDegrees(Math.atan2(l1, l2)); //uses trig method 'SOH CAH TOA'
        for(int i = 0; i < noOfLines; i++){
            lines[i].rotate(trigAngle);
        }
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
