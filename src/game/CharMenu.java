/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2i;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author ryan
 */
public class CharMenu extends Menu {
    private final static String SEP = Constants.SEP;

    private String[] hairCols = new String[3];
    private Message[] messages = new Message[6];

    private Message hairCol = null;

    public CharMenu(RenderWindow w) {
        super(w, "Character creation menu");

        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        hairCol = new Message(w, 700, 242, 0, "Red", Color.BLACK, 11);

        addEntity(new Rect(w, null, centerX, centerY, windowSize.x, windowSize.y, Color.BLACK, 128));
        addEntity(new Rect(w, null, 725, 375, 350, 700, Color.WHITE, 128));
        addEntity(new Rect(w, null, 725, 500, 350, 150, Color.WHITE, 128));
        Button createButton = new Button(w, 685, 650, 100, 50, Color.WHITE, 100, "CREATE", 11);

        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();

        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file

            final String a = (dir.toString() + SEP +"assets" + SEP +"art" + SEP +"magic.png");
            final String d = (dir.toString() + SEP + "assets" + SEP + "art" + SEP +"ranged.png");
            final String e = (dir.toString()  + SEP + "assets" + SEP + "art" + SEP + "strength.png");

            addEntity(new Image(w, 600, 100, 0, a));
            addEntity(new Image(w, 725, 100, 0, e));
            addEntity(new Image(w, 850, 100, 0, d));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        addEntity(createButton);

        // DebugPrinter.debugPrint(this, getClass().getClassLoader().getResource("\\SCC210-Group-Project\\assets\\art\\magic.png").toString());
        messages[0] = (new Message(w, 575, 430, 0, "Stats", Color.BLACK, 20));
        messages[1] = (new Message(w, 675, 475, 0, "Attack power : 5", Color.BLACK, 12));
        messages[2] = (new Message(w, 675, 500, 0, "Intellect : 0", Color.BLACK, 12));
        messages[3] = (new Message(w, 675, 525, 0, "Agility : 0", Color.BLACK, 12));
        messages[4] = (new Message(w, 675, 550, 0, "Endurance : 5", Color.BLACK, 12));
        messages[5] = (new Message(w, 675, 575, 0, "Vitality : 5", Color.BLACK, 12));

        addEntity(messages[0]);
        addEntity(messages[1]);
        addEntity(messages[2]);
        addEntity(messages[3]);
        addEntity(messages[4]);
        addEntity(messages[5]);

        hairCols[0] = "Red";
        hairCols[1] = "Blue";
        hairCols[2] = "Green";

        addEntity(new Button(w, 775, 250, 25, 25, Color.WHITE, 100, ">>", 9 ));
        addEntity(new Button(w, 650, 250, 25, 25, Color.WHITE, 100, "<<", 9 ));
        addEntity(hairCol);

    }

    public void moveRight(int i){
        (((Text)hairCol.getTransformable(0))).setString(hairCols[i]);
    }

    public void moveLeft(int i){
        System.out.println("move left");
    }

    public void setStats(int a, int b, int c , int d, int e){
        ((Text)messages[1].getTransformable(0)).setString("Attack power : " + a);
        ((Text)messages[2].getTransformable(0)).setString("Intellect : " + b);
        ((Text)messages[3].getTransformable(0)).setString("Agility : " + c);
        ((Text)messages[4].getTransformable(0)).setString("Endurance : " + d);
        ((Text)messages[5].getTransformable(0)).setString("Vitality : " + e);
    }
}
