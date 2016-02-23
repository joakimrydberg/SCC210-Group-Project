package controllers;

import components.Image;
import interfaces.Clickable;
import tools.Constants;

/**
 * Created by Ross on 21/02/2016.
 */
public class StatsMenu extends Menu {
    public final static String NAME = "Stats Menu";

    public StatsMenu(){
        super(NAME);
        addEntity(new Image(625, 350, 520, 470, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + "PANEL.png"));
    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {

    }
}
