package controllers;

import components.Image;
import interfaces.Clickable;
import tools.Constants;

/**
 * Created by Ross on 21/02/2016.
 */
public class InventoryMenu extends Menu {
    public final static String NAME = "Inventory Menu";

    public InventoryMenu(){
        super(NAME);

        addSlot("LARGE", 500, 500);
    }

    private void addSlot(String slot, int x, int y){
        addEntity(new Image(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot +".png"));
    }

    private void addSlot(String slot, int x, int y, int length, int height){
        addEntity(new Image(x, y, length, height, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot +".png"));
    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {

    }
}
