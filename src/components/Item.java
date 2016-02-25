package components;

/**
 * Created by newby on 23/02/2016.
 */
public class Item {

    private Image itemIcon;
    private String name = "Not Set", description = "Not Set", type = "HELMET";
    private int price = 1, value = 1;

    public Item(String n, Image icon, String desc, String ty, int pr, int val){
        name = n;
        itemIcon = icon;
        description = desc;
        type = ty;
        price = pr;
        value = val;
    }

    public String getName(){
        return name;
    }

    public Image getItemIcon() {
        return itemIcon;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getValue() {
        return value;
    }
}
