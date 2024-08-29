package utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ColorChanger {

	// Method to load an image from the resource path
	public static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try {
			URL imageUrl = ColorChanger.class.getResource(path);
			if (imageUrl != null) {
				image = ImageIO.read(imageUrl);
			} else {
				System.err.println("Image not found: " + path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	// Method to change the color of the image
	public static BufferedImage changeImageColor(BufferedImage image, Color color) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = newImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);

		// Get the pixel data
		int width = image.getWidth();
		int height = image.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgba = image.getRGB(x, y);
				// Check if the pixel is not transparent
				if ((rgba >> 24) != 0x00) {
					// Apply the new color while keeping the original alpha value
					int newRgb = color.getRGB() & 0xFFFFFF;
					int alpha = rgba >> 24;
					int coloredArgb = (alpha << 24) | newRgb;
					newImage.setRGB(x, y, coloredArgb);
				}
			}
		}
		g2d.dispose();
		return newImage;
	}

	// Method to set a colored image to a JLabel
	public static void setColoredImageToLabel(JLabel label, String imagePath, Color color) {
		BufferedImage image = loadImage(imagePath);
		if (image != null) {
			BufferedImage coloredImage = changeImageColor(image, color);
			ImageIcon icon = new ImageIcon(coloredImage);
			label.setIcon(icon);
		}
	}
}
