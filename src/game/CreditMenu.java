package game;

import interfaces.Clickable;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Ross on 18/02/2016.
 */
public class CreditMenu extends Menu {
    public CreditMenu(){
        super("CREDITS");

        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "menu_background.png"));

        Button backButton = new Button(70, 40, 100, 50, Color.WHITE, 200 , "BACK", 15 );
        backButton.addClickListener(this);
        addEntity(backButton);

        SoundBuffer soundBuffer = new SoundBuffer();
        try {
            soundBuffer.loadFromFile(Paths.get("assets\\audio\\applause.wav"));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        Sound sound = new Sound();
        sound.setBuffer(soundBuffer);
        sound.play();

        addEntity(new Message(centerX, 200, 0, "Joshua Mills", Color.BLACK, 50));
        addEntity(new Message(centerX, 300, 0, "Ryan Mills", Color.BLACK, 50));
        addEntity(new Message(centerX, 400, 0, "Michael Monk", Color.BLACK, 50));
        addEntity(new Message(centerX, 500, 0, "Ross Newby", Color.BLACK, 50));
        addEntity(new Message(centerX, 600, 0, "Joakim Rydberg", Color.BLACK, 50));
    }

    @Override
    public void buttonClicked(Clickable clickable, Object[] args) {
        if (clickable instanceof  Button ) {
            Entity button = (Button) clickable;
            if (button.getName().equals("BACK")) {
                this.unload();

                Drawer mainMenu = Driver.getDrawer(MainMenu.NAME);
                if (mainMenu == null)
                    mainMenu = new MainMenu();

                mainMenu.load();

                System.out.println("Back clicked");
            }
        }
    }
}
