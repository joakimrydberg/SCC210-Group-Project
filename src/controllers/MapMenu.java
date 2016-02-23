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
import components.mobs.Mage;
import components.mobs.Player;
import components.mobs.Ranger;
import components.mobs.Warrior;
import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;
import tools.CSVReader;
import tools.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This...  //TODO complete sentence :P
 * @author Ross Newby
 */
public class MapMenu extends Menu implements Clickable, Serializable {
    private static final long serialVersionUID = 3L;  //actually needed
    private Node[] nodes = new Node[NUMBER_OF_NODES];
    private NodeDescriptor[] nodeDesc = new NodeDescriptor[NUMBER_OF_NODES];
    public final static String NAME = "Map Menu";
    private final int map_id = 1;  // = new Random().nextInt( /* a value */);
    private final static int MAX_MOVE_DIST = 200,
            MIN_MOVE_DIST = 100;
    public final static int NUMBER_OF_NODES = 15;
    private ArrayList<String[]> csvContent;
    private ArrayList<Rect> lines;
    private final static int EXTRA_BOSS_DIFF = 5;
    //private Node currentNode;
    private NodeDescriptor currNodeDescriptor;
    private static Player player = null;
    /**
     *
     */
    public MapMenu() {
        super(NAME);

        super.addEntity(this);  //do first for events

        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "game-map.png"));

        Button backButton = new Button(50, 40, 80, 50, "BROWN", 200, "BACK", 15);
        backButton.addClickListener(this);
        addEntity(backButton);
        this.addClickListener(this);
        Node start = null, end;
        //generating map
        ArrayList<Node> tempNodes;
        int iterationCount = 0;

        if (Player.classType.equals("warrior")) {
            player = new Warrior();
        }
        if (Player.classType.equals("mage")) {
            player = new Mage();
        }
        if (Player.classType.equals("ranger")) {
            player = new Ranger();
        }

        // p.setClass(Player.classType);
        do {
            iterationCount++;

            lines = new ArrayList<>();
            tempNodes = new ArrayList<>();
            csvContent = CSVReader.read("assets" + Constants.SEP + "maps" + Constants.SEP + "map_" + map_id + ".csv");

            { // creating start and end nodes
                String[] row = null;
                for (int i = 0; i < csvContent.size(); i++) {
                    row = csvContent.get(i);

                    if (row[2].equals("start")) {
                        start = new Node("Starting Level", Integer.parseInt(row[0]), Integer.parseInt(row[1]), 10, Color.BLUE, Color.BLACK, 4, 300);
                        tempNodes.add(start);
                        start.setType("start");
                        csvContent.remove(row);
                    } else if (row[2].equals("end")) {
                        end = new Node("BOSS. Be warned.", Integer.parseInt(row[0]), Integer.parseInt(row[1]), 10, Color.RED, Color.BLACK, 4, 300);
                        tempNodes.add(end);
                        end.setType("end");
                        csvContent.remove(row);
                    }
                }
            }

            if (iterationCount == 1) {
                if (tempNodes.get(0) == null || tempNodes.get(1) == null) {
                    throw new RuntimeException("Invalid map csv, needs a start and a end node");
                }

                ArrayList<Node> allNodes = new ArrayList<>(tempNodes); //as two have been removed

                for (String[] row : csvContent) {
                    allNodes.add(new Node("It doesn't matter", Integer.parseInt(row[0]), Integer.parseInt(row[1]), 10, Color.WHITE, Color.BLACK, 4, 300));
                }

                if (!isMapValid(allNodes, false)) {
                    throw new RuntimeException("Invalid map csv: no way from the start to the end with the current values of MAX_MOVE_DIST and MIN_MOVE_DIST");
                }
            }

            int nodeCount = 3;
            while (tempNodes.size() < NUMBER_OF_NODES) {

                //Iterator it = csvContent.iterator();
                for (int i = 0; i < NUMBER_OF_NODES
                        && i < csvContent.size()
                        && tempNodes.size() < NUMBER_OF_NODES; i++) {
                    String[] row = csvContent.get(randomInt(0, csvContent.size() - 1));

                    boolean newNode = true;
                    for (Node node : tempNodes) {
                        if (node.getCenterX() == Integer.parseInt(row[0])
                                && node.getCenterY() == Integer.parseInt(row[1])) {
                            newNode = false;
                        }
                    }

                    if (newNode) {
                        Node node;
                        node = new Node("Level: " + String.valueOf(nodeCount), Integer.parseInt(row[0]), Integer.parseInt(row[1]), 10, Color.WHITE, Color.BLACK, 4, 300);

                        boolean okayToAdd = true;
                        for (Node anotherNode : tempNodes) {
                            double dist = Math.sqrt(Math.pow(anotherNode.getCenterX() - node.getCenterX(), 2) + Math.pow(anotherNode.getCenterY() - node.getCenterY(), 2));

                            if (dist < MAX_MOVE_DIST) {
                                if (dist > MIN_MOVE_DIST) {

                                    drawDottedLine(node, anotherNode, Color.BLACK);
                                } else {
                                    okayToAdd = false;
                                }
                            }
                        }

                        if (okayToAdd) {
                            //creating node descriptors
                            tempNodes.add(node);
                            node.setType(String.valueOf(nodeCount));
                            csvContent.remove(row);
                            nodeCount++;
                        }
                    }
                }
            }

        } while (!isMapValid(tempNodes, true));

        for (int i = 0; i < tempNodes.size(); i++) {
            this.nodes[i] = tempNodes.get(i);
        }

        for (Rect line : lines) {
            addEntity(line);
        }


        ArrayList<Node> nodesCopy = new ArrayList<>();
        nodesCopy.addAll(Arrays.asList(nodes));

        int i = 0;
        int maxDiff = 0;

        for (Node node : nodes) {
            addEntity(node);
            node.addClickListener(this);

            if (!node.getType().equals("end")) {
                clearTimes(nodesCopy);
                int difficulty = navigateTo(start, node, 0, nodesCopy);
                maxDiff = difficulty > maxDiff ? difficulty : maxDiff;
                nodeDesc[i] = new NodeDescriptor(node.getName(),  difficulty, node, 200, 150, this);
                addEntity(nodeDesc[i]);
            }

            if (!node.getType().equals("start")) {
                node.lock();
            } else {
                currNodeDescriptor = nodeDesc[i];
                node.unlock();
            }
            i++;
        }

        //setting the boss to be the most difficult
        for (Node node : nodes) {
            int k = 0;
            for (NodeDescriptor nodeDescriptor : nodeDesc) {
                if (nodeDescriptor == null) {
                    nodeDesc[k] = new NodeDescriptor(node.getName(), (maxDiff + EXTRA_BOSS_DIFF), node, 200, 150, this);

                    addEntity(nodeDesc[k]);
                }
                k++;
            }
        }
    }

    public boolean isMapValid(ArrayList<Node> nodes, boolean all) {
        Node start = null;
        Node end = null;
        for (Node node : nodes) {
            node.visitTime = Integer.MAX_VALUE;
            if (node.getType().equals("start")) {
                start = node;

            } else if (node.getType().equals("end")) {
                end = node;
            }
        }

        int fails = 0;
        if (all) {
            for (Node node : nodes) {
                clearTimes(nodes);
                if (navigateTo(start, node, 0, nodes) < 0) {
                    fails++;
                }
            }

            System.out.println("fails " + fails);

            return (fails == 0);
        } else {
            return navigateTo(start, end, 0, nodes) > 0;
        }
    }

    public void clearTimes (ArrayList<Node> nodes) {
        for (Node node : nodes) {
            node.visitTime = Integer.MAX_VALUE;
        }
    }

    public int navigateTo(Node frm, Node end, int stepCount, ArrayList<Node> nodes) {
        ++stepCount;

        if (frm.equals(end) ) {
            return stepCount;
        }

        int bestStep = Integer.MAX_VALUE;
        for (Node node : nodes) {
            double dist = Math.sqrt(Math.pow(frm.getCenterX() - node.getCenterX(), 2) + Math.pow(frm.getCenterY() - node.getCenterY(), 2));

            if (end.getType().equals("end") || !node.getType().equals("end")) {
                if (node.visitTime > stepCount && dist < MAX_MOVE_DIST && dist > MIN_MOVE_DIST) {
                    node.visitTime = stepCount;
                    Integer testStep = navigateTo(node, end, stepCount, nodes);
                    if (testStep > 0 && testStep < bestStep) {
                        bestStep = testStep;
                    }
                }
            }
        }

        if (bestStep < Integer.MAX_VALUE) {
            return bestStep;
        } else {
            return -1;
        }
    }

    public void levelComplete() {
        this.load();
        setCurrNodeDescriptor(currNodeDescriptor, false);
    }

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
            Node button = (Node) clickable;

            NodeDescriptor nodeDescriptor = null;
            for (NodeDescriptor nodeD : nodeDesc) {
                if (nodeD.getNode().equals(button)) {
                    nodeDescriptor = nodeD;
                }
            }
            //debugging
            System.out.println("Node " + button.getName() + " clicked");

            if (nodeDescriptor != null) {
                if (!button.isLocked()) {
                    if (nodeDescriptor.isLoaded()) {
                        nodeDescriptor.unload();
                    } else {
                        setCurrNodeDescriptor(nodeDescriptor, true);
                        nodeDescriptor.load();
                    }
                } else {
                    System.out.println("Node is locked");
                }
            }
        } else if (clickable instanceof MapMenu) {
            for (NodeDescriptor nodeDescriptor : nodeDesc) {
                if (nodeDescriptor.isLoaded()) {
                    nodeDescriptor.unload();
                }
            }
        }
    }

    public void setCurrNodeDescriptor(NodeDescriptor current, boolean keepLinksLocked) {
        currNodeDescriptor.getNode().setColour(Color.WHITE, Color.BLACK, 4, 300);

        Node node = current.getNode();
        node.unlock();

        if (!keepLinksLocked) {
            for (Node link : node.getLinks()) {
                link.unlock();
            }
        }

        node.setColour(Color.BLUE, Color.BLACK, 4, 300);

        currNodeDescriptor = current;
    }

    /*
     * This method uses trigonometry to draw a solid line between two nodes
     */
    public void drawLine(RenderWindow w, Node p1, Node p2, Color c){
        double length = Math.sqrt(Math.pow(p2.getCenterY() - p1.getCenterY(), 2) + Math.pow(p2.getCenterX() - p1.getCenterX(), 2)); //find the length the line should be using pythagoras theorem
        Rect line = new Rect(null, (p1.getCenterX() + p2.getCenterX()) / 2, (p1.getCenterY() + p2.getCenterY()) / 2, (int)length, 5, c, 300); //create the line between the two nodes

        lines.add(line);

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
        int noOfLines = (int)(length / 20); //has a line every 20p

        // System.out.println(noOfLines);
        ArrayList<Rect> lines = new ArrayList<>();


        double xInterval = (p2.getCenterX() - p1.getCenterX()) / noOfLines;
        double yInterval = (p2.getCenterY() - p1.getCenterY()) / noOfLines;


        //creating the lines at intervals between the 2 nodes
        for(int i = 0, j = 1; i < noOfLines; i++, j++){
            lines.add(new Rect(null, (int)(p1.getCenterX() + (xInterval * j)), (int)(p1.getCenterY() + (yInterval * j)), 10, 5, c, 300));
        }

        this.lines.addAll(lines);
        p1.addNodeLinks(lines, p2);
        p2.addNodeLinks(lines, p1);

        //setRotation the lines using trigonometry
        int l1 = p2.getCenterY()- p1.getCenterY();
        int l2 = p2.getCenterX() - p1.getCenterX();

        float trigAngle = (float)Math.toDegrees(Math.atan2(l1, l2)); //uses trig method 'SOH CAH TOA'
        for(int i = 0; i < noOfLines; i++){
            lines.get(i).rotate(trigAngle);
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


    @Override
    public void clicked(Event e) {
        buttonClicked(this, null);
    }

    //===============================================================================================================


    @Override
    public void addClickListener(ClickListener clickListener) {

    }

    /**
     * Checks whether the x and y parameters
     *
     * @param x - X coordinate to check
     * @param y - Y coordinate to check
     * @return - true if checkWithin, false if not;
     */
    @Override
    public boolean checkWithin(int x, int y) {
        for (Entity entity : getEntities()) {
            if (entity instanceof Clickable
                    && !(entity instanceof MapMenu)
                    && ((Clickable) entity).checkWithin(x, y)) {
                return false;
            }
        }
        return true;
    }

    public static Player getPlayer() {
        return player;
    }


    /**
     * Checks whether the x and y parameters passed in an Event obj
     *
     * @param e - the Event that caused this method call
     * @return - true if checkWithin, false if not;
     */
    @Override
    public boolean checkWithin(Event e) {
        return checkWithin(e.asMouseButtonEvent().position.x, e.asMouseButtonEvent().position.y);
    }
}
