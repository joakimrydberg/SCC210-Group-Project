/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpackage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;

/**
 *
 * @author newby
 */
public class Message extends Actor {
    public Text t;

    ////////////////////////////////////////////////////////////////////////////////
    private static String JavaVersion = 
            Runtime.class.getPackage( ).getImplementationVersion( );
    private static String JdkFontPath =
            "C:\\Program Files\\Java\\jdk" + JavaVersion +
            "\\jre\\lib\\fonts\\";
    private static String JreFontPath =
            "C:\\Program Files\\Java\\jre" + JavaVersion +
            "\\lib\\fonts\\";

    private int fontSize     = 20;
    private String FontFile  = "LucidaSansRegular.ttf";
    private String FontPath; // Where fonts were found
    ////////////////////////////////////////////////////////////////////////////////
    
    public Message(int x, int y, int r, String message, Color c , int size) {
        if ((new File(JreFontPath)).exists( ))
            FontPath = JreFontPath;
        else
            FontPath = JdkFontPath;

        // Load the font		
        Font sansRegular = new Font( );	
        try {		
            sansRegular.loadFromFile(			
                    Paths.get(FontPath+FontFile));
        } catch (IOException ex) {	
            ex.printStackTrace( );	
        }
		
        Text text = new Text (message, sansRegular, size);		
        text.setColor(c);		
        //text.setStyle(Text.BOLD | Text.UNDERLINED);
	
        FloatRect textBounds = text.getLocalBounds( );
	// Find middle and set as origin/ reference point
	text.setOrigin(textBounds.width / 2,
			textBounds.height / 2);

	this.x = x;
	this.y = y;
	this.r = r;
        t = text;
	obj = text;
                        
        //System.out.println(path);
        //String decodedPath = URLDecoder.decode(path, "UTF-8");
    }
}
