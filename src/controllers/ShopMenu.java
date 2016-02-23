package controllers;

import components.ClickableImage;
import components.Image;
import components.Slot;
import interfaces.Clickable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import tools.Constants;

/**
 * Created by Ross on 23/02/2016.
 */
public class ShopMenu extends Menu {
    public final static String NAME = "Shop Menu";
    private ItemDescriptor[] itemDesc = new ItemDescriptor[42];
    private Slot[] slots = new Slot[42];
    private int count = 0;

    public ShopMenu(){
        super(NAME);

        RenderWindow w = getWindow();
        final Vector2i windowSize = w.getSize();
        final int centerX = windowSize.x / 2, centerY = windowSize.y / 2;

        addEntity(new Image(centerX, centerY, "assets" + Constants.SEP + "art" + Constants.SEP + "shop_image.png"));

        drawSlots();
    }

    private void drawSlots(){
        //
    }

    @Override
    public void buttonClicked(Clickable button, Object[] args) {

    }

    private void addSlot(String slot, int x, int y, String name) {
        itemDesc[count] = new ItemDescriptor(slot, x, y);

        Slot s = new Slot(x, y, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png", name, itemDesc[count]);
        s.addClickListener(this);
        addEntity(s);

        count++;
    }

    private void addSlot(String slot, int x, int y, int length, int height) {
        addEntity(new ClickableImage(x, y, length, height, "assets" + Constants.SEP + "art" + Constants.SEP + "slots" + Constants.SEP + slot + ".png"));
    }
}
