package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import game.GameEngine;
import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.arena.WinterArena;
import game.competition.Competition;
import game.competition.SkiCompetition;
import game.competition.SnowboardCompetition;
import game.competition.WinterCompetition;
import game.entities.sportsman.Skier;
import game.entities.sportsman.Snowboarder;
import game.entities.sportsman.WinterSportsman;
import game.enums.*;
import utilities.ValidationUtils;

public class App extends JFrame {

	private WinterArena arena = null;
	private Competition winter_competition = null;
	private HashMap<ICompetitor, JLabel> racers_labels = new HashMap<>();

	private JPanelWithBackground screen;
	private JPanel controls;
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

	private JTextArea arena_length = new JTextArea(1, 0);
	private JComboBox arena_surface = new JComboBox(surfaces);
	private JComboBox arena_weather = new JComboBox(weather);

	private JComboBox competition_kind = new JComboBox<>(competition);
	private JTextArea competition_max_count = new JTextArea();
	private JComboBox competition_discipline = new JComboBox<>(discipline);
	private JComboBox competition_league = new JComboBox<>(league);
	private JComboBox competition_gender = new JComboBox<>(gender);

	private JTextArea competitor_name = new JTextArea();
	private JTextArea competitor_age = new JTextArea();
	private JTextArea competitor_max_speed = new JTextArea(1, 0);
	private JTextArea competitor_acceleration = new JTextArea();

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

	private JLabel createLeftAlignedLabel(String text) {
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		return label;
	}

	private JPanel build_arena() {
		JPanel arena_panel = new JPanel();
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

		JButton btn = new JButton("Build Arena");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ValidationUtils.assertStringToNumber(arena_length.getText(),
						"Please enter a valid number for competition max count")) {
					return;
				}

				width = Integer.parseInt(arena_length.getText());
				screen.setSize(width, HEIGHT);
				setSize(width + SIDEBAR, HEIGHT);
				int surface_index = arena_surface.getSelectedIndex();
				int weather_index = arena_weather.getSelectedIndex();
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

	private JPanel build_competition() {
		JPanel competition_panel = new JPanel();
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

		JButton btn = new JButton("Create Competition");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (arena == null) {
					ValidationUtils.alert("Must build arena before competition");
					return;
				}
				if (!ValidationUtils.assertStringToNumber(competition_max_count.getText(),
						"Please enter a valid number for competition max count")) {
					return;
				}

				int max_count = Integer.parseInt(competition_max_count.getText());
				if (max_count <= 0 || max_count > 20) {
					ValidationUtils.alert("Max count number must be in the range of [1,20]");
					return;
				}

				try {
					Class<?> loaded_class = Class
							.forName("game.competition." + competition_kind.getSelectedItem() + "Competition");
					Constructor<?> ctor = loaded_class.getConstructor(WinterArena.class, int.class, Discipline.class,
							League.class, Gender.class);
					winter_competition = (WinterCompetition) ctor.newInstance(
							arena,
							max_count,
							Discipline.values()[competition_discipline.getSelectedIndex()],
							League.values()[competition_league.getSelectedIndex()],
							Gender.values()[competition_gender.getSelectedIndex()]);
				} catch (Exception error) {
					ValidationUtils.alert(error.toString());
				}
			}

		});
		competition_panel.add(btn);

		return competition_panel;
	}

	private JPanel add_competitor() {
		JPanel competitor = new JPanel();
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

		JButton btn = new JButton("Add Competitor");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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

				String name = competitor_name.getText();
				double age = Double.parseDouble(competitor_age.getText());
				double max_speed = Double.parseDouble(competitor_max_speed.getText());
				double acceleration = Double.parseDouble(competitor_acceleration.getText());
				WinterCompetition comp = (WinterCompetition) winter_competition;
				String kind = (String) competition_kind.getSelectedItem();

				try {
					Class<?> loaded_class = Class.forName("game.entities.sportsman." + kind + "er");
					Constructor<?> ctor = loaded_class.getConstructor(String.class, double.class, Gender.class,
							double.class, double.class, Discipline.class);

					WinterSportsman racer = (WinterSportsman) ctor.newInstance(
							name,
							age,
							comp.getGender(),
							acceleration,
							max_speed,
							comp.getDiscipline());
					winter_competition.addCompetitor(racer);

					String path = "icons/" + kind + competition_gender.getSelectedItem() + ".png";
					JLabel racerIcon = createRacerLabel(path);

					int x = (int) racer.getLocation().getX();
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
				} catch (Exception error) {
					System.out.println(error);
					ValidationUtils.alert("Competitor is not fit to competition! Choose another competitor.");
				}
			}
		});
		competitor.add(btn);
		return competitor;
	}

	private JLabel createRacerLabel(String iconPath) {
		ImageIcon loaded_img = new ImageIcon(iconPath);
		Image scaled = loaded_img.getImage().getScaledInstance(RACER_SIZE, RACER_SIZE, Image.SCALE_SMOOTH);
		loaded_img = new ImageIcon(scaled);
		JLabel racerIcon = new JLabel(loaded_img);
		racerIcon.setVisible(true);
		return racerIcon;
	}

	private JPanel game() {
		JPanel game = new JPanel();
		game.setVisible(true);
		game.setPreferredSize(new Dimension(SIDEBAR, 200));
		game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
		game.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

		JButton start_btn = new JButton("Start Competition");
		start_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(() -> {
					GameEngine gameEngine = GameEngine.getInstance();
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
					}
				}).start();
			}
		});

		JButton info_btn = new JButton("Show Info");
		info_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handle_competitors_info();
			}
		});
		game.add(start_btn);
		game.add(info_btn);
		return game;
	}

	public void handle_competitors_info() {
		String[] column_names = { "Name", "Speed", "Max speed", "Location", "Finished" };

		JFrame info_panel = new JFrame();
		info_panel.setPreferredSize(new Dimension(500, 300));
		info_panel.setTitle("Competitors information");
		info_panel.setLayout(new BorderLayout());

		DefaultTableModel model = new DefaultTableModel(column_names, 0);
		JTable table = new JTable(model);
		JScrollPane scroll_pane = new JScrollPane(table);
		info_panel.add(scroll_pane, BorderLayout.CENTER);

		info_panel.pack();
		info_panel.setVisible(true);

		new Thread(() -> {
			while (winter_competition.hasActiveCompetitors()) {
				SwingUtilities.invokeLater(() -> {
					model.setRowCount(0); // Clear existing rows
					print_competitors_info(model);
				});

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.fireTableDataChanged();
				table.revalidate();
				table.repaint();

				if (!winter_competition.hasActiveCompetitors()) {
					((Timer) e.getSource()).stop();
				}
			}
		});
		timer.start();
	}

	private void print_competitors_info(DefaultTableModel model) {
		racers_labels.forEach((key, value) -> {
			WinterSportsman racer = (WinterSportsman) key;
			String name = racer.getName();
			int y = (int) racer.getLocation().getX();
			System.out.println(y);
			double maxSpeed = racer.getMaxSpeed();
			double speed = racer.getSpeed();
			boolean isFinished = arena.isFinished(racer);
			String[] row = {
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
