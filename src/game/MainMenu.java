/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;

/**
 *
 * @author ryan
 */
public class MainMenu extends Menu {
    public int loaded = 0;

    public MainMenu(RenderWindow window) {
        super(window, "Main Menu");

        final Vector2i windowSize = window.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(window, centerX, centerY, windowSize.x, windowSize.y, "assets" + Constants.SEP + "art" + Constants.SEP + "main_menu_background.png"));

        Button btnNewGame = new Button(window, centerX, centerY, 250, 100, Color.WHITE, 100, "NEW GAME", 22);
        btnNewGame.addClickListener(this);
        addEntity(btnNewGame);
    }

    @Override
    public void buttonClicked(Button button, Object[] args) {
        if (button.getName().equals("NEW GAME")) {
            this.unload();

            new CharMenu(getWindow()).load();

            System.out.println("NEW GAME clicked");
        }
    }

}
