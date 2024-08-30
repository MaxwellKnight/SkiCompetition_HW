package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.arena.WinterArena;
import game.competition.Competition;
import game.entities.sportsman.Sportsman;

public class App extends JFrame implements Observer {

	private WinterArena arena = null;
	private Competition winterCompetition = null;
	private final HashMap<ICompetitor, JLabel> racersLabels = new HashMap<>();

	private final JPanel controls;
	private final InfoFrame infoFrame;
	private final ArenaPanel arenaPanel;
	private final CompetitionPanel competitionPanel;
	private final CompetitorPanel competitorPanel;
	private final GamePanel gamePanel;

	private int width = 1000;
	private static final int SIDEBAR = 250;
	private static final int RACER_SIZE = 50;
	private static final int HEIGHT = 800;

	public App() {
		setPreferredSize(new Dimension(width + SIDEBAR, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Winter Competition");
		setLayout(new BorderLayout());

		screen = new JPanelWithBackground("icons/Cloudy.jpg");
		screen.setPreferredSize(new Dimension(width, HEIGHT));
		screen.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		screen.setLayout(null);
		screen.setVisible(true);

		controls = new JPanel();
		controls.setPreferredSize(new Dimension(SIDEBAR, HEIGHT));

		infoFrame = new InfoFrame();
		arenaPanel = new ArenaPanel(this, screen);
		competitionPanel = new CompetitionPanel(this);
		competitorPanel = new CompetitorPanel(this, competitionPanel);
		gamePanel = new GamePanel(this);

		controls.add(arenaPanel);
		controls.add(competitionPanel);
		controls.add(competitorPanel);
		controls.add(gamePanel);
		controls.setVisible(true);

		add(screen, BorderLayout.CENTER);
		add(controls, BorderLayout.EAST);
		pack();
		setVisible(true);
	}

	public IArena getArena() {
		return arena;
	}

	public void setArena(WinterArena arena) {
		this.arena = arena;
	}

	public Competition getWinterCompetition() {
		return winterCompetition;
	}

	public void setWinterCompetition(Competition winterCompetition) {
		this.winterCompetition = winterCompetition;
	}

	public HashMap<ICompetitor, JLabel> getRacersLabels() {
		return racersLabels;
	}

	public InfoFrame getInfoPanel() {
		return this.infoFrame;
	}

	private final JPanelWithBackground screen;

	public JPanelWithBackground getScreen() {
		return this.screen;
	}

	private JLabel createRacerLabel(final String iconPath) {
		ImageIcon loaded_img = new ImageIcon(iconPath);
		final Image scaled = loaded_img.getImage().getScaledInstance(RACER_SIZE, RACER_SIZE, Image.SCALE_SMOOTH);
		loaded_img = new ImageIcon(scaled);
		final JLabel racerIcon = new JLabel(loaded_img);
		racerIcon.setVisible(true);
		return racerIcon;
	}

	public void addRacerLabel(ICompetitor competitor, String path) {
		Sportsman racer = (Sportsman) competitor;
		JLabel racerLabel = createRacerLabel(path);
		racerLabel.setBounds(RACER_SIZE * racersLabels.size(), 0, RACER_SIZE, RACER_SIZE);
		screen.add(racerLabel);
		racersLabels.put(racer, racerLabel);
		screen.revalidate();
		screen.repaint();
		infoFrame.updateCompetitorInfo(racersLabels, arena);
	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		ICompetitor racer = (ICompetitor) o;
		JLabel racerLabel = racersLabels.get(racer);

		int y = (int) racer.getLocation().getX();
		if (y > screen.getHeight())
			y = screen.getHeight() - RACER_SIZE;

		final int finalY = y;
		SwingUtilities.invokeLater(() -> {
			racerLabel.setBounds(racerLabel.getX(), finalY, RACER_SIZE, RACER_SIZE);
			infoFrame.updateCompetitorInfo(racersLabels, arena);
			screen.revalidate();
			screen.repaint();
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(App::new);
	}
}
