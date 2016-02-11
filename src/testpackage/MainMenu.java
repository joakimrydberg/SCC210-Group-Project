/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpackage;

import java.util.ArrayList;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;

/**
 *
 * @author ryan
 */
public class MainMenu extends Actor {
            
            public int loaded = 0;
            private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
            public ArrayList<Actor> act = new ArrayList<Actor>( );
            
            public MainMenu(){
                
                add(new Rect(0, 0, 1024, 768, Color.BLACK, 128));
                Button b = new Button((1024 / 2) - 100, 768 / 2, 250, 100, Color.WHITE, 100, "NEW GAME", 22);
                add(b);
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
            
        }
