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

    public Button(int x, int y, int width, int height, String colour, int transparency, String text, int size) {
        super(text, x, y, width, height, Color.WHITE, 0);
        //super(w, text, x, y, width, height, c, transparency);

        // Load image/ texture
        Texture imgTexture = new Texture( );
        try {
            imgTexture.loadFromFile(Paths.get("assets" + Constants.SEP + "art" + Constants.SEP + "menu_buttons" + Constants.SEP + colour +".png"));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }
        imgTexture.setSmooth(true);

        Sprite img = new Sprite(imgTexture);

        Vector2i imgSize = imgTexture.getSize();
        int widthTemp = (width < 0) ? imgSize.x : width;
        int heightTemp = (height < 0) ? imgSize.y : height;
        setWidthHeight(widthTemp, heightTemp);

        setCenterX(x);
        setCenterY(y);
        addTransformable(img, 0, 0, 0, 0); //not supplying w and h so the origin will be 0

        if (width >= 0)
            img.scale((float)(widthTemp / (imgSize.x/1.0)), (float)(heightTemp / (imgSize.y / 1.0)));

        // Load text
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

        //size = imgSize.y / 2;
        Text textObj = new Text (text, sansRegular, size);
        textObj.setColor(new Color(128, 0, 0));
        //text.setStyle(Text.BOLD | Text.UNDERLINED);

        final FloatRect textBounds = textObj.getLocalBounds();

        addTransformable(textObj, width / 2, height / 2 - 5, (int)textBounds.width, (int)textBounds.height);
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

    @Override
    public void clicked(Event e){
        MusicPlayer.play("button_click.wav");

        for (ClickListener listener : clickListeners) {
            listener.buttonClicked(this, null);
        }
    }

    @Override
    public void addClickListener(ClickListener clickListener) {
        clickListeners.add(clickListener);
    }
}
