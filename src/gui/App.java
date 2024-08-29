package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import game.GameEngine;
import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.arena.WinterArena;
import game.competition.Competition;
import game.competition.WinterCompetition;
import game.entities.sportsman.WinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;
import utilities.ValidationUtils;

/**
 * 
 */
public class App extends JFrame {

	private WinterArena arena = null;
	private Competition winter_competition = null;
	private final HashMap<ICompetitor, JLabel> racers_labels = new HashMap<>();

	private final JPanelWithBackground screen;
	private final JPanel controls;
	private final String surfaces[] = { "Powder", "Crud", "Ice" };
	private final String competition[] = { "Snowboard", "Ski" };
	private final String discipline[] = { "Slalom", "Giant_Slalom", "Downhill", "Freestyle" };
	private final String league[] = { "Junior", "Adult", "Senior" };
	private final String gender[] = { "Female", "Male" };
	private final String weather[] = { "Sunny", "Cloudy", "Stormy", };
	private final String paths[] = { "icons/Sunny.jpg", "icons/Cloudy.jpg", "icons/Stormy.jpg" };

	private int width = 1000; /* user can modify the width of the arena */
	private final int SIDEBAR = 250;
	private final int RACER_SIZE = 50;
	private final int HEIGHT = 800;

	private final JTextArea arena_length = new JTextArea(1, 0);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private final JComboBox arena_surface = new JComboBox(surfaces);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private final JComboBox arena_weather = new JComboBox(weather);

	@SuppressWarnings("rawtypes")
	private final JComboBox competition_kind = new JComboBox<>(competition);
	private final JTextArea competition_max_count = new JTextArea();
	@SuppressWarnings("rawtypes")
	private final JComboBox competition_discipline = new JComboBox<>(discipline);
	@SuppressWarnings("rawtypes")
	private final JComboBox competition_league = new JComboBox<>(league);
	@SuppressWarnings("rawtypes")
	private final JComboBox competition_gender = new JComboBox<>(gender);

	private final JTextArea competitor_name = new JTextArea();
	private final JTextArea competitor_age = new JTextArea();
	private final JTextArea competitor_max_speed = new JTextArea(1, 0);
	private final JTextArea competitor_acceleration = new JTextArea();

	/**
	 * 
	 */
	public App() {
		setPreferredSize(new Dimension(width + SIDEBAR, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Knight Competition");
		setLayout(new BorderLayout());

		/* panel for the main screen (visual characters display) */
		screen = new JPanelWithBackground("icons/Cloudy.jpg");
		screen.setPreferredSize(new Dimension(width, HEIGHT));
		screen.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		screen.setLayout(null);
		screen.setVisible(true);

		/* panel for the control buttons, lables, dropdowns */
		controls = new JPanel();
		controls.setPreferredSize(new Dimension(SIDEBAR, HEIGHT));
		/* adding the controls sections to modify the competition setiings */
		controls.add(build_arena());
		controls.add(build_competition());
		controls.add(add_competitor());
		controls.add(game());
		controls.setVisible(true);

		add(screen, BorderLayout.CENTER);
		add(controls, BorderLayout.EAST);
		pack();
		setVisible(true);
	}

	/**
	 * 
	 */
	public void handle_competitors_info() {
		final String[] column_names = { "Name", "Speed", "Max speed", "Location", "Finished" };

		final JFrame info_panel = new JFrame();
		info_panel.setPreferredSize(new Dimension(500, 300));
		info_panel.setTitle("Competitors information");
		info_panel.setLayout(new BorderLayout());

		final DefaultTableModel model = new DefaultTableModel(column_names, 0);
		final JTable table = new JTable(model);
		final JScrollPane scroll_pane = new JScrollPane(table);
		info_panel.add(scroll_pane, BorderLayout.CENTER);

		info_panel.pack();
		info_panel.setVisible(true);

		final Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				model.setRowCount(0);
				print_competitors_info(model);

				if (!winter_competition.hasActiveCompetitors()) {
					((Timer) e.getSource()).stop();
				}
			}
		});
		timer.start();
	}

	private JLabel createLeftAlignedLabel(final String text) {
		final JLabel label = new JLabel(text);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		return label;
	}

	/**
	 * @return
	 */
	private JPanel build_arena() {
		final JPanel arena_panel = new JPanel();
		arena_panel.setVisible(true);
		arena_panel.setPreferredSize(new Dimension(SIDEBAR, 170));
		arena_panel.setLayout(new BoxLayout(arena_panel, BoxLayout.Y_AXIS));
		arena_panel.add(createLeftAlignedLabel("BUILD ARENA"));

		arena_panel.add(createLeftAlignedLabel("Arena length"));
		arena_panel.add(arena_length);

		arena_panel.add(createLeftAlignedLabel("Snow Surface"));
		arena_panel.add(arena_surface);

		arena_panel.add(createLeftAlignedLabel("Weather Condition"));
		arena_panel.add(arena_weather);

		final JButton btn = new JButton("Build Arena");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (!ValidationUtils.assertStringToNumber(arena_length.getText(),
						"Please enter a valid number for competition max count")) {
					return;
				}

				width = Integer.parseInt(arena_length.getText());
				screen.setSize(width, HEIGHT);
				setSize(width + SIDEBAR, HEIGHT);
				final int surface_index = arena_surface.getSelectedIndex();
				final int weather_index = arena_weather.getSelectedIndex();
				screen.setBackgroundImage(paths[weather_index]);
				arena = new WinterArena(
						width,
						SnowSurface.values()[surface_index],
						WeatherCondition.values()[weather_index]);
			}
		});

		arena_panel.add(btn);

		return arena_panel;
	}

	/**
	 * @return
	 */
	private JPanel build_competition() {
		final JPanel competition_panel = new JPanel();
		competition_panel.setVisible(true);
		competition_panel.setPreferredSize(new Dimension(SIDEBAR, 260));
		competition_panel.setLayout(new BoxLayout(competition_panel, BoxLayout.Y_AXIS));
		competition_panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

		competition_panel.add(createLeftAlignedLabel("CREATE COMPETITION"));
		competition_panel.add(createLeftAlignedLabel("Choose Competition"));
		competition_panel.add(competition_kind);

		competition_panel.add(createLeftAlignedLabel("Max competitors number"));
		competition_panel.add(competition_max_count);

		competition_panel.add(createLeftAlignedLabel("Discipline"));
		competition_panel.add(competition_discipline);

		competition_panel.add(createLeftAlignedLabel("League"));
		competition_panel.add(competition_league);

		competition_panel.add(createLeftAlignedLabel("Gender"));
		competition_panel.add(competition_gender);

		final JButton btn = new JButton("Create Competition");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (arena == null) {
					ValidationUtils.alert("Must build arena before competition");
					return;
				}
				if (!ValidationUtils.assertStringToNumber(competition_max_count.getText(),
						"Please enter a valid number for competition max count")) {
					return;
				}

				final int max_count = Integer.parseInt(competition_max_count.getText());
				if (max_count <= 0 || max_count > 20) {
					ValidationUtils.alert("Max count number must be in the range of [1,20]");
					return;
				}

				try {
					final Class<?> loaded_class = Class
							.forName("game.competition." + competition_kind.getSelectedItem() + "Competition");
					final Constructor<?> ctor = loaded_class.getConstructor(IArena.class, int.class,
							Discipline.class,
							League.class, Gender.class);
					winter_competition = (WinterCompetition) ctor.newInstance(
							arena,
							max_count,
							Discipline.values()[competition_discipline.getSelectedIndex()],
							League.values()[competition_league.getSelectedIndex()],
							Gender.values()[competition_gender.getSelectedIndex()]);
				} catch (final Exception error) {
					ValidationUtils.alert(error.toString());
					System.out.println(error.toString());
				}
			}

		});
		competition_panel.add(btn);

		return competition_panel;
	}

	/**
	 * @return
	 */
	private JPanel add_competitor() {
		final JPanel competitor = new JPanel();
		competitor.setVisible(true);
		competitor.setPreferredSize(new Dimension(SIDEBAR, 200));
		competitor.setLayout(new BoxLayout(competitor, BoxLayout.Y_AXIS));
		competitor.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

		competitor.add(createRacerLabel("Create competitor"));
		competitor.add(createLeftAlignedLabel("Name"));
		competitor.add(competitor_name);

		competitor.add(createLeftAlignedLabel("Age"));
		competitor.add(competitor_age);

		competitor.add(createLeftAlignedLabel("Max speed"));
		competitor.add(competitor_max_speed);

		competitor.add(createLeftAlignedLabel("Acceleration"));
		competitor.add(competitor_acceleration);

		final JButton btn = new JButton("Add Competitor");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (arena == null || winter_competition == null) {
					ValidationUtils.alert("Must build arena and create competition before adding competitors");
					return;
				}
				if (!ValidationUtils.assertStringToDouble(competitor_age.getText(), "Age must be a number") ||
						!ValidationUtils.assertStringToDouble(competitor_max_speed.getText(),
								"Max speed must be number")
						||
						!ValidationUtils.assertStringToDouble(competitor_acceleration.getText(),
								"Acceleration must be a number")) {
					return;
				}

				final String name = competitor_name.getText();
				final double age = Double.parseDouble(competitor_age.getText());
				final double max_speed = Double.parseDouble(competitor_max_speed.getText());
				final double acceleration = Double.parseDouble(competitor_acceleration.getText());
				final WinterCompetition comp = (WinterCompetition) winter_competition;
				final String kind = (String) competition_kind.getSelectedItem();

				try {
					final Class<?> loaded_class = Class.forName("game.entities.sportsman." + kind + "er");
					final Constructor<?> ctor = loaded_class.getConstructor(String.class, double.class, Gender.class,
							double.class, double.class, Discipline.class);

					final WinterSportsman racer = (WinterSportsman) ctor.newInstance(
							name,
							age,
							comp.getGender(),
							acceleration,
							max_speed,
							comp.getDiscipline());
					winter_competition.addCompetitor(racer);

					final String path = "icons/" + kind + competition_gender.getSelectedItem() + ".png";
					final JLabel racerIcon = createRacerLabel(path);

					final int x = (int) racer.getLocation().getX();
					racerIcon.setBounds(RACER_SIZE * racers_labels.size(), x, RACER_SIZE, RACER_SIZE);
					screen.add(racerIcon);
					racers_labels.put(racer, racerIcon);
					if (racers_labels.size() * RACER_SIZE >= width) {
						width *= 2;
						screen.setSize(width, HEIGHT);
						setSize(width + SIDEBAR, HEIGHT);
					}
					screen.revalidate();
					screen.repaint();
				} catch (final Exception error) {
					System.out.println(error);
					ValidationUtils.alert("Competitor is not fit to competition! Choose another competitor.");
				}
			}
		});
		competitor.add(btn);
		return competitor;
	}

	/**
	 * @param iconPath
	 * @return
	 */
	private JLabel createRacerLabel(final String iconPath) {
		ImageIcon loaded_img = new ImageIcon(iconPath);
		final Image scaled = loaded_img.getImage().getScaledInstance(RACER_SIZE, RACER_SIZE, Image.SCALE_SMOOTH);
		loaded_img = new ImageIcon(scaled);
		final JLabel racerIcon = new JLabel(loaded_img);
		racerIcon.setVisible(true);
		return racerIcon;
	}

	/**
	 * @return
	 */
	private JPanel game() {
		final JPanel game = new JPanel();
		game.setVisible(true);
		game.setPreferredSize(new Dimension(SIDEBAR, 200));
		game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
		game.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

		final JButton info_btn = new JButton("Show Info");
		final JButton start_btn = new JButton("Start Competition");
		start_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				new Thread(() -> {
					final GameEngine gameEngine = GameEngine.getInstance();
					gameEngine.startRace(winter_competition);

					while (winter_competition.hasActiveCompetitors()) {
						SwingUtilities.invokeLater(() -> {
							racers_labels.forEach((key, value) -> {
								int y = (int) key.getLocation().getX();
								if (y > screen.getHeight())
									y = screen.getHeight() - RACER_SIZE;

								value.setBounds(value.getX(), y, RACER_SIZE, RACER_SIZE);
							});
							screen.revalidate();
							screen.repaint();
						});

						try {
							Thread.sleep(100);
						} catch (final InterruptedException ex) {
							ex.printStackTrace();
						}
					}

					SwingUtilities.invokeLater(() -> {
						for (final ActionListener al : info_btn.getActionListeners()) {
							info_btn.removeActionListener(al);
						}
					});
				}).start();
			}
		});

		info_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				handle_competitors_info();
			}
		});
		game.add(start_btn);
		game.add(info_btn);
		return game;
	}

	private void print_competitors_info(final DefaultTableModel model) {
		racers_labels.forEach((key, value) -> {
			final WinterSportsman racer = (WinterSportsman) key;
			final String name = racer.getName();
			final int y = (int) racer.getLocation().getX();
			System.out.println(y);
			final double maxSpeed = racer.getMaxSpeed();
			final double speed = racer.getSpeed();
			final boolean isFinished = arena.isFinished(racer);
			final String[] row = {
					name,
					Double.toString(speed),
					Double.toString(maxSpeed),
					Integer.toString(y),
					isFinished ? "Yes" : "No"
			};
			model.addRow(row);
		});
	}
}
