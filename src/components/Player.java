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
    public static BufferedImage[] characterStill;
    public static Animation warriorWalk;
    public static Animation currAnimation;

    public Player(String name) {
        super(name);
    }

    public void setClass(String c){
        classType = c;

        if(c.equals("mage")){
            System.out.println("mage selected");
            theSpriteSheet = SpriteSheetLoad.loadSprite("MageMaleSheet");
            characterStill = new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, theSpriteSheet)};
            warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            currAnimation = warriorWalk;
            currAnimation.stop();
            currAnimation = warriorWalk;
            currAnimation.start();
        }
        if(c.equals("warrior")){
            System.out.println("warrior selected");
            theSpriteSheet = SpriteSheetLoad.loadSprite("WarriorMaleSheet");
            characterStill = new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, theSpriteSheet)};
            warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            currAnimation = warriorWalk;
            currAnimation.stop();
            currAnimation = warriorWalk;
            currAnimation.start();
        }
        if(c.equals("ranger")){
            System.out.println("ranger selected");
            theSpriteSheet  = SpriteSheetLoad.loadSprite("RangerMaleSheet");
            characterStill = new BufferedImage[]{SpriteSheetLoad.getSprite(0, 0, theSpriteSheet)};
            warriorWalk = new Animation(200, 200, 64, 128, characterStill, 1);
            currAnimation = warriorWalk;
            currAnimation.stop();
            currAnimation = warriorWalk;
            currAnimation.start();
        }

    }

    public void setStats(int a, int b, int c , int d, int e){

        attackPower = a;
        Intellect = b;
        Agility = c;
        Endurance = d;
        Vitality = e;
    }

    public void move(BufferedImage character, int dir){
        super.move();

        currAnimation.stop();
        BufferedImage[] characterMove = {SpriteSheetLoad.getSprite(0, dir, character), SpriteSheetLoad.getSprite(1, dir, character), SpriteSheetLoad.getSprite(0, dir, character), SpriteSheetLoad.getSprite(2, dir, character)};
        warriorWalk = new Animation(200, 200, 64, 128, characterMove, 1);
        currAnimation = warriorWalk;
        if(dir==0)
            currAnimation.updateMove(0, 1);
        else if(dir == 1)
            currAnimation.updateMove(1, 0);
        else if(dir == 2)
            currAnimation.updateMove(-1, 0);
        else if(dir == 3)
            currAnimation.updateMove(0, -1);
        currAnimation.start();
    }

    //Move character from sheet based on direction, 0-down, 1-right, 2-left, 3-up
/*    public static BufferedImage[] charMove(BufferedImage character, int dir)
    {

    }*/

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
