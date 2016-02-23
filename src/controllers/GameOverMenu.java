package controllers;

import components.Button;
import components.Image;
import components.Message;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import tools.Constants;

/**
 * Created by Ryan on 23/02/2016.
 */
public class GameOverMenu extends Menu{

    public GameOverMenu(){

        super("Game Over");
        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "pause_menu_background.png"));

        addEntity(new Message(centerX, centerY, 0, "You died", Color.RED, 30));
        addEntity(new Message(centerX, centerY + 100, 0, "Level :", Color.RED, 30));

        Button btn = new Button(centerX, centerY + 200, 200, 40, "GREEN", 200, "Play Again", 15);
        btn.addClickListener(this);
        addEntity(btn);
    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {

        loadDrawer(MainMenu.class);
        unload();
    }
}
