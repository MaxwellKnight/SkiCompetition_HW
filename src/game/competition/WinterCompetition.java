package game.competition;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.entities.sportsman.WinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import utilities.ValidationUtils;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */

public class WinterCompetition extends Competition {
	protected Discipline discipline;
	protected League league;
	protected Gender gender;

	/**
	 * Constructs a winter competition with specified parameters.
	 *
	 * @param arena          The winter arena where the competition takes place.
	 * @param maxCompetitors The maximum number of competitors allowed in the
	 *                       competition.
	 * @param discipline     The specific discipline of the competition (e.g.,
	 *                       skiing, snowboarding).
	 * @param league         The league or category of the competition.
	 * @param gender         The gender restriction for competitors in the
	 *                       competition.
	 */
	public WinterCompetition(
			final IArena arena, final int maxCompetitors,
			final Discipline discipline, final League league, final Gender gender) {
		super(arena, maxCompetitors);
		ValidationUtils.assertNotNull(league);
		ValidationUtils.assertNotNull(discipline);
		this.discipline = discipline;
		this.gender = gender;
		this.league = league;
	}

	/**
	 * Checks if a competitor is valid for this winter competition.
	 * A competitor is valid if they meet the age requirement of the league,
	 * belong to the specified gender, and participate in the specified discipline.
	 *
	 * @param competitor The competitor to validate.
	 * @return True if the competitor is valid, false otherwise.
	 */
	@Override
	public boolean isValidCompetitor(final ICompetitor competitor) {
		return league.isInLeague(((WinterSportsman) competitor).getAge())
				&& ((WinterSportsman) competitor).getGender() == gender
				&& ((WinterSportsman) competitor).getDiscipline() == discipline;
	}

	public Discipline getDiscipline() {
		return this.discipline;
	}

	public Gender getGender() {
		return this.gender;
	}
}
