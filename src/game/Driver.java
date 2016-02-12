package game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import java.io.File;
import java.util.ArrayList;

/**
 * Acts as the Driver, shouldn't do much but set up debug printing etc
 *
 */
public class Driver {
    private ArrayList<Entity> entities = new ArrayList<>( );
    private int screenWidth = 1024,
                screenHeight = 768;

    private String FontPath;

    /**
     * Set up for printing
     *
     */
    private static void setUpPrinting() {
        DebugPrinter.setDebug(true);

        //DebugPrinter.addHiddenClass(Entity.class);
        //DebugPrinter.addHiddenClass(MovingEntity.class);
        //DebugPrinter.addHiddenClass(FileHandling.class);
    }

    /**
     * Initialises the game and then lets it run
     *
     */
    public Driver() {
        setUpPrinting();

        run();
    }


    private void run() {
        //create window
        //
        // Check whether we're running from a JDK or JRE install
        // ...and set FontPath appropriately.
        //
        if ((new File(Constants.JRE_FONT_PATH)).exists()) FontPath = Constants.JDK_FONT_PATH;
        else FontPath = Constants.JDK_FONT_PATH;

        //
        // Create a window
        //
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(screenWidth, screenHeight), "Game Woo", WindowStyle.DEFAULT);

        window.setFramerateLimit(30); // Avoid excessive updates

        //
        // Create some entities

        //entities.add(new Message(screenWidth / 2, screenHeight / 2,
        //			10, Message, Color.BLACK));

                /*
		entities.add(new Image(screenWidth / 4, screenHeight / 4,
					10, ImageFile));
		entities.add(new Bubble(500, 500, 20, Color.MAGENTA, 128));
		entities.add(new Bubble(600, 600, 20, Color.YELLOW,  128));
		entities.add(new Bubble(500, 600, 20, Color.BLUE,    128));
		entities.add(new Bubble(600, 500, 20, Color.BLACK,   128));
                */

        CharMenu charMenu = new CharMenu(window);
        MainMenu mainMenu = new MainMenu(window);
        MapMenu mapMenu = new MapMenu(window);

        //mapMenu.load();
        //charMenu.load();
        mainMenu.load();

        entities.add(charMenu);
        entities.add(mainMenu);
        entities.add(mapMenu);

        // Main loop
        while (window.isOpen()) {
            // Clear the screen
            window.clear(Color.WHITE);

            // Move all the entities around
            drawAll(entities);

            // Update the display with any changes
            window.display();

            // Handle any events
            for (Event event : window.pollEvents()) {
                if (event.type == Event.Type.CLOSED)      // the user pressed the close button
                    window.close();

                if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                    //   if(event.asMouseButtonEvent()){
                    if (charMenu.loaded != 0)
                        checkWithins(charMenu.getEntities(), event);

                    if (mainMenu.loaded != 0)
                        checkWithins(mainMenu.getEntities(), event);

                    if (mapMenu.loaded != 0)
                        checkWithins(mapMenu.getEntities(), event);
                }
            }
        }
    }

    private void checkWithins(ArrayList<Entity> array, Event event) { //TODO rename to something remotely appropriate
        for (Entity entity : array)
            if (entity instanceof CollidingEntity
                    && ((CollidingEntity) entity).colliding(event))
                entity.clicked(event.asMouseButtonEvent().position);
    }

    private void drawAll(ArrayList<Entity> array) {  //TODO rename to something remotely appropriate
        for (Entity entity : array) {
            if (entity instanceof MovingEntity)
                ((MovingEntity) entity).move();
            entity.draw();

        }
    }

    /**
     * Starts off the game
     *
     * @param args - Arguments..
     */
    public static void main(String[] args) {
        new Driver();
    }
}
