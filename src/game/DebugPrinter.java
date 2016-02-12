package game;

import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.util.ArrayList;

/**
 * This class will provide functionality for turning on and off printing for debugging
 *
 * @author Alexander J Mills
 */
public class DebugPrinter {
    private static boolean print = false;
    private static ArrayList<String> hideList = new ArrayList<>(); //List of classes to hide debug info

    /**
     * Turn on and off debug printing
     *
     * @param debug - True to turn on, False to turn off
     */
    public static void setDebug(boolean debug) {
        print = debug;
    }

    /**
     * Print a String message, if and only if, debug printing is turned on. ( setDebug(bool) )
     *
     * @param caller - Calling class ( under normal operation pass "this" )
     * @param output - String to print
     */
    public static void debugPrint(Object caller, String output) {
        if (hideList.contains(caller.getClass().getName())) return;

        if (print) print(caller, output);
    }

    /**
     * Print a String message
     *
     * @param caller - Calling class ( under normal operation pass "this" )
     * @param output - String to print
     */
    public static void print(Object caller, String output) {
        //System.out.println("<" + caller.getClass().getSimpleName() + " | " + caller.toString() + "> " + output);
    }

    /**
     * Adds a class to hide all debug printing from
     *
     * @param type - String returned from obj.getClass()
     */
    public static void addHiddenClass(Class type) {
        hideList.add(type.getName());
    }

    /**
     * Removes all classes from the hidden list
     */
    public static void clearHiddenList() {
        for (int i = 0; i < hideList.size(); i++)
            hideList.remove(i);
    }
}
