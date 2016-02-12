/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup_originals;

import java.util.ArrayList;

/**
 *
 * @author Ross Newby
 */
public class Driver {

    private static int screenWidth  = 1024;
    private static int screenHeight = 768;

    // The Java install comes with a set of fonts but these will
    // be on different filesystem paths depending on the version
    // of Java and whether the JDK or JRE version is being used.

    private static String JavaVersion =
            Runtime.class.getPackage( ).getImplementationVersion( );
    private static String JdkFontPath =
            "C:\\Program Files\\Java\\jdk" + JavaVersion +
            "\\jre\\lib\\fonts\\";
    private static String JreFontPath =
            "C:\\Program Files\\Java\\jre" + JavaVersion +
            "\\lib\\fonts\\";

    private static int fontSize     = 20;
    private static String FontFile  = "LucidaSansRegular.ttf";
    private static String ImageFile = "team.jpg";

    private String title   = "Not Set";

    private ArrayList<Actor> actors = new ArrayList<Actor>( );

    private Message[] m = new Message[6];
    private Actor charMenu;
    private Actor mainMenu;
    private Actor mapMenu;

    public Driver(int w, int h, String t){
        screenWidth = w;
        screenHeight = h;
        title = t;
    }

    /*
     * Runs the game
     */
    public void run(){

    }

    /*
     * Saves the game
     */
    public void save(){

    }

    /*
     * Loads a saved game
     */
    public void load(){

    }
}
