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
    private Node[] nodes = new Node[10];

    /**
     *
     */
    public MapMenu(RenderWindow w) {
        super(w, "Map");

        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(w, centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "game-map.png"));

        Button backButton = new Button(w, 50, 25, 80, 30, Color.WHITE, 200 , "BACK", 15 );
        backButton.addClickListener(this);
        addEntity(backButton);

        //creating the nodes
        nodes[0] = new Node(w, "1", 200, 600, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[1] = new Node(w, "2", 200, 500, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[2] = new Node(w, "3", 100, 300, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[3] = new Node(w, "4", 500, 500, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[4] = new Node(w, "5", 550, 400, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[5] = new Node(w, "6", 350, 300, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[6] = new Node(w, "7", 500, 100, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[7] = new Node(w, "8", 700, 400, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[8] = new Node(w, "9", 700, 200, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[9] = new Node(w, "10", 800 , 300, 10, Color.WHITE, Color.BLACK, 4, 300);

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
            nodes[i].addClickListener(this);
            addEntity(nodes[i]);
        }

        // ****NEEDS AMENDING AS THE LOCKS ARE JUST SUPERFICIAL AT THIS POINT****
        //add locked images to each node
        for(int i = 0; i < 10; i++){
            addEntity(new Image(w, nodes[i].getCenterX(), nodes[i].getCenterY(), "assets" + Constants.SEP + "art" + Constants.SEP + "lock.png"));
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
        else if (clickable instanceof Node){
            Entity button = (Node) clickable;
            if (button.getName().equals("1")) {
                System.out.println("node 1 clicked");
            }
            else if (button.getName().equals("2")) {
                System.out.println("node 2 clicked");
            }
            else if (button.getName().equals("3")) {
                System.out.println("node 3 clicked");
            }
            else if (button.getName().equals("4")) {
                System.out.println("node 4 clicked");
            }
            else if (button.getName().equals("5")) {
                System.out.println("node 5 clicked");
            }
            else if (button.getName().equals("6")) {
                System.out.println("node 6 clicked");
            }
            else if (button.getName().equals("7")) {
                System.out.println("node 7 clicked");
            }
            else if (button.getName().equals("8")) {
                System.out.println("node 8 clicked");
            }
            else if (button.getName().equals("9")) {
                System.out.println("node 9 clicked");
            }
            else if (button.getName().equals("10")) {
                System.out.println("node 10 clicked");
            }
        }
    }

    /*
     * This method uses trigonometry to draw a solid line between two nodes
     */
    public void drawLine(RenderWindow w, Node p1, Node p2, Color c){
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
    public void drawDottedLine(RenderWindow w, Node p1, Node p2, Color c){
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
