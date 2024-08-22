package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelWithBackground extends JPanel {
	Image background_image = null;

	public JPanelWithBackground(String path) {
		setBackgroundImage(path);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background_image, 0, 0, getWidth(), getHeight(), this);
	}

	public void setBackgroundImage(String path) {
		ImageIcon loaded_image = new ImageIcon(path);
		background_image = loaded_image.getImage();
		repaint();
	}
}
