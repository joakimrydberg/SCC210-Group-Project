package game;

import java.lang.Object;
import java.lang.String;
import java.lang.System;

/**
 * This class will provide functionality for turning on and off printing for debugging
 *
 * @author Alexander J Mills
 */
public class DebugPrinter {
    private static boolean print = false;

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
        if (print) print(caller, output);
    }

    /**
     * Print a String message
     *
     * @param caller - Calling class ( under normal operation pass "this" )
     * @param output - String to print
     */
    public static void print(Object caller, String output) {
        System.out.println("<" + caller.getClass().getName() + "> " + output);
    }
}
