/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import interfaces.Clickable;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * This...
 * @author Ross Newby
 */
public class MapMenu extends Menu {
    private final static String SEP = Constants.SEP;
    public int loaded = 0;
    private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
    private Node[] nodes = new Node[10];
    private NodeDescriptor[] nodeDesc = new NodeDescriptor[10];
    public final static String NAME = "Map Menu";

    /**
     *
     */
    public MapMenu() {
        super(NAME);

        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "game-map.png"));

        Button backButton = new Button(50, 40, 80, 50, Color.WHITE, 200 , "BACK", 15 );
        backButton.addClickListener(this);
        addEntity(backButton);

        //creating the nodes
        nodes[0] = new Node("1", 200, 600, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[1] = new Node("2", 200, 500, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[2] = new Node("3", 100, 300, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[3] = new Node("4", 500, 500, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[4] = new Node("5", 550, 400, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[5] = new Node("6", 350, 300, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[6] = new Node("7", 500, 100, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[7] = new Node("8", 700, 400, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[8] = new Node("9", 700, 200, 10, Color.WHITE, Color.BLACK, 4, 300);
        nodes[9] = new Node("10", 800 , 300, 10, Color.WHITE, Color.BLACK, 4, 300);

        //creating node descriptors
        nodeDesc[0] = new NodeDescriptor("d1", nodes[0], 100, 100);
        nodeDesc[1] = new NodeDescriptor("d2", nodes[1], 100, 100);
        nodeDesc[2] = new NodeDescriptor("d3", nodes[2], 100, 100);
        nodeDesc[3] = new NodeDescriptor("d4", nodes[3], 100, 100);
        nodeDesc[4] = new NodeDescriptor("d5", nodes[4], 100, 100);
        nodeDesc[5] = new NodeDescriptor("d6", nodes[5], 100, 100);
        nodeDesc[6] = new NodeDescriptor("d7", nodes[6], 100, 100);
        nodeDesc[7] = new NodeDescriptor("d8", nodes[7], 100, 100);
        nodeDesc[8] = new NodeDescriptor("d9", nodes[8], 100, 100);
        nodeDesc[9] = new NodeDescriptor("d10", nodes[9], 100, 100);

        //draw lines connecting the nodes
        drawDottedLine(nodes[0], nodes[1], Color.BLACK);

        drawDottedLine(nodes[1], nodes[2], Color.BLACK);
        drawDottedLine(nodes[1], nodes[3], Color.BLACK);

        drawDottedLine(nodes[3], nodes[4], Color.BLACK);

        drawDottedLine(nodes[4], nodes[5], Color.BLACK);
        drawDottedLine(nodes[4], nodes[7], Color.BLACK);

        drawDottedLine(nodes[5], nodes[6], Color.BLACK);

        drawDottedLine(nodes[7], nodes[8], Color.BLACK);
        drawDottedLine(nodes[7], nodes[9], Color.BLACK);

        //adding the nodes to the screen in a loop
        for(int i = 0; i < 10; i++){
            nodes[i].addClickListener(this);
            addEntity(nodes[i]);
        }

        //add locked images to each node ****NEEDS AMENDING AS THE LOCKS ARE JUST SUPERFICIAL AT THIS POINT****
        for(int i = 0; i < 10; i++){
            nodes[i].lock();
        }
        nodes[0].unlock();
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) { //TODO should there actually be a back button? idk
        if (clickable instanceof  Button ) {
            Entity button = (Button) clickable;
            if (button.getName().equals("BACK")) {
                this.unload();

                Drawer charMenu = Driver.getDrawer(CharMenu.NAME);
                if (charMenu == null)
                    charMenu = new CharMenu();

                charMenu.load();

                System.out.println("Create clicked");
            }
        }
        else if (clickable instanceof Node){
            Entity button = (Node) clickable;
            int i = Integer.parseInt(button.getName()) - 1;

            //debugging
            System.out.println("Node " + (i + 1) + " clicked");

            if (!nodes[i].isLocked()){
                if (nodeDesc[i].isLoaded()){
                    nodeDesc[i].unload();
                }
                else {
                    nodeDesc[i].load();
                }
            }
            else {
                System.out.println("Node is locked");
            }

            /*
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
            */
        }
    }

    /*
     * This method uses trigonometry to draw a solid line between two nodes
     */
    public void drawLine(RenderWindow w, Node p1, Node p2, Color c){
        double length = Math.sqrt(Math.pow(p2.getCenterY() - p1.getCenterY(), 2) + Math.pow(p2.getCenterX() - p1.getCenterX(), 2)); //find the length the line should be using pythagoras theorem
        Rect line = new Rect(null, (p1.getCenterX() + p2.getCenterX()) / 2, (p1.getCenterY() + p2.getCenterY()) / 2, (int)length, 5, c, 300); //create the line between the two nodes

        addEntity(line);

        //setRotation the line using trigonometry
        int l1 = p2.getCenterY()- p1.getCenterY();
        int l2 = p2.getCenterX() - p1.getCenterX();
        float trigAngle = (float)Math.toDegrees(Math.atan2(l1, l2)); //uses trig method 'SOH CAH TOA'
        line.rotate(trigAngle);
    }

    /*
     * This method uses trigonometry to draw a dotted line between two nodes
     */
    public void drawDottedLine(Node p1, Node p2, Color c){
        double length = Math.sqrt(Math.pow(p2.getCenterY() - p1.getCenterY(), 2) + Math.pow(p2.getCenterX() - p1.getCenterX(), 2)); //find the length the line should be using pythagoras theorem
        int noOfLines = (int)length / 20; //has a line every 20p

        Rect lines[] = new Rect[noOfLines];

        double xInterval = (p2.getCenterX() - p1.getCenterX()) / noOfLines;
        double yInterval = (p2.getCenterY() - p1.getCenterY()) / noOfLines;

        //creating the lines at intervals between the 2 nodes
        for(int i = 0, j = 1; i < noOfLines; i++, j++){
            lines[i] = new Rect(null, (int)(p1.getCenterX() + (xInterval * j)), (int)(p1.getCenterY() + (yInterval * j)), 10, 5, c, 300);
        }

        //adding the lines to the screen in a loop
        for(int i = 0; i < noOfLines; i++){
            addEntity(lines[i]);
        }

         //setRotation the lines using trigonometry
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
