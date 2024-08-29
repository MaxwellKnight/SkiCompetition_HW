package game.competition;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitionBuilder;
import game.enums.Discipline;
import game.enums.League;
import game.enums.Gender;

/**
 * Competition Builder
 */
public class SkiCompetitionBuilder implements ICompetitionBuilder {
	private IArena _arena;
	private int _maxCompetitors;
	private Discipline _discipline;
	private League _league;
	private Gender _gender;

	public SkiCompetitionBuilder arena(final IArena arena) {
		this._arena = arena;
		return this;
	}

	public SkiCompetitionBuilder maxCompetitors(final int max_competitors) {
		this._maxCompetitors = max_competitors;
		return this;
	}

	public SkiCompetitionBuilder discipline(final Discipline discipline) {
		this._discipline = discipline;
		return this;
	}

	public SkiCompetitionBuilder league(final League league) {
		this._league = league;
		return this;
	}

	public SkiCompetitionBuilder gender(final Gender gender) {
		this._gender = gender;
		return this;
	}

	public SkiCompetition build() {
		return new SkiCompetition(
				_arena,
				_maxCompetitors,
				_discipline,
				_league,
				_gender);
	}
}
