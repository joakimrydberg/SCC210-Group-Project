/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpackage;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

/**
 *
 * @author ryan
 */
public class Rect extends Actor {
		private int radius;

		public Rect(int x, int y, int height, int width, Color c,
				int transparency) {
                        Vector2f vec = new Vector2f(height,width);
			RectangleShape rect = new RectangleShape(vec);
			rect.setFillColor(new Color(c, transparency));
			rect.setOrigin(radius, radius);

			this.x = x;
			this.y = y;
			this.radius = radius;

			obj = rect;
		}
                
		//
		// Default method typically assumes a rectangle,
		// so do something a little different
		//
		
	}