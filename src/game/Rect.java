/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 *
 * @author ryan
 */
public class Rect extends Entity {

	public Rect(RenderWindow window, String name, int x, int y, int width, int height, Color c, int transparency) {
		super(window, name);

		Vector2f vec = new Vector2f(width, height);
		RectangleShape rect = new RectangleShape(vec);
		rect.setFillColor(new Color(c, transparency));

		this.setWidthHeight(width, height);
		this.setTopLeftX(x);
		this.setTopLeftY(y);

		this.setTransformable(rect);
	}

}