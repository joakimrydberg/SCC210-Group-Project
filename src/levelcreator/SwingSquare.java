package levelcreator;

import tools.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SwingSquare extends JButton implements ComponentListener {
	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;
	private String filePath;
	private int rotation = 0;

	/**
	 * Sets up the card to display an image
	 *
	 */
	public SwingSquare() {


		repaint();
	}

	/**
	 * Sets the image according to the image path
	 *
	 * @param imagePath - string representing the file path
	 */
	public void setImage(String imagePath) throws IOException {
		filePath = imagePath.substring(imagePath.lastIndexOf(Constants.SEP.charAt(0)) + 1);
		try{
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e){
			throw e;
		}
		repaint();
	}

	public BufferedImage getImage() {
		return image;
	}

	@Override
	public String toString() {
		return filePath;
	}

	public void setRotation(int degrees) {
		degrees = degrees % 360;

		switch (degrees){
			case 0:		case 90:
			case 180:	case 270:
				rotation = degrees;
				break;

			default:
				throw new IllegalArgumentException("Cannot rotate by any degree other than a multiple of 90 degrees");
		}
	}

	public int getRotation() {
		return rotation;
	}

	/**
	 * Whenever the pane is resized, repaint.
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		repaint();
	}

	/**
	 * Set the image to be the same size as the window
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		int newW = this.getWidth();
		int newH = this.getHeight();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		switch (rotation) {
			case 90:
			case 270:
				g2.rotate(Math.toRadians(rotation), newW / 2, newH / 2);
				g2.translate((newW - newH) / 2, (newH - newW) / 2);
				g2.drawImage(image, 0, 0, newH, newW, null);
				break;

			case 180:
				g2.rotate(Math.toRadians(rotation), newW / 2, newH / 2);
				g2.drawImage(image, 0, 0, newW, newH, null);
				break;

			default:
				g2.drawImage(image, 0, 0, newW, newH, null);
				break;
		}
	}

	//============================================

	@Override
	public void componentMoved(ComponentEvent e) {
		//do nothing
	}

	@Override
	public void componentShown(ComponentEvent e) {
		//do nothing

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		//do nothing

	}
}