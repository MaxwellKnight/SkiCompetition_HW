package game.competition;

import game.Interfaces.IPrototype;
import game.arena.WinterArena;
import game.entities.sportsman.Skier;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;

public class CompetitionDirector {
	/**
	 * @param builder
	 * @param N
	 * @return default SkiCompetition with N default competitiors
	 */
	public SkiCompetition makeDefault(final SkiCompetitionBuilder builder, final int N) {
		final SkiCompetition competition = (SkiCompetition) builder
				.arena(new WinterArena(800, SnowSurface.CRUD, WeatherCondition.SUNNY))
				.maxCompetitors(N)
				.gender(Gender.MALE)
				.league(League.ADULT)
				.discipline(Discipline.DOWNHILL)
				.build();

		final IPrototype racer = new Skier("default", 20, Gender.MALE, 15, 30, Discipline.DOWNHILL);
		for (int i = 0; i < N; i++)
			competition.addCompetitor(racer.clone());

		return competition;
	}

	public SkiCompetition makeSki(final SkiCompetitionBuilder builder, final int N) {
		return (SkiCompetition) builder
				.arena(new WinterArena(800, SnowSurface.CRUD, WeatherCondition.SUNNY))
				.maxCompetitors(N)
				.gender(Gender.MALE)
				.league(League.ADULT)
				.discipline(Discipline.DOWNHILL)
				.build();
	}

	public SnowboardCompetition makeSnowboard(final SnowboardCompetitionBuilder builder, final int N) {
		return (SnowboardCompetition) builder
				.arena(new WinterArena(800, SnowSurface.CRUD, WeatherCondition.SUNNY))
				.maxCompetitors(N)
				.gender(Gender.MALE)
				.league(League.ADULT)
				.discipline(Discipline.DOWNHILL)
				.build();
	}

	@Override
	public String toString() {
		return "CompetitionDirector []";
	}
}
