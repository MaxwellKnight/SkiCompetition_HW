package gui;

import javax.swing.*;
import java.awt.*;

import game.Interfaces.IArena;
import game.competition.WinterCompetition;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import utilities.ValidationUtils;

public class CompetitionPanel extends JPanel {
	private final App app;
	private final JComboBox<String> competitionKind;
	private final JTextArea competitionMaxCount;
	private final JComboBox<String> competitionDiscipline;
	private final JComboBox<String> competitionLeague;
	private final JComboBox<String> competitionGender;

	private static final String[] COMPETITION = { "Snowboard", "Ski" };
	private static final String[] DISCIPLINE = { "Slalom", "Giant_Slalom", "Downhill", "Freestyle" };
	private static final String[] LEAGUE = { "Junior", "Adult", "Senior" };
	private static final String[] GENDER = { "Female", "Male" };

	public CompetitionPanel(App app) {
		this.app = app;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Create Competition"));

		competitionKind = new JComboBox<>(COMPETITION);
		competitionMaxCount = new JTextArea(1, 0);
		competitionDiscipline = new JComboBox<>(DISCIPLINE);
		competitionLeague = new JComboBox<>(LEAGUE);
		competitionGender = new JComboBox<>(GENDER);

		add(new JLabel("Choose Competition"));
		add(competitionKind);
		add(new JLabel("Max competitors number"));
		add(competitionMaxCount);
		add(new JLabel("Discipline"));
		add(competitionDiscipline);
		add(new JLabel("League"));
		add(competitionLeague);
		add(new JLabel("Gender"));
		add(competitionGender);

		JButton createButton = new JButton("Create Competition");
		createButton.addActionListener(e -> createCompetition());
		add(createButton);
	}

	public App getApp() {
		return app;
	}

	public String getCompetitionKind() {
		return competitionKind.getSelectedItem().toString();
	}

	public JTextArea getCompetitionMaxCount() {
		return competitionMaxCount;
	}

	public JComboBox<String> getCompetitionDiscipline() {
		return competitionDiscipline;
	}

	public JComboBox<String> getCompetitionLeague() {
		return competitionLeague;
	}

	public JComboBox<String> getCompetitionGender() {
		return competitionGender;
	}

	public static String[] getCompetition() {
		return COMPETITION;
	}

	public static String[] getDiscipline() {
		return DISCIPLINE;
	}

	public static String[] getLeague() {
		return LEAGUE;
	}

	public String getGender() {
		return competitionGender.getSelectedItem().toString();
	}

	private void createCompetition() {
		if (app.getArena() == null) {
			ValidationUtils.alert("Must build arena before competition");
			return;
		}
		if (!ValidationUtils.assertStringToNumber(competitionMaxCount.getText(),
				"Please enter a valid number for competition max count")) {
			return;
		}

		int maxCount = Integer.parseInt(competitionMaxCount.getText());
		if (maxCount <= 0 || maxCount > 20) {
			ValidationUtils.alert("Max count number must be in the range of [1,20]");
			return;
		}

		try {
			Class<?> loadedClass = Class
					.forName("game.competition." + competitionKind.getSelectedItem() + "Competition");
			WinterCompetition competition = (WinterCompetition) loadedClass.getConstructor(
					IArena.class, int.class, Discipline.class, League.class, Gender.class)
					.newInstance(
							app.getArena(),
							maxCount,
							Discipline.values()[competitionDiscipline.getSelectedIndex()],
							League.values()[competitionLeague.getSelectedIndex()],
							Gender.values()[competitionGender.getSelectedIndex()]);
			app.setWinterCompetition(competition);
		} catch (Exception error) {
			ValidationUtils.alert(error.toString());
			System.out.println(error.toString());
		}
	}
}
