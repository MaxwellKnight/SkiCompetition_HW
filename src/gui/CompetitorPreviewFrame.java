package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import game.Interfaces.ICompetitor;
import game.Interfaces.IWinterSportsman;
import game.entities.sportsman.Decorator.ColoredSportsman;
import game.entities.sportsman.Decorator.SpeedySportsman;
import utilities.ColorChanger;

public class CompetitorPreviewFrame extends JFrame {

	private final JPanelWithBackground previewPanel;
	private final JPanelWithBackground screen;
	private final HashMap<ICompetitor, JLabel> racerLabels;
	private final HashMap<ICompetitor, ImageIcon> originalIcons;
	private final JComboBox<ICompetitor> racerDropdown;
	private Color selectedColor = Color.BLACK;
	private Map<ICompetitor, ColoredSportsman> coloredSportsmen;
	private Map<ICompetitor, SpeedySportsman> speedySportsmen;
	private JTextField accelerationField;

	private static final int DEFAULT_PREVIEW_WIDTH = 200;
	private static final int DEFAULT_PREVIEW_HEIGHT = 200;

	public CompetitorPreviewFrame(HashMap<ICompetitor, JLabel> racerLabels, JPanelWithBackground screen) {
		this.screen = screen;
		this.racerLabels = racerLabels;
		this.originalIcons = new HashMap<>();

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		previewPanel = new JPanelWithBackground("");
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

		accelerationField = new JTextField();
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

		this.coloredSportsmen = new HashMap<>();
		this.speedySportsmen = new HashMap<>();

		for (ICompetitor competitor : racerLabels.keySet()) {
			if (competitor instanceof IWinterSportsman) {
				IWinterSportsman winterSportsman = (IWinterSportsman) competitor;
				coloredSportsmen.put(competitor, new ColoredSportsman(winterSportsman));
				speedySportsmen.put(competitor, new SpeedySportsman(winterSportsman));
			}
		}

		updatePreview((ICompetitor) racerDropdown.getSelectedItem());
	}

	private void updatePreview(ICompetitor selectedCompetitor) {
		if (selectedCompetitor != null) {
			ImageIcon icon = getUpdatedIcon(selectedCompetitor);
			JLabel label = new JLabel(icon);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);

			// Use a minimum size for the label
			int width = Math.max(previewPanel.getWidth(), DEFAULT_PREVIEW_WIDTH);
			int height = Math.max(previewPanel.getHeight(), DEFAULT_PREVIEW_HEIGHT);
			label.setPreferredSize(new Dimension(width, height));

			previewPanel.setLayout(new BorderLayout());
			previewPanel.removeAll();
			previewPanel.add(label, BorderLayout.CENTER);

			if (selectedCompetitor instanceof IWinterSportsman) {
				SpeedySportsman speedySportsman = speedySportsmen.get(selectedCompetitor);
				double currentAcceleration = speedySportsman.getAcceleration();
				JLabel accelerationLabel = new JLabel("Current Acceleration: " + currentAcceleration);
				previewPanel.add(accelerationLabel, BorderLayout.SOUTH);
			}

			previewPanel.revalidate();
			previewPanel.repaint();
		}
	}

	private ImageIcon getUpdatedIcon(ICompetitor competitor) {
		ImageIcon originalIcon = originalIcons.get(competitor);
		Color color = Color.BLACK;

		if (competitor instanceof IWinterSportsman) {
			ColoredSportsman coloredSportsman = coloredSportsmen.get(competitor);
			if (coloredSportsman != null) {
				Color sportmanColor = coloredSportsman.getColor();
				if (sportmanColor != null) {
					color = sportmanColor;
				}
			}
		}

		if (color.equals(Color.BLACK)) {
			return scaleIcon(originalIcon, 300, 300);
		}

		BufferedImage bufferedImage = new BufferedImage(
				originalIcon.getIconWidth(),
				originalIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(originalIcon.getImage(), 0, 0, null);
		g2d.dispose();

		BufferedImage coloredImage = ColorChanger.changeImageColor(bufferedImage, color);
		return scaleIcon(new ImageIcon(coloredImage), 300, 300);
	}

	private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
		if (width <= 0 || height <= 0) {
			return icon; // Return original icon if dimensions are invalid
		}
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImg);
	}

	private void updateLabels(ICompetitor selectedCompetitor) {
		if (selectedCompetitor instanceof IWinterSportsman) {
			ColoredSportsman coloredSportsman = coloredSportsmen.get(selectedCompetitor);
			SpeedySportsman speedySportsman = speedySportsmen.get(selectedCompetitor);

			coloredSportsman.colorSportsman(selectedColor);

			try {
				if (accelerationField.getText().length() > 0) {
					double accelerationIncrease = Double.parseDouble(accelerationField.getText());
					speedySportsman.increaseAcceleration(accelerationIncrease);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Please enter a valid number for acceleration.",
						"Invalid Input",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			ImageIcon updatedIcon = getUpdatedIcon(selectedCompetitor);

			updatePreview(selectedCompetitor);

			JLabel screenLabel = racerLabels.get(selectedCompetitor);
			if (screenLabel != null) {
				screenLabel.setIcon(scaleIcon(updatedIcon, 50, 50));
				screen.revalidate();
				screen.repaint();
			}
		}
	}

	private void resetLabels(ICompetitor selectedCompetitor) {
		if (selectedCompetitor instanceof IWinterSportsman) {
			ColoredSportsman coloredSportsman = coloredSportsmen.get(selectedCompetitor);
			SpeedySportsman speedySportsman = speedySportsmen.get(selectedCompetitor);

			coloredSportsman.colorSportsman(Color.BLACK);
			speedySportsman.increaseAcceleration(-speedySportsman.getAcceleration());

			updatePreview(selectedCompetitor);

			JLabel screenLabel = racerLabels.get(selectedCompetitor);
			if (screenLabel != null) {
				screenLabel.setIcon(originalIcons.get(selectedCompetitor));
				screen.revalidate();
				screen.repaint();
			}
		}
	}
}
