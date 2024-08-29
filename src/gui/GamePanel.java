package gui;

import javax.swing.*;
import game.GameEngine;

public class GamePanel extends JPanel {
	private final App app;

	public GamePanel(App app) {
		this.app = app;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Game Controls"));

		JButton infoButton = new JButton("Show Info");
		JButton startButton = new JButton("Start Competition");

		startButton.addActionListener(e -> startCompetition());
		infoButton.addActionListener(e -> showInfo());

		add(startButton);
		add(infoButton);
	}

	private void startCompetition() {
		GameEngine gameEngine = GameEngine.getInstance();
		gameEngine.startRace(app.getWinterCompetition());
	}

	private void showInfo() {
		app.getInfoPanel().setVisible(true);
		app.getInfoPanel().updateCompetitorInfo(app.getRacersLabels(), app.getArena());
	}
}
