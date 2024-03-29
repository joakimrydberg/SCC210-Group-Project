package game;

import abstract_classes.Drawer;
import abstract_classes.Entity;
import controllers.MainMenu;
import levelcreator.LevelCreator;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import tools.Constants;
import tools.DebugPrinter;

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
        Entity.setWindow(window);  //important

        window.create(new VideoMode(screenWidth, screenHeight), "Dungeons but not Dragons", WindowStyle.TITLEBAR | WindowStyle.CLOSE);

        window.setFramerateLimit(30); // Avoid excessive updates

        MainMenu mainMenu;

        for (String cheat : args) {
            if (cheat.equals("levelcreator")
                    || cheat.equals("level creator")) {

                new Thread(LevelCreator::new).start(); //who even knew java had this syntax
            } else if (false) {

            }
        }

        mainMenu = new MainMenu();

        mainMenu.load();

      //  ArrayList<Drawer> tempDrawers = new ArrayList<>();

        while (window.isOpen()) {
            window.clear(Color.BLACK);

            Iterable<Event> tempEvents = window.pollEvents();
            ArrayList<Event> events = new ArrayList<>();

            for (Event e : tempEvents) {
                events.add(e);
            }

            for (int i = 0; i < drawers.size(); i++) {
                drawers.get(i).update(events);
            }

            try{
                Thread.sleep(20);
            } catch (Exception e ) {

            }
            window.display();
        }
    }

    public static void addDrawer(Drawer drawer) {
//        Drawer item;
//        for (int i = 0; i < drawers.size(); i++) {
//            item = drawers.get(i);
//            if (item.getName().equals(drawer.getName())) {
//                drawers.remove(item);
//            }
//        }

        //drawers.remove(drawer);
        drawers.add(drawer);
    }

	public static void removeDrawer(Drawer drawer) {
		drawers.remove(drawer);
	}

    public static Drawer getDrawer(String name, Class type) {

        boolean nameIgnore = (name == null),
                typeIgnore = (type == null);

        Drawer drawer;
        for (int i = 0; i < drawers.size(); i++) {
            drawer = drawers.get(i);
            if ((drawer.getClass().equals(type) || typeIgnore)
                    && (drawer.getName().equals(name) || nameIgnore)) {
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

    public void doNotRemoveMeUntilFriday() {
        int i = 9;
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
