package game;

import interfaces.ClickListener;
import interfaces.Clickable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.window.event.Event;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by millsr3 on 16/02/2016.
 */
public class Player extends MovingEntity {

    private String classType;
    public int Agility = 0;
    public int Intellect = 0;
    public int attackPower = 0;
    public int Endurance = 0;
    public int Vitality = 0;


    public Player(RenderWindow window, String name) {
        super(window, name);
    }

    void setClass(String c){
        classType = c;

        if(c.equals("mage")){
            System.out.println("mage selected");
        }
    }

    public void setStats(int a, int b, int c , int d, int e){

        attackPower = a;
        Intellect = b;
        Agility = c;
        Endurance = d;
        Vitality = e;
    }
}
