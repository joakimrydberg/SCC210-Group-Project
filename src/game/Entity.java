package game;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;

/**
 * This class represents an Entity in the game. Something that will appear on the screen.
 *
 * @author Alexander J Mills
 */
public class Entity  {
    private String name = "";
    private int x = 0, y = 0;   //current x and y coordinates
    private int w = -1, h = -1; // current width and height of the Entity
    private Transformable transformable = null;
    private RenderWindow window = null;

    /**
     * Creates a new entity, with a given name
     *
     * @param window - RenderWindow in which the Entity will be displayed
     * @param name - String name for the Entity
     * @param topLeftX - int X position of the top left of the entity
     * @param topLeftY - int Y position of the top left of the entity
     * @param width - int width
     * @param height - int height
     * @param transformable - Transformable obj
     */
    public Entity(RenderWindow window, String name, int topLeftX, int topLeftY, int width, int height, Transformable transformable) {
        super();

        setWindow(window);
        setName(name);
        setTopLeftX(topLeftX);
        setTopLeftY(topLeftY);
        setHeight(height);
        setWidth(width);
        setTransformable(transformable);
    }

    /**
     * Creates a new entity
     *
     * @param window - RenderWindow in which the Entity will be displayed
     * @param name - String name for the Entity
     */
    public Entity(RenderWindow window, String name) {
        super();

        setName(name);
    }

    /**
     * Draws the Entity on to the screen
     *
     */
    public void draw() {
        window.draw((Drawable)transformable);
    }

    /**
     * Rotates the entity
     *
     * see Transformable's implementations for specifics
     * @param v - float
     */
    public void rotate(float v) {
        transformable.rotate(v);
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
        this.x = x;
    }

    /**
     * Sets the current Y coordinate on the screen (Top left value for entity)
     *
     * @param y - new Y position
     */
    public void setTopLeftY(int y) {
        this.y = y;
    }

    /**
     * Gets the current X coordinate of the center of the entity on the screen
     *
     * (height must be set accurately)
     * @return - int x
     */
    public int getCenterX() {
        return getTopLeftX() + (w / 2);
    }

    /**
     * Gets the current Y coordinate of the center of the entity on the screen
     *
     * (height must be set accurately)
     * @return - int y
     */
    public int getCenterY() {
        return getTopLeftY() + (h / 2);
    }

    /**
     * Sets the current X coordinate of the center of the entity on the screen
     *
     * (height must be set accurately)
     * @param x - new X position
     */
    public void setCenterX(int x) {
        setTopLeftX(x - (w / 2));
    }

    /**
     * Sets the current Y coordinate of the center of the entity on the screen
     *
     * (height must be set accurately)
     * @param y - new Y position
     */
    public void setCenterY(int y) {
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
    public void setWidth(int w) {
        this.w = w;
    }

    /**
     * Sets the current Height of the Entity
     *
     * @param h - new Height
     */
    public void setHeight(int h) {
        this.h = h;
    }

    /**
     * Set the entity's transformable obj
     *
     * @param transformable - Transformable obj
     */
    public void setTransformable(Transformable transformable) {
        this.transformable = transformable;
    }

    /**
     * Get the entity's transformable obj
     *
     * @return Transformable obj
     */
    public Transformable getTransformable() {
        return this.transformable;
    }



}
