package controllers;

import abstract_classes.Entity;
import components.Button;
import components.Image;
import components.Message;
import interfaces.Clickable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import tools.Constants;
import tools.MusicPlayer;

/**
 * Created by Ross on 18/02/2016.
 */
public class Instructions extends Menu {
    private Image arrowKeys = new Image(200, 300, 300, 195, "assets" + Constants.SEP + "art" + Constants.SEP + "arrowKeys.png");
    private Image cursor = new Image(700, 500, 100, 195, "assets" + Constants.SEP + "art" + Constants.SEP + "cursor.png");
    private Image map = new Image(500, 370, 500, 350, "assets" + Constants.SEP + "art" + Constants.SEP + "game-map.png");
    //room image

    public Message ins1 = new Message(600, 250, 0, "Use the arrow keys to move", Color.WHITE, 40);
    public Message ins2 = new Message(500, 500, 0, "Click to attack", Color.WHITE, 40);
    public Message ins3 = new Message(500, 150, 0, "Use the map to navigate to the final boss battle", Color.WHITE, 30);
    public Message ins4 = new Message(420, 590, 0, "Purchase items and customise your player ^", Color.WHITE, 30);
    public Message ins5 = new Message(500, 150, 0, "Purchase items and customise your player ^", Color.WHITE, 30);
    public Message ins6 = new Message(600, 590, 0, "Click P to pause", Color.WHITE, 30);

    Button btnNext;

    private int click = 0;

    public Instructions(){
        super("INSTRUCTIONS");

        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "menu_background.png"));

        //BACK button
        addEntity(new Image(200, 50, 180, 110, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu.png")); //background to button - just to look good
        Button btnBack = new Button(200, 65, 120, 40, "RED", 200, "BACK", 15);
        btnBack.addClickListener(this);
        addEntity(btnBack);

        //NEXT button
        addEntity(new Image(950, windowSize.y / 2, 180, 110, "assets" + Constants.SEP + "art" + Constants.SEP + "game_menu.png")); //background to button - just to look good
        btnNext = new Button(950, windowSize.y / 2, 120, 40, "YELLOW", 200, "NEXT >", 15);
        btnNext.addClickListener(this);
        addEntity(btnNext);

        MusicPlayer.stop("main_menu_loop.wav");

        addEntity(ins1);
        addEntity(ins2);
        addEntity(ins3);
        addEntity(ins4);
        addEntity(ins5);
        addEntity(ins6);

        addEntity(arrowKeys);
        addEntity(cursor);
        addEntity(map);

        map.hide();
        ins3.setText("");
        ins4.setText("");
        ins5.setText("");
        ins6.setText("");
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof  Button ) {
            Entity button = (Button) clickable;
            if (button.getName().equals("BACK")) {
                this.unload();

                loadDrawer(MainMenu.class);

                MusicPlayer.play("main_menu_loop.wav", true);

                System.out.println("BACK clicked");
            } else if (button.getName().equals("NEXT >")) {
                if(click == 0) {
                    arrowKeys.hide();
                    cursor.hide();

                    ins1.setText("");
                    ins2.setText("");

                    ins3.setText("Use the map to navigate to the final boss battle");
                    ins4.setText("Purchase items and customise your player ^");
                    map.show();

                    System.out.println("NEXT clicked");
                    click++;
                } else if (click == 1){
                    map.hide();

                    ins3.setText("");
                    ins4.setText("");

                    ins5.setText("Fight your way through the rooms, find the finish!");
                    ins6.setText("Click P to pause");

                    System.out.println("NEXT clicked");
                    click++;

                    btnNext.hide();
                }
            }
        }
    }
}
