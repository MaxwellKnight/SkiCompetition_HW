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

	public static BufferedImage changeImageColor(BufferedImage image, Color color) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgba = image.getRGB(x, y);
				int alpha = (rgba >> 24) & 0xFF; // Preserve alpha
				if (alpha != 0) { // Non-transparent pixel
					int newRgb = color.getRGB() & 0xFFFFFF; // New color without alpha
					int coloredArgb = (alpha << 24) | newRgb;
					newImage.setRGB(x, y, coloredArgb);
				} else {
					newImage.setRGB(x, y, rgba); // Preserve transparent pixels
				}
			}
		}
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
