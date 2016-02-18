package game;

import java.awt.image.BufferedImage;

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


    public Player(String name) {
        super(name);
    }

    void setClass(String c){
        classType = c;
            System.out.println(c + " selected");
    }

    public void setStats(int a, int b, int c , int d, int e){

        attackPower = a;
        Intellect = b;
        Agility = c;
        Endurance = d;
        Vitality = e;
    }


    //Move character from sheet based on direction, 0-down, 1-right, 2-left, 3-up
    public BufferedImage[] moveAnimation(BufferedImage character, int dir)
    {
        BufferedImage[] characterMove = {SpriteSheetLoad.getSprite(0, dir, character), SpriteSheetLoad.getSprite(1, dir, character), SpriteSheetLoad.getSprite(0, dir, character), SpriteSheetLoad.getSprite(2, dir, character)};
        return characterMove;
    }

    public BufferedImage[] attackAnimation(BufferedImage character, int dir)
    {
        BufferedImage[] characterAttack = {SpriteSheetLoad.getSprite(4, dir, character), SpriteSheetLoad.getSprite(5, dir, character)};
        return characterAttack;
    }

    public BufferedImage[] hurtAnimation(BufferedImage character, int dir)
    {
        BufferedImage[] characterHurt = {SpriteSheetLoad.getSprite(3, dir, character)};
        return characterHurt;
    }
}
