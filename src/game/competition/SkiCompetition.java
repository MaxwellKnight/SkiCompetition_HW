package game.competition;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import game.entities.sportsman.Skier;

public class SkiCompetition extends WinterCompetition {

	/**
	 * Constructs a SkiCompetition with the specified attributes.
	 *
	 * @param arena          The WinterArena where the competition takes place.
	 * @param maxCompetitors The maximum number of competitors allowed in the
	 *                       competition.
	 * @param discipline     The discipline of the competition.
	 * @param league         The league associated with the competition.
	 * @param gender         The gender of competitors allowed in the competition.
	 */
	public SkiCompetition(
			final IArena arena, final int maxCompetitors,
			final Discipline discipline, final League league, final Gender gender) {
		super(arena, maxCompetitors, discipline, league, gender);
	}

	/**
	 * Checks if a competitor is valid for this ski competition.
	 * Only instances of Skier are considered valid competitors.
	 *
	 * @param competitor The competitor to validate.
	 * @return True if the competitor is a Skier and meets all other criteria, false
	 *         otherwise.
	 */
	@Override
	public boolean isValidCompetitor(final ICompetitor competitor) {
		if (competitor instanceof Skier)
			return super.isValidCompetitor(competitor);
		return false;
	}
}
