/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import abstract_classes.Entity;
import components.Button;
import components.Image;
import components.Message;
import components.Node;
import game.Level;
import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;
import tools.Constants;

/**
 *
 * @author newby
 */
public class NodeDescriptor extends Menu implements Clickable {
    private final static String SEP = Constants.SEP;
    //public int loaded = 0;
    private MapMenu mapMenu;
    //ArrayList<ClickListener> clickListeners = new ArrayList<>();
    private static Image smallMenu;
	private int difficulty;
    private Node node;

    public NodeDescriptor(String name, int difficulty, Node n, int width, int height, MapMenu map) {
        super(name);
        this.mapMenu = map;
		this.difficulty = difficulty;

        this.node = n;
        RenderWindow w = getWindow();
        final Vector2i windowSize = new Vector2i(width, height);
        int centerX = n.getCenterX() + (width / 2), centerY = n.getCenterY() - (height / 2);

        //stop the descriptor appearing off the edge of the screen
        int topPoint = centerY - (height / 2);
        if(topPoint < 0) {
            centerY += Math.abs(topPoint);
        }
        int rightPoint = centerX + (width / 2);
        if(rightPoint > w.getSize().x){
            centerX -= (rightPoint - w.getSize().x);
        }

        setWidthHeight(width, height);
        setCenterY(centerY);
        setCenterX(centerX);
        smallMenu = new components.Image(centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu_titled.png");
        addEntity(smallMenu);

        addEntity(new Image(centerX, centerY, windowSize.x - 50, 30, "assets" + Constants.SEP + "art" + Constants.SEP + "game_label.png"));

        addEntity(new Message(centerX, centerY - 55, 0, name, Color.BLACK, 10)); //title

        Color txtCol;
        if (difficulty < MapMenu.NUMBER_OF_NODES / 3) {
            txtCol = new Color(0, 153, 0);
        } else if ((difficulty < (MapMenu.NUMBER_OF_NODES / 3) * 2)) {
            txtCol = new Color(255, 51, 0);
        } else {
            txtCol = Color.RED;
        }

        addEntity(new Message(centerX, centerY - 5, 0, "Difficulty: " + this.difficulty, txtCol, 15));

        Button btnClose = new Button(centerX - 50, centerY + 35, 30, 30, "RED", 200 , "<", 15 );
        btnClose.addClickListener(this);
        addEntity(btnClose);

        Button btnPlay = new Button(centerX + 20, centerY + 35, 90, 30, "GREEN", 200 , "PLAY", 15 );
        btnPlay.addClickListener(this);
        addEntity(btnPlay);
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof Button) {
            Entity button = (Button) clickable;
            if (button.getName().equals("PLAY")) {
                this.unload();
                mapMenu.unload();

                new Level(this.getName(), difficulty, mapMenu);

                System.out.println("Play clicked");
            }
            else if (button.getName().equals("<")) {
                this.unload();
                System.out.println("Close clicked");
            }
        }
    }

    public Node getNode() {
        return node;
    }

    @Override
    public void clicked(Event e) {
        //do nothing
    }

    @Override
    public void addClickListener(ClickListener clickListener) {
        //do nothing
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
        return  getTopLeftX() < x
                && x < getTopLeftX() + getWidth()
                && getTopLeftY() < y
                && y < getTopLeftY() + getHeight();
    }

    /**
     * Checks whether the x and y parameters passed in an Event obj
     *
     * @param e - the Event that caused this method call
     * @return - true if checkWithin, false if not;
     */
    @Override
    public boolean checkWithin(Event e) {
        Vector2i v = e.asMouseButtonEvent().position;
        return checkWithin(v.x, v.y);
    }
}
	
