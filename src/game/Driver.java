package game;

/**
 * Acts as the Driver, shouldn't do much but set up debug printing etc
 *
 */
public class Driver {

    /**
     * Starts off the game
     *
     * @param args - Arguments..
     */
    public static void main(String args) {
        new Driver();
    }

    /**
     * Initialises the game and then lets it run
     *
     */
    public Driver() {
        setUpPrinting();

        //create window
    }

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
}
