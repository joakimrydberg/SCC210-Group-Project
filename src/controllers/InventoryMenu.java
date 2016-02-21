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
    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {

    }
}
