package controllers;

import components.Button;
import interfaces.Clickable;

/**
 * Created by newby on 23/02/2016.
 */
public class ItemMenu extends Menu {
    public final static String NAME = "Pause Menu";

    public ItemMenu(){
        super(NAME);

        Button btnEquipt = new Button(800, 600 + 35, 90, 30, "YELLOW", 200 , "EQUIPT", 15 );
        btnEquipt.addClickListener(this);
        addEntity(btnEquipt);

        Button btnDiscard = new Button(700, 600 + 35, 90, 30, "RED", 200 , "DISCARD", 15 );
        btnDiscard.addClickListener(this);
        addEntity(btnDiscard);
    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {
        //
    }
}
