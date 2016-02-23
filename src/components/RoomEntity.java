package components;

import abstract_classes.Drawer;
import game.LevelPart;
import org.jsfml.system.Vector2i;
import tools.Constants;

import java.util.ArrayList;

/**
 * @author josh
 * @date 18/02/16.
 */
public class RoomEntity extends Drawer {
    private final static String LEVEL_ID_DIR =  "assets" + Constants.SEP + "levels"  + Constants.SEP;
    private final static String LEVELPARTS =  "assets" + Constants.SEP + "levelparts"  + Constants.SEP;
    private LevelPart[][] levelParts;
    float partWidth,
            partHeight;

    public RoomEntity() {
        super("Room");
    }

    public RoomEntity create(LevelPart[][] levelPartsPassed) {
        this.levelParts = levelPartsPassed;

        if (levelParts.length != 11 || levelParts[0].length != 11 )
            throw new IllegalArgumentException();


        final Vector2i wSize = getWindow().getSize();
        partWidth = wSize.x / 11;
        partHeight = wSize.y / 11;

        DisplayedImagePart img;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                img = new DisplayedImagePart(i, j, partWidth, partHeight, LEVELPARTS + levelParts[i][j].getSpriteFileName());
                addPart(img);
                levelParts[i][j].displayed = true;
                img.rotate(levelParts[i][j].getRotation());
            }
        }

        return this;
    }

    public RoomEntity create(ArrayList<Object> levelPartsPassed) {

        if (levelPartsPassed.size() == 0 || !(levelPartsPassed.get(0) instanceof LevelPart)) {
            throw new IllegalArgumentException("Invalid ArrayList");
        }

        LevelPart[][] levelParts = new LevelPart[11][11];

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                levelParts[i][j] = (LevelPart) levelPartsPassed.get(i*11 + j);
            }
        }
        return create(levelParts);
    }


    public LevelPart getPart(int i, int j) {
        return levelParts[i][j];
    }

    public void setPart(LevelPart levelPart) {
        int i = levelPart.getRowNo(),
                j = levelPart.getColNo();

        levelPart.displayed = true;

        DisplayedImagePart img;
        img = new DisplayedImagePart(i, j, partWidth, partHeight, LEVELPARTS + levelPart.getSpriteFileName());
        img.rotate(levelPart.getRotation());

        replaceEntity(i * 11 + j, img);
        levelParts[i][j] = levelPart;
    }

    private void addPart(DisplayedImagePart part) {
        super.addEntity(part);
    }

    private class DisplayedImagePart extends Image {
        private int row = -1,
                col = -1;

        public DisplayedImagePart(int row, int col, float w, float h, String fileName) {
            super((int)(col * w + w/2), (int)(row * h + h/2), (int)w, (int)h, fileName);
            this.row = row;
            this.col = col;
        }
    }

    public LevelPart[][] getTiles() {
        return levelParts;
    }

    public Vector2i getPartSize() {
        Vector2i wSize = getWindow().getSize();

        return new Vector2i(wSize.x / 11, wSize.y / 11);
    }
}
