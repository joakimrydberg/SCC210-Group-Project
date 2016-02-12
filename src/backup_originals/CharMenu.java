/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup_originals;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import game.Constants;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;

/**
 *
 * @author ryan
 */
public class CharMenu extends Actor{

    private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
    public ArrayList<Actor> act = new ArrayList<Actor>( );

    private String[] hairCols = new String[3];
    private Message[] m = new Message[6];

    private Message hairCol = new Message(725, 260, 0, "Red", Color.BLACK, 11);

    public int loaded = 0;

    public CharMenu(){
        add(new Rect(0, 0, 1024, 768, Color.BLACK, 128));
        add(new Rect(550, 50, 350, 700, Color.WHITE, 128));
        add(new Rect(550, 450, 350, 150, Color.WHITE, 128));
        Button b = new Button(685, 650, 100, 50, Color.WHITE, 100, "CREATE", 11);
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        try{
            File myfile = new File(url.toURI());
            File dir = myfile.getParentFile().getParentFile().getParentFile(); // strip off .jar file
            String a = (dir.toString() + Constants.SEP + "assets" + Constants.SEP + "art" + Constants.SEP + "magic.png");
            String d = (dir.toString() + Constants.SEP + "assets" + Constants.SEP + "art" + Constants.SEP + "ranged.png");
            String e = (dir.toString() + Constants.SEP + "assets" + Constants.SEP + "art" + Constants.SEP + "strength.png");
            add(new Image(600, 100, 0, a));
            add(new Image(725, 100, 0, e));
            add(new Image(850, 100, 0, d));
        } catch (URISyntaxException e) { }
        add(b);
        //System.out.println(getClass().getClassLoader().getResource("\\SCC210-Group-Project\\assets\\art\\magic.png"));
        m[0] = (new Message(575, 430, 0, "Stats", Color.BLACK, 20));
        m[1] = (new Message(675, 475, 0, "Attack power : 5", Color.BLACK, 12));
        m[2] = (new Message(675, 500, 0, "Intellect : 0", Color.BLACK, 12));
        m[3] = (new Message(675, 525, 0, "Agility : 0", Color.BLACK, 12));
        m[4] = (new Message(675, 550, 0, "Endurance : 5", Color.BLACK, 12));
        m[5] = (new Message(675, 575, 0, "Vitality : 5", Color.BLACK, 12));
        add(m[0]);
        add(m[1]);
        add(m[2]);
        add(m[3]);
        add(m[4]);
        add(m[5]);

        hairCols[0] = "Red";
        hairCols[1] = "Blue";
        hairCols[2] = "Green";

        add(new Button(775, 250, 25, 25, Color.WHITE, 100 , ">>", 9 ));
        add(new Button(650, 250, 25, 25, Color.WHITE, 100 , "<<", 9 ));
        add(hairCol);

    }
    public void add(Actor a){
        objs.add(a.obj);
        act.add(a);
    }

    void draw(RenderWindow w) {
        for (Actor a : act) {
            if(loaded==1){
                a.draw(w);
            }
        }
    }
    void performMove(){
        for(Actor a : act){
            a.performMove();
        }
    }
    void load(){
        loaded++;
    }
    void unload(){
        loaded--;
    }

    void moveRight(int i){
        hairCol.t.setString(hairCols[i]);

    }

    void moveLeft(int i){
        System.out.println("move left");
    }

    void setStats(int a, int b, int c , int d, int e){
        m[1].t.setString("Attack power : " + a);
        m[2].t.setString("Intellect : " + b);
        m[3].t.setString("Agility : " + c);
        m[4].t.setString("Endurance : " + d);
        m[5].t.setString("Vitality : " + e);
    }
}
