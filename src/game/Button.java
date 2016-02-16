/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author newby
 */
public class Button extends Rect implements Clickable {
    private Entity menu;
    private ArrayList<ClickListener> clickListeners = new ArrayList<>();
    private static String FontFile  = "LucidaSansRegular.ttf";
    private String FontPath; // Where fonts were found

    public Button(RenderWindow w, int x, int y, int width, int height, Color c, int transparency, String text, int size) {
        super(w, text, x, y, width, height, c, transparency);

        if ((new File(Constants.JRE_FONT_PATH)).exists( ))
            FontPath = Constants.JRE_FONT_PATH;
        else
            FontPath = Constants.JDK_FONT_PATH;


        Font sansRegular = new Font( );
        try {
            sansRegular.loadFromFile(
                    Paths.get(FontPath+FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        Text textObj = new Text (text, sansRegular, size);
        textObj.setColor(Color.BLACK);
        //text.setStyle(Text.BOLD | Text.UNDERLINED);

        final FloatRect textBounds = textObj.getLocalBounds();

        addTransformable(textObj, width / 2, height / 2, (int)textBounds.width, (int)textBounds.height);
    }

    @Override
    public boolean checkWithin(Event e){
        Vector2i v = e.asMouseButtonEvent().position;
        return checkWithin(v.x, v.y);
    }

    @Override
    public boolean checkWithin(int x, int y) {
        return  getTopLeftX() < x
                && x < getTopLeftX() + getWidth()
                && getTopLeftY() < y
                && y < getTopLeftY() + getHeight();    //TODO NEEDSS MASSIVE WORK
    }


    public void clicked(Event e){
        for (ClickListener listener : clickListeners) {
            listener.buttonClicked(this, null);
        }
    }

    public void addClickListener(ClickListener clickListener) {
        clickListeners.add(clickListener);
    }
}
