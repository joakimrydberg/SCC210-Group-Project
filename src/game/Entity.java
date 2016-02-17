package game;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Transformable;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This class represents an Entity in the game. Something that will appear on the screen.
 *
 * @author Alexander J Mills
 */
public class Entity  {
    private String name = "";
    private int x = 0, y = 0;   //current x and y coordinates
    private int w = -1, h = -1; // current width and height of the Entity
    private ArrayList<TransformableHolder> transformableHolders = new ArrayList<>();
    //private RenderWindow window = null;
    public boolean hidden = false;
    /**
     * Creates a new entity, with a given name
     *
     * @param name - String name for the Entity
     * @param centerX - int X position of the center of the entity
     * @param centerY - int Y position of the center of the entity
     * @param width - int width
     * @param height - int height
     */
    public Entity(String name, int centerX, int centerY, int width, int height) {
        super();

        setName(name);
        setWidthHeight(width, height);
        setCenterX(centerX);
        setCenterY(centerY);
    }

    /**
     * Creates a new entity
     *
     * @param name - String name for the Entity
     */
    public Entity(String name) {
        super();

        this.name = name;
    }

    /**
     * Draws the Entity on to the screen
     *
     */
    public void draw() {
        for (TransformableHolder transformableHolder : transformableHolders) {
            //DebugPrinter.debugPrint(this, "drawing: x:" + getTopLeftX() + " y:" + getTopLeftY() + " w:" + getWidth() + " h:" + getHeight());
            if (!transformableHolder.hidden)
                getWindow().draw((Drawable) transformableHolder.transformable);
        }
    }

    /**
     * Rotates the entity
     *
     * see Transformable's implementations for specifics
     * @param v - float
     */
    public void rotate(float v) {
        for (TransformableHolder transformableHolder : transformableHolders) {
            transformableHolder.transformable.rotate(v);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    //=============================== Getters and Setters below ========================================================

/*

    public void setWindow(RenderWindow window) {
        this.window = window;
    }
*/

    /**
     * Gets the RenderWindow, on which this entity is displayed
     *
     * @return RenderWindow
     */
    public RenderWindow getWindow() {
        return Driver.getWindow();
    }

    /**
     * Sets the name of the Entity, (which will be returned be toString)
     *
     * @param name - String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the Entity
     *
     * @return - String name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current X coordinate on the screen (Top left value for entity)
     *
     * @return - int x
     */
    public int getTopLeftX() {
        return x;
    }

    /**
     * Gets the current Y coordinate on the screen (Top left value for entity)
     *
     * @return - int y
     */
    public int getTopLeftY() {
        return y;
    }

    /**
     * Sets the current X coordinate on the screen (Top left value for entity)
     *
     * @param x - new X position
     */
    public void setTopLeftX(int x) {
        update();

        this.x = x;
    }

    /**
     * Sets the current Y coordinate on the screen (Top left value for entity)
     *
     * @param y - new Y position
     */
    public void setTopLeftY(int y) {
        update();

        this.y = y;
    }

    private void update() {
        for (TransformableHolder transformableHolder : transformableHolders) {
            transformableHolder.update();
        }

    }
    /**
     * Gets the current X coordinate of the center of the entity on the screen
     *
     * (height must be set accurately)
     * @return - int x
     */
    public int getCenterX() {
        return (w == -1) ? -1 : getTopLeftX() + w/2;
    }

    /**
     * Gets the current Y coordinate of the center of the entity on the screen
     *
     * (height must be set accurately)
     * @return - int y
     */
    public int getCenterY() {
        return (h == -1) ? -1 : getTopLeftY() + h/2;
    }

    /**
     * Sets the current X coordinate of the center of the entity on the screen
     *
     * (height must be set accurately)
     * @param x - new X position
     */
    public void setCenterX(int x) {
        if (w == -1)
            throw new RuntimeException("You must set the width before you try and use setCenterX, use setTopLeftX instead");
        else
            setTopLeftX(x - (w / 2));
    }

    /**
     * Sets the current Y coordinate of the center of the entity on the screen
     *
     * (height must be set accurately)
     * @param y - new Y position
     */
    public void setCenterY(int y) {
        if (h == -1)
            throw new RuntimeException("You must set the height before you try and use setCenterY, use setTopLeftY instead");
        else
            setTopLeftY(y - (h / 2));
    }

    /**
     * Gets the current Width of the Entity
     *
     * @return - int Width
     */
    public int getWidth() {
        return w;
    }

    /**
     * Gets the current Height of the Entity
     *
     * @return - int Height
     */
    public int getHeight() {
        return h;
    }

    /**
     * Sets the current Width of the Entity
     *
     * @param w - new Width
     */
    public void setWidthHeight(int w, int h) {
        this.h = h;
        this.w = w;

        update();
    }

    /**
     * Set the entity's transformable obj
     *
     * @param transformable - Transformable obj
     */
    public void addTransformable(Transformable transformable, int relX, int relY, int w, int h) {
        transformableHolders.add(new TransformableHolder(this, transformable, relX, relY, w, h));
    }

    public void hideTransformable(Transformable transformable) {
        ListIterator<TransformableHolder> it = transformableHolders.listIterator();
        while (it.hasNext()) {
            TransformableHolder transformableHolder = it.next();
            if (transformableHolder.transformable.equals(transformable)) {
                transformableHolder.hide();
            }
        }
    }

    public void showTransformable(Transformable transformable) {
        ListIterator<TransformableHolder> it = transformableHolders.listIterator();
        while (it.hasNext()) {
            TransformableHolder transformableHolder = it.next();
            if (transformableHolder.transformable.equals(transformable)) {
                transformableHolder.show();
            }
        }
    }


    /**
     * Get the entity's transformable obj
     *
     * @return Transformable obj
     */
    public Transformable getTransformable(int i) {
        return transformableHolders.get(i).transformable;
    }

    private class TransformableHolder {
        private Entity entity;
        private Transformable transformable;
        private int relX, relY;
        private int width = -1, height = -1;
        private boolean hidden = false;

        public TransformableHolder(Entity e, Transformable t, int relX, int relY, int w, int h) {
            this.entity = e;
            this.transformable = t;
            this.relX = relX;
            this.relY = relY;
            this.width = w;
            this.height = h;

            update();
        }

        public void hide() {
            hidden = true;
        }
        public void show() {
            hidden = false;
        }

        public void update() {
            final int tempX = entity.getTopLeftX() + relX;
            final int tempY = entity.getTopLeftY() + relY;

            transformable.setPosition(tempX, tempY);

            int width = this.width,
                    height = this.height;

            width = (width != -1) ? width : 0;
            height = (height != -1) ? height : 0;

            transformable.setOrigin(width / 2,  height / 2);
        }

        public void setRelX(int relX) {
            this.relX = relX;

            update();
        }

        public void setRelY(int relY) {
            this.relY = relY;

            update();
        }

        public void setWidth(int w) {
            this.width = w;

            update();
        }

        public void setHeight(int h) {
            this.height = h;

            update();
        }
    }
}
