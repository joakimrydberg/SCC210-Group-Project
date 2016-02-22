/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import abstract_classes.Entity;
import components.Button;
import components.Image;
import components.Node;
import components.Rect;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import tools.CSVReader;
import tools.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * This...
 * @author Ross Newby
 */
public class MapMenu extends Menu /*implements Clickable*/ {
    private Node[] nodes = new Node[10];
    private NodeDescriptor[] nodeDesc = new NodeDescriptor[10];
    public final static String NAME = "Map Menu";
    private final int map_id = 1;  // = new Random().nextInt( /* a value */);
    private final static int MOVE_DIST = 200;
    ArrayList<String[]> csvContent;

    /**
     *
     */
    public MapMenu() {
        super(NAME);

        super.addEntity(this);

        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "game-map.png"));

        Button backButton = new Button(50, 40, 80, 50, "BROWN", 200 , "BACK", 15 );
        backButton.addClickListener(this);
        addEntity(backButton);

    //    this.addClickListener(this);

        csvContent = CSVReader.read("assets" + Constants.SEP + "maps" + Constants.SEP + "map_" + map_id + ".csv");

//        { // creating start and end nodes
//            Iterator it = csvContent.iterator();
//            while (it.hasNext()) {
//                String[] row = (String[]) it.next();
//
//                if (row[2].equals("start")) {
//                    nodes[0] = new Node("start", Integer.parseInt(row[0]), Integer.parseInt(row[1]), 10, Color.WHITE, Color.BLACK, 4, 300);
//                    csvContent.remove(row);
//                } else if (row[2].equals("end")) {
//                    nodes[1] = new Node("end", Integer.parseInt(row[0]), Integer.parseInt(row[1]), 10, Color.WHITE, Color.BLACK, 4, 300);
//                    csvContent.remove(row);
//                }
//            }
//
//            if (nodes[0] == null || nodes[1] == null) {
//                throw new RuntimeException("Invalid map csv.");
//            }
//        }


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
        nodeDesc[0] = new NodeDescriptor("Level 1", "Easy", nodes[0], 200, 150, this);
        nodeDesc[1] = new NodeDescriptor("Level 2", "Easy", nodes[1], 200, 150, this);
        nodeDesc[2] = new NodeDescriptor("Level 3", "Easy", nodes[2], 200, 150, this);
        nodeDesc[3] = new NodeDescriptor("Level 4", "Medium", nodes[3], 200, 150, this);
        nodeDesc[4] = new NodeDescriptor("Level 5", "Medium", nodes[4], 200, 150, this);
        nodeDesc[5] = new NodeDescriptor("Level 6", "Medium", nodes[5], 200, 150, this);
        nodeDesc[6] = new NodeDescriptor("Level 7", "Hard", nodes[6], 200, 150, this);
        nodeDesc[7] = new NodeDescriptor("Level 8", "Hard", nodes[7], 200, 150, this);
        nodeDesc[8] = new NodeDescriptor("Level 9", "Hard", nodes[8], 200, 150, this);
        nodeDesc[9] = new NodeDescriptor("Level 10", "BOSS", nodes[9], 200, 150, this);

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


//    public int navigateTo(Node frm, int stepCount) {
//
////        Iterator it = csvContent.iterator();
////        while (it.hasNext()) {
////            String[] row = (String[]) it.next();
////
////            if (row[2].equals())
////        }
//    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) { //TODO should there actually be a back button? idk
        if (clickable instanceof  Button ) {
            Entity button = (Button) clickable;
            if (button.getName().equals("BACK")) {
                this.unload();

                loadDrawer(CharMenu.class);

                //close any node descriptors that are open
                for(int i = 0; i < 10; i++){
                    if (nodeDesc[i].isLoaded()){
                        nodeDesc[i].unload();
                    }
                }

                System.out.println("Back clicked");
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

//    //=================testing===========================================================================
//
//    @Override
//    public void clicked(Event e) {
//        System.out.println("X: " + e.asMouseEvent().position.x + ", Y: " + e.asMouseEvent().position.y);
//    }
//
//    @Override
//    public void addClickListener(ClickListener clickListener) {
//
//    }
//
//    /**
//     * Checks whether the x and y parameters
//     *
//     * @param x - X coordinate to check
//     * @param y - Y coordinate to check
//     * @return - true if checkWithin, false if not;
//     */
//    @Override
//    public boolean checkWithin(int x, int y) {
//        return true;
//    }
//
//    /**
//     * Checks whether the x and y parameters passed in an Event obj
//     *
//     * @param e - the Event that caused this method call
//     * @return - true if checkWithin, false if not;
//     */
//    @Override
//    public boolean checkWithin(Event e) {
//        return true;
//    }

}
