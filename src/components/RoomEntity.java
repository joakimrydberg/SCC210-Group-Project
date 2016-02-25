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
    private int numberOfParts = 11;
    float partWidth = getWindow().getSize().x / numberOfParts,
            partHeight = getWindow().getSize().y / numberOfParts;
    
    public static int roomCounter = 0;


    public RoomEntity() {
        super("Room" + roomCounter++);

        setTopLeftX(0);
        setTopLeftY(0);
    }

    public RoomEntity(int numberOfParts, int x, int y, int w, int h) {
        super("Not a room, a map");
        this.numberOfParts = numberOfParts;

        setCenterX(x);
        setCenterY(y);
        setWidthHeight(w, h);

        partWidth = w / numberOfParts;
        partHeight = h / numberOfParts;
    }

    public RoomEntity create(LevelPart[][] levelPartsPassed) {
        this.levelParts = levelPartsPassed;

        if (levelParts.length != numberOfParts || levelParts[0].length != numberOfParts )
            throw new IllegalArgumentException();


        DisplayedImagePart img;
        for (int i = 0; i < numberOfParts; i++) {
            for (int j = 0; j < numberOfParts; j++) {
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

        LevelPart[][] levelParts = new LevelPart[numberOfParts][numberOfParts];

        for (int i = 0; i < numberOfParts; i++) {
            for (int j = 0; j < numberOfParts; j++) {
                levelParts[i][j] = (LevelPart) levelPartsPassed.get(i*numberOfParts + j);
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

        replaceEntity(i * numberOfParts + j, img);
        levelParts[i][j] = levelPart;
    }

    private void addPart(DisplayedImagePart part) {
        super.addEntity(part);
    }

    private class DisplayedImagePart extends Image {
        private int row = -1,
                col = -1;

        public DisplayedImagePart(int row, int col, float w, float h, String fileName) {
            super(RoomEntity.this.getTopLeftX() + (int)(col * w + w/2),
                    RoomEntity.this.getTopLeftY() + (int)(row * h + h/2),
                    (int)w,
                    (int)h,
                    fileName);

            this.row = row;
            this.col = col;
        }
    }

    public LevelPart[][] getTiles() {
        return levelParts;
    }

    public Vector2i getPartSize() {
        Vector2i wSize = getWindow().getSize();

        return new Vector2i(wSize.x / numberOfParts, wSize.y / numberOfParts);
    }

	public void resetCounter() {
		roomCounter = 0;
	}
}
