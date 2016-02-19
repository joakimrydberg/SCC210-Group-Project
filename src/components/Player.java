package components;

import abstract_classes.MovingEntity;
import game.SpriteSheetLoad;

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
    public int Health = 100;
    SpriteSheetLoad ourSpriteSheet = new SpriteSheetLoad(64, 128);
     static BufferedImage theSpriteSheet;
     static BufferedImage[] warrior = Player.charMove(theSpriteSheet, 0);
     public static Animation warriorWalk = new Animation(200, 200, 64, 128, warrior, 1);
    public static Animation currAnimation = warriorWalk;

    public Player(String name) {
        super(name);
        currAnimation.stop();
        currAnimation = warriorWalk;
        currAnimation.start();
    }

    void setClass(String c){
        classType = c;

        if(c.equals("mage")){
            System.out.println("mage selected");
            theSpriteSheet = SpriteSheetLoad.loadSprite("MageMaleSheet");
        }
        if(c.equals("warrior")){
            System.out.println("warrior selected");
            theSpriteSheet = SpriteSheetLoad.loadSprite("WarriorMaleSheet");
        }
        if(c.equals("ranger")){
            System.out.println("ranger selected");
            theSpriteSheet  = SpriteSheetLoad.loadSprite("RangerMaleSheet");

        }

    }

    public void setStats(int a, int b, int c , int d, int e){

        attackPower = a;
        Intellect = b;
        Agility = c;
        Endurance = d;
        Vitality = e;
    }


    //Move character from sheet based on direction, 0-down, 1-right, 2-left, 3-right
    public static BufferedImage[] charMove(BufferedImage character, int dir)
    {
        BufferedImage[] characterMove = {SpriteSheetLoad.getSprite(0, dir, character), SpriteSheetLoad.getSprite(1, dir, character), SpriteSheetLoad.getSprite(0, dir, character), SpriteSheetLoad.getSprite(2, dir, character)};
        return characterMove;
    }

    public BufferedImage[] charAttack(BufferedImage character, int dir)
    {
        BufferedImage[] characterAttack = {SpriteSheetLoad.getSprite(4, dir, character), SpriteSheetLoad.getSprite(5, dir, character)};
        return characterAttack;
    }

    public BufferedImage[] charHurt(BufferedImage character, int dir)
    {
        BufferedImage[] characterHurt = {SpriteSheetLoad.getSprite(3, dir, character)};
        this.Health = this.Health-20;
        return characterHurt;
    }
}
