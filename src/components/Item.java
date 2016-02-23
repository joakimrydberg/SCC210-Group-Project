package components;

/**
 * Created by newby on 23/02/2016.
 */
public class Item {

    private Image itemIcon;
    private String name = "Not Set", description = "Not Set";

    public Item(String n, Image icon, String desc){
        name = n;
        itemIcon = icon;
        description = desc;
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
}
