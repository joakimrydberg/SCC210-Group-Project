/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Transformable;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author ryan
 */
public class CharMenu extends EntityHolder {
    private final static String SEP = Constants.SEP;
    private ArrayList<Transformable> objs = new ArrayList<>( );

    private String[] hairCols = new String[3];
    private Message[] messages = new Message[6];

    private Message hairCol = null;

    public int loaded = 0;

    public CharMenu(RenderWindow w) {
        super(w, "Character creation menu");

        hairCol = new Message(w, 725, 260, 0, "Red", Color.BLACK, 11);

        add(new Rect(w, null, 0, 0, 1024, 768, Color.BLACK, 128));
        add(new Rect(w, null, 550, 50, 350, 700, Color.WHITE, 128));
        add(new Rect(w, null, 550, 450, 350, 150, Color.WHITE, 128));
        Button createButton = new Button(w, 685, 650, 100, 50, Color.WHITE, 100, "CREATE", 11);

        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();

        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file

            final String a = (dir.toString() + SEP +"assets" + SEP +"art" + SEP +"magic.png");
            final String d = (dir.toString() + SEP + "assets" + SEP + "art" + SEP +"ranged.png");
            final String e = (dir.toString()  + SEP + "assets" + SEP + "art" + SEP + "strength.png");

            add(new Image(w, 600, 100, 0, a));
            add(new Image(w, 725, 100, 0, e));
            add(new Image(w, 850, 100, 0, d));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        add(createButton);

        // DebugPrinter.debugPrint(this, getClass().getClassLoader().getResource("\\SCC210-Group-Project\\assets\\art\\magic.png").toString());
        messages[0] = (new Message(w, 575, 430, 0, "Stats", Color.BLACK, 20));
        messages[1] = (new Message(w, 675, 475, 0, "Attack power : 5", Color.BLACK, 12));
        messages[2] = (new Message(w, 675, 500, 0, "Intellect : 0", Color.BLACK, 12));
        messages[3] = (new Message(w, 675, 525, 0, "Agility : 0", Color.BLACK, 12));
        messages[4] = (new Message(w, 675, 550, 0, "Endurance : 5", Color.BLACK, 12));
        messages[5] = (new Message(w, 675, 575, 0, "Vitality : 5", Color.BLACK, 12));

        add(messages[0]);
        add(messages[1]);
        add(messages[2]);
        add(messages[3]);
        add(messages[4]);
        add(messages[5]);

        hairCols[0] = "Red";
        hairCols[1] = "Blue";
        hairCols[2] = "Green";

        add(new Button(w, 775, 250, 25, 25, Color.WHITE, 100 , ">>", 9 ));
        add(new Button(w, 650, 250, 25, 25, Color.WHITE, 100 , "<<", 9 ));
        add(hairCol);

    }
    public void add(Entity a){
        objs.add(a.getTransformable());
        addEntity(a);
    }

    @Override
    public void draw() {
        for (Entity a : getEntities())
            if(loaded==1)
                a.draw();
    }

    /* As far as I can tell no need for this in a menu

        public void performMove(){
            for(MovingEntity a : act){
                a.move(0, 0);
            }
        }
    */

    public void load(){
        loaded++;
    }

    public void unload(){
        loaded--;
    }

    public void moveRight(int i){
        ((Text)hairCol.getTransformable()).setString(hairCols[i]);
    }

    public void moveLeft(int i){
        System.out.println("move left");
    }

    public void setStats(int a, int b, int c , int d, int e){
        ((Text)messages[1].getTransformable()).setString("Attack power : " + a);
        ((Text)messages[2].getTransformable()).setString("Intellect : " + b);
        ((Text)messages[3].getTransformable()).setString("Agility : " + c);
        ((Text)messages[4].getTransformable()).setString("Endurance : " + d);
        ((Text)messages[5].getTransformable()).setString("Vitality : " + e);
    }
}
