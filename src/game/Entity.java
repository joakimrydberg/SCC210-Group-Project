package game;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import java.util.ArrayList;

/**
 * This class represents an Entity in the game. Something that will appear on the screen.
 *
 * @author Alexander J Mills
 */
public class Entity  {
    private String name = "";
    private int x = 0, y = 0;   //current x and y coordinates
    private int w = -1, h = -1; // current width and height of the Entity
    private ArrayList<TransformableHolder> transformables = new ArrayList<>();
    private RenderWindow window = null;

    /**
     * Creates a new entity, with a given name
     *
     * @param window - RenderWindow in which the Entity will be displayed
     * @param name - String name for the Entity
     * @param centerX - int X position of the center of the entity
     * @param centerY - int Y position of the center of the entity
     * @param width - int width
     * @param height - int height
     */
    public Entity(RenderWindow window, String name, int centerX, int centerY, int width, int height) {
        super();

        setWindow(window);
        setName(name);
        setWidthHeight(width, height);
        setCenterX(centerX);
        setCenterY(centerY);
    }

    /**
     * Creates a new entity
     *
     * @param window - RenderWindow in which the Entity will be displayed
     * @param name - String name for the Entity
     */
    public Entity(RenderWindow window, String name) {
        super();

        this.window = window;
        this.name = name;
    }

    /**
     * Draws the Entity on to the screen
     *
     */
    public void draw() {
        for (TransformableHolder transformableHolder : transformables) {
            //DebugPrinter.debugPrint(this, "drawing: x:" + getTopLeftX() + " y:" + getTopLeftY() + " w:" + getWidth() + " h:" + getHeight());
            window.draw((Drawable) transformableHolder.transformable);
        }
    }

    /**
     * Rotates the entity
     *
     * see Transformable's implementations for specifics
     * @param v - float
     */
    public void rotate(float v) {
        for (TransformableHolder transformableHolder : transformables) {
            transformableHolder.transformable.rotate(v);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Called when clicked
     *
     * @param clickPosition - Vector2i containing the coordinates of the click event
     */
    public void clicked(Vector2i clickPosition) {
        //does nothing at all, should probably override.
    }

    public void clicked(MainMenu mainMenu, CharMenu charMenu, MapMenu mapMenu){
        //override
    }

    //=============================== Getters and Setters below ========================================================

    /**
     * Sets the Entity's RenderWindow to be displayed on
     *
     * @param window - RenderWindow
     */
    public void setWindow(RenderWindow window) {
        this.window = window;
    }

    /**
     * Gets the RenderWindow, on which this entity is displayed
     *
     * @return RenderWindow
     */
    public RenderWindow getWindow() {
        return window;
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
        for (TransformableHolder transformableHolder : transformables) {
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
        transformables.add(new TransformableHolder(this, transformable, relX, relY, w, h));
    }

    /**
     * Get the entity's transformable obj
     *
     * @return Transformable obj
     */
    public Transformable getTransformable(int i) {
        return transformables.get(i).transformable;
    }

    private class TransformableHolder {
        private Entity entity;
        private Transformable transformable;
        private int relX, relY;
        private int width, height;

        public TransformableHolder(Entity e, Transformable t, int relX, int relY, int w, int h) {
            this.entity = e;
            this.transformable = t;
            this.relX = relX;
            this.relY = relY;
            this.width = w;
            this.height = h;

            update();
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
