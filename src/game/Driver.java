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
    private static ArrayList<Drawer> drawers = new ArrayList<>( );
    private static int screenWidth = 1024,
            screenHeight = 768;
    private static RenderWindow window;
    private static String FontPath;

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
     * Starts off the game
     *
     * @param args - Arguments..
     */
    public static void main(String[] args) {
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
        window = new RenderWindow();
        window.create(new VideoMode(screenWidth, screenHeight), "Dungeons but not Dragons", WindowStyle.DEFAULT);

        window.setFramerateLimit(30); // Avoid excessive updates

        MainMenu mainMenu = new MainMenu();
        mainMenu.load();


        new MusicPlayer();

        while (window.isOpen()) {
            window.clear(Color.WHITE);

            Iterable<Event> tempEvents = window.pollEvents();

            ArrayList<Event> events = new ArrayList<>();
            for (Event e : tempEvents){                       //necessary for reasons, see
                events.add(e);
            }

            for (int i = 0; i < drawers.size(); i++) {
                drawers.get(i).update(events);
            }

            window.display();
        }
    }


    public static void addDrawer(Drawer drawer) {
        Drawer item;
        Boolean found = false;
        for (int i = 0; i < drawers.size(); i++) {
            item = drawers.get(i);
            if (item.getName().equals(drawer.getName())) {
				found = true;
				i = drawers.size();
            }
        }
        if (!found) drawers.add(drawer);
    }

    public static Drawer getDrawer(String name) {
        Drawer drawer;
        for (int i = 0; i < drawers.size(); i++) {
            drawer = drawers.get(i);
            if (drawer.getName().equals(name)) {
                return drawer;
            }
        }

        return null;
    }

/*  Doubt we will need this, with maybe if we are certain that the drawer will never be opened again

    public static void removeDrawer(Drawer drawer) {
        drawers.remove(drawer);
    }*/

    public static RenderWindow getWindow() {
        return window;
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
