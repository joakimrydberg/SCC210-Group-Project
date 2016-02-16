package game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Acts as the Driver, shouldn't do much but set up debug printing etc
 *
 */
public class Driver {
    MainMenu mainMenu;
    CharMenu charMenu;
    MapMenu mapMenu;
    private ArrayList<Drawer> drawers = new ArrayList<>( );
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
        window.create(new VideoMode(screenWidth, screenHeight), "Dungeons but not Dragons", WindowStyle.DEFAULT);

        window.setFramerateLimit(30); // Avoid excessive updates

        mainMenu = new MainMenu(window, this);
        mainMenu.load();

        ArrayList<Drawer> tempDrawers = new ArrayList<>();

        while (window.isOpen()) {
            window.clear(Color.WHITE);

            Iterable<Event> events = window.pollEvents();

            ListIterator<Drawer> it = drawers.listIterator();
            while (it.hasNext()) {  //keep this as a while. trust me there are reasons
                Drawer item = it.next();

                item.update(events);
            }
            window.display();
        }
    }


    public void addDrawer(Drawer drawer) {
        drawers.add(drawer);
    }

    public void removeDrawer(Drawer drawer) {
        drawers.remove(drawer);
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



/*
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
                    if (charMenu.isLoaded())
                        checkWithins(charMenu.getEntities(), event);

                    if (mainMenu.isLoaded())
                        checkWithins(mainMenu.getEntities(), event);

                    if (mapMenu.isLoaded())
                        checkWithins(mapMenu.getEntities(), event);
                }
            }
        }

    }

    private void checkWithins(ArrayList<Entity> array, Event event) {
        for (Entity entity : array)
            if (entity instanceof Clickable
                    && ((Clickable) entity).checkWithin(event)) {

                ((Clickable) entity).clicked(event);
            }
    }

    private void drawAll(ArrayList<Entity> array) {
        for (Entity entity : array) {
            if (entity instanceof MovingEntity)
                ((MovingEntity) entity).move();

            entity.draw();
        }
    }
    */
