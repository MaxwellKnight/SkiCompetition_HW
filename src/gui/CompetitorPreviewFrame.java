package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JColorChooser;

import game.Interfaces.ICompetitor;
import utilities.ColorChanger;

public class CompetitorPreviewFrame extends JFrame {

	private final JPanel previewPanel;
	private final JPanelWithBackground screen;
	private final HashMap<ICompetitor, JLabel> racerLabels;
	private final HashMap<ICompetitor, ImageIcon> originalIcons;
	private final JComboBox<ICompetitor> racerDropdown;
	private Color selectedColor = Color.BLACK;

	public CompetitorPreviewFrame(HashMap<ICompetitor, JLabel> racerLabels, JPanelWithBackground screen) {
		this.screen = screen;
		this.racerLabels = racerLabels;
		this.originalIcons = new HashMap<>();

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		previewPanel = new JPanel(new BorderLayout());
		previewPanel.setPreferredSize(new Dimension(800, 600));

		racerDropdown = new JComboBox<>(racerLabels.keySet().toArray(new ICompetitor[0]));
		racerDropdown.addActionListener(e -> updatePreview((ICompetitor) racerDropdown.getSelectedItem()));

		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton colorPickerButton = new JButton("Choose Color");
		colorPickerButton.addActionListener(e -> {
			Color color = JColorChooser.showDialog(CompetitorPreviewFrame.this, "Choose Color", selectedColor);
			if (color != null) {
				selectedColor = color;
				updatePreview((ICompetitor) racerDropdown.getSelectedItem());
			}
		});

		JTextField accelerationField = new JTextField();
		accelerationField.setPreferredSize(new Dimension(100, 30));
		accelerationField.setToolTipText("Enter acceleration value");

		JPanel buttonPanel = new JPanel();
		JButton applyButton = new JButton("Apply");
		JButton resetButton = new JButton("Reset");

		applyButton.addActionListener(e -> {
			ICompetitor selectedCompetitor = (ICompetitor) racerDropdown.getSelectedItem();
			if (selectedCompetitor != null) {
				updateLabels(selectedCompetitor);
			}
		});

		resetButton.addActionListener(e -> {
			selectedColor = Color.BLACK;
			ICompetitor selectedCompetitor = (ICompetitor) racerDropdown.getSelectedItem();
			if (selectedCompetitor != null) {
				resetLabels(selectedCompetitor);
			}
		});

		buttonPanel.add(colorPickerButton);
		buttonPanel.add(applyButton);
		buttonPanel.add(resetButton);

		JPanel fieldsPanel = new JPanel();
		fieldsPanel.add(new JLabel("Acceleration:"));
		fieldsPanel.add(accelerationField);

		controlPanel.add(fieldsPanel, BorderLayout.CENTER);
		controlPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(racerDropdown, BorderLayout.NORTH);
		add(previewPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);

		for (Map.Entry<ICompetitor, JLabel> entry : racerLabels.entrySet()) {
			originalIcons.put(entry.getKey(), (ImageIcon) entry.getValue().getIcon());
		}

		updatePreview((ICompetitor) racerDropdown.getSelectedItem());
	}

	private void updatePreview(ICompetitor selectedCompetitor) {
		if (selectedCompetitor != null) {
			JLabel label = new JLabel(getUpdatedIcon(selectedCompetitor));
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			previewPanel.removeAll();
			previewPanel.add(label, BorderLayout.CENTER);
			previewPanel.revalidate();
			previewPanel.repaint();
		}
	}

	private ImageIcon getUpdatedIcon(ICompetitor competitor) {
		ImageIcon originalIcon = originalIcons.get(competitor);
		if (selectedColor.equals(Color.BLACK)) {
			return originalIcon;
		}

		BufferedImage bufferedImage = new BufferedImage(
				originalIcon.getIconWidth(),
				originalIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(originalIcon.getImage(), 0, 0, null);
		g2d.dispose();

		BufferedImage coloredImage = ColorChanger.changeImageColor(bufferedImage, selectedColor);
		return new ImageIcon(coloredImage);
	}

	private void updateLabels(ICompetitor selectedCompetitor) {
		ImageIcon updatedIcon = getUpdatedIcon(selectedCompetitor);

		// Update preview panel
		updatePreview(selectedCompetitor);

		// Update screen panel
		JLabel screenLabel = racerLabels.get(selectedCompetitor);
		if (screenLabel != null) {
			screenLabel.setIcon(updatedIcon);
			screen.revalidate();
			screen.repaint();
		}
	}

	private void resetLabels(ICompetitor selectedCompetitor) {
		// Reset preview panel
		updatePreview(selectedCompetitor);

		// Reset screen panel
		JLabel screenLabel = racerLabels.get(selectedCompetitor);
		if (screenLabel != null) {
			screenLabel.setIcon(originalIcons.get(selectedCompetitor));
			screen.revalidate();
			screen.repaint();
		}
	}
}
