/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Message extends Entity {
    private final static String FontFile  = "LucidaSansRegular.ttf";
    private String fontPath; // Where fonts were found

    public Message(RenderWindow window, int x, int y, int r, String message, Color c , int size) {
        super(window, message);

        if ((new File(Constants.JRE_FONT_PATH)).exists())
            fontPath = Constants.JRE_FONT_PATH;
        else
            fontPath = Constants.JDK_FONT_PATH;

        // Load the font		
        Font sansRegular = new Font( );
        try {
            sansRegular.loadFromFile(
                    Paths.get(fontPath+FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        Text text = new Text (message, sansRegular, size);
        text.setColor(c);

        setTopLeftX(x);
        setTopLeftY(y);

        setTransformable(text);
    }
}