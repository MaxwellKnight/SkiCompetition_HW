package gui;

import javax.swing.*;

import game.competition.WinterCompetition;
import game.entities.sportsman.WinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import utilities.ValidationUtils;

public class CompetitorPanel extends JPanel {
	private final App app;
	private final JTextArea competitorName;
	private final JTextArea competitorAge;
	private final JTextArea competitorMaxSpeed;
	private final JTextArea competitorAcceleration;
	private final CompetitionPanel competitionPanel;

	public CompetitorPanel(App app, CompetitionPanel competitionPanel) {
		this.app = app;
		this.competitionPanel = competitionPanel;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Create Competitor"));

		competitorName = new JTextArea(1, 0);
		competitorAge = new JTextArea(1, 0);
		competitorMaxSpeed = new JTextArea(1, 0);
		competitorAcceleration = new JTextArea(1, 0);

		add(new JLabel("Name"));
		add(competitorName);
		add(new JLabel("Age"));
		add(competitorAge);
		add(new JLabel("Max speed"));
		add(competitorMaxSpeed);
		add(new JLabel("Acceleration"));
		add(competitorAcceleration);

		JButton addButton = new JButton("Add Competitor");
		addButton.addActionListener(e -> addCompetitor());
		add(addButton);
	}

	private void addCompetitor() {
		if (app.getArena() == null || app.getWinterCompetition() == null) {
			ValidationUtils.alert("Must build arena and create competition before adding competitors");
			return;
		}
		if (!ValidationUtils.assertStringToDouble(competitorAge.getText(), "Age must be a number") ||
				!ValidationUtils.assertStringToDouble(competitorMaxSpeed.getText(), "Max speed must be number") ||
				!ValidationUtils.assertStringToDouble(competitorAcceleration.getText(),
						"Acceleration must be a number")) {
			return;
		}

		String name = competitorName.getText();
		double age = Double.parseDouble(competitorAge.getText());
		double maxSpeed = Double.parseDouble(competitorMaxSpeed.getText());
		double acceleration = Double.parseDouble(competitorAcceleration.getText());
		String kind = competitionPanel.getCompetitionKind();

		try {
			Class<?> loadedClass = Class.forName("game.entities.sportsman." + kind + "er");
			WinterCompetition competition = (WinterCompetition) app.getWinterCompetition();
			WinterSportsman racer = (WinterSportsman) loadedClass.getConstructor(
					String.class, double.class, Gender.class, double.class, double.class, Discipline.class)
					.newInstance(
							name,
							age,
							competition.getGender(),
							acceleration,
							maxSpeed,
							competition.getDiscipline());
			racer.addObserver(app);
			app.getWinterCompetition().addCompetitor(racer);
			final String path = "icons/" + kind + competitionPanel.getGender() + ".png";
			app.addRacerLabel(racer, path);
		} catch (Exception error) {
			System.out.println(error);
			ValidationUtils.alert("Competitor is not fit to competition! Choose another competitor.");
		}
	}
}
