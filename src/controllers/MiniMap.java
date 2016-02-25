package controllers;

import abstract_classes.Drawer;
import abstract_classes.Entity;
import components.Rect;
import game.Level;
import game.LevelPart;
import game.Room;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2i;
import tools.Constants;

/**
 * @author josh
 * @date 25/02/16.
 */
public class MiniMap extends Drawer/*extends RoomEntity*/ {
    private Level level = null;
    private LevelPart[][] levelParts = new LevelPart[Level.MAX_ROOMS][Level.MAX_ROOMS];
    private final static String LEVEL_ID_DIR =  "assets" + Constants.SEP + "levels"  + Constants.SEP;
    private final static String LEVELPARTS =  "assets" + Constants.SEP + "levelparts"  + Constants.SEP;
    //private LevelPart[][] levelParts;
    private int numberOfParts = 10;
    float partWidth = getWindow().getSize().x / numberOfParts,
            partHeight = getWindow().getSize().y / numberOfParts;


    public MiniMap() {
        super("MiniMap");

    }

    public void update(Level level, int x, int y, int w, int h) {

        setWidthHeight(w, h);
        setCenterX(x);
        setCenterY(y);

        partWidth = w / numberOfParts;
        partHeight = h / numberOfParts;

        this.level = level;

        LevelPart[][] levelParts = new LevelPart[Level.MAX_ROOMS][Level.MAX_ROOMS];
        Room[][] rooms = level.getRooms();

        for (int i = 0; i < Level.MAX_ROOMS; i++) {
            for (int j = 0; j < Level.MAX_ROOMS; j++) {
                if (rooms[i][j] != null) {
                    LevelPart levelPart = new LevelPart();

                    levelPart.setSpriteFileName(level.getRooms()[i][j].getName());
                    levelPart.setRowNo(i);
                    levelPart.setColNo(j);

                    levelParts[i][j] = levelPart;
                }
            }
        }
        create(levelParts);

    }

    public void setCurrentRoom(Vector2i vector2i) {
        final int i = vector2i.x,
                j = vector2i.y;


        if (levelParts[i][j] != null) {
            LevelPart levelPart = levelParts[i][j];

//            int i = levelPart.getRowNo(),
//                    j = levelPart.getColNo();

            levelPart.displayed = true;

            DisplayedImagePart img;
            img = new DisplayedImagePart(i, j, partWidth, partHeight, LEVELPARTS + levelPart.getSpriteFileName()+"cows", Color.BLUE);
            //img.rotate(levelPart.getRotation());

            int index = 0;
            for (Entity entity : getEntities()) {
                DisplayedImagePart imagePart = (DisplayedImagePart) entity;
                if (imagePart.row == i && imagePart.col == j) {
                    break;
                }
                index++;
            }
            replaceEntity(index, img);
            // levelParts[i][j] = levelPart;
        }
    }

    public MiniMap create(LevelPart[][] levelPartsPassed) {
        this.levelParts = levelPartsPassed;

        if (levelParts.length != numberOfParts || levelParts[0].length != numberOfParts )
            throw new IllegalArgumentException();


        DisplayedImagePart img;
        for (int i = 0; i < numberOfParts; i++) {
            for (int j = 0; j < numberOfParts; j++) {
                if (levelParts[i][j] != null) {
                    img = new DisplayedImagePart(i, j, partWidth, partHeight, LEVELPARTS + levelParts[i][j].getSpriteFileName(), Color.BLACK);
                    addPart(img);
                    levelParts[i][j].displayed = true;
                    //img.rotate(levelParts[i][j].getRotation());
                }
            }
        }

        setCurrentRoom(level.getCurrentRoomIndex());

        return this;
    }


    private void addPart(DisplayedImagePart part) {
        super.addEntity(part);
    }

    private class DisplayedImagePart extends Rect {
        private int row = -1,
                col = -1;

        public DisplayedImagePart(int row, int col, float w, float h, String fileName, Color color) {
            super("Map-Room-" + row + col, MiniMap.this.getTopLeftX() + (int)(col * w + w/2),
                    MiniMap.this.getTopLeftY() + (int)(row * h + h/2),
                    (int)w,
                    (int)h,
                    color,
                    100);

            this.row = row;
            this.col = col;
        }
    }
}
