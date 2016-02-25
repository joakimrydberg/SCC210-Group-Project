package controllers;

import abstract_classes.Entity;
import components.Animation;
import game.SpriteSheetLoad;
import java.awt.image.BufferedImage;

/**
 * Created by monkm on 25/02/2016.
 */
public class HUD extends Entity {
    private BufferedImage newMageSheet = SpriteSheetLoad.loadSprite("HealthSheet");

    public HUD(String name) {
        super(name);
    }

    public void healthBar(int health, BufferedImage character){
        int currHealth = (int) Math.floor(health/2);
        int sheetPos = 50 - currHealth;
        BufferedImage[] currentHealth;
        if(currHealth > 35)
        {currentHealth = new BufferedImage[]{SpriteSheetLoad.getSprite(sheetPos, 0, character)};}
        else if(currHealth > 20) {
            {currentHealth = new BufferedImage[]{SpriteSheetLoad.getSprite(sheetPos, 0, character)};}
        }
        else if(currHealth > 5)
        {currentHealth = new BufferedImage[]{SpriteSheetLoad.getSprite(sheetPos, 2, character)};}
        else{currentHealth = new BufferedImage[]{SpriteSheetLoad.getSprite(sheetPos, 3, character)};}
        Animation currentHealthBar = new Animation(200, 200, 64, 128, currentHealth, 3);
    }



}
