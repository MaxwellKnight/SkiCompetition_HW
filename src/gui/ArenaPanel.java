package gui;

import javax.swing.*;
import game.arena.WinterArena;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;
import utilities.ValidationUtils;

public class ArenaPanel extends JPanel {
	private final App app;
	private final JTextArea arenaLength;
	private final JComboBox<String> arenaSurface;
	private final JComboBox<String> arenaWeather;
	private final JPanelWithBackground screen;
	private static final String[] SURFACES = { "Powder", "Crud", "Ice" };
	private static final String[] WEATHER = { "Sunny", "Cloudy", "Stormy" };
	private static final String[] PATHS = { "icons/Sunny.jpg", "icons/Cloudy.jpg", "icons/Stormy.jpg" };

	public ArenaPanel(App app, JPanelWithBackground screen) {
		this.app = app;
		this.screen = screen;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Build Arena"));

		arenaLength = new JTextArea(1, 0);
		arenaSurface = new JComboBox<>(SURFACES);
		arenaWeather = new JComboBox<>(WEATHER);

		add(new JLabel("Arena length"));
		add(arenaLength);
		add(new JLabel("Snow Surface"));
		add(arenaSurface);
		add(new JLabel("Weather Condition"));
		add(arenaWeather);

		JButton buildButton = new JButton("Build Arena");
		buildButton.addActionListener(e -> buildArena());
		add(buildButton);
	}

	private void buildArena() {
		if (!ValidationUtils.assertStringToNumber(arenaLength.getText(),
				"Please enter a valid number for arena length")) {
			return;
		}

		int width = Integer.parseInt(arenaLength.getText());
		int surfaceIndex = arenaSurface.getSelectedIndex();
		int weatherIndex = arenaWeather.getSelectedIndex();
		screen.setBackgroundImage(PATHS[weatherIndex]);

		WinterArena arena = new WinterArena(
				width,
				SnowSurface.values()[surfaceIndex],
				WeatherCondition.values()[weatherIndex]);
		app.setArena(arena);
	}
}
