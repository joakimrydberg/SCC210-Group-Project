/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import abstract_classes.Entity;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;
import tools.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Message extends Entity {
    private final static String FontFile  = "LucidaSansRegular.ttf";
    private String fontPath; // Where fonts were found

    public Message(int x, int y, int r, String message, Color c , int size) {
        super(message);

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

        final FloatRect textBounds = text.getLocalBounds();

        addTransformable(text, 0, 0, (int)textBounds.width, (int)textBounds.height);
    }
}
