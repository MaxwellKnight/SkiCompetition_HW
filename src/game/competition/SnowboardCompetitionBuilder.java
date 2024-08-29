package game.competition;

import game.Interfaces.IArena;
import game.enums.Discipline;
import game.enums.League;
import game.enums.Gender;

/**
 * Competition Builder
 */
public class SnowboardCompetitionBuilder {
	private IArena _arena;
	private int _maxCompetitors;
	private Discipline _discipline;
	private League _league;
	private Gender _gender;

	public SnowboardCompetitionBuilder arena(final IArena arena) {
		this._arena = arena;
		return this;
	}

	public SnowboardCompetitionBuilder maxCompetitors(final int max_competitors) {
		this._maxCompetitors = max_competitors;
		return this;
	}

	public SnowboardCompetitionBuilder discipline(final Discipline discipline) {
		this._discipline = discipline;
		return this;
	}

	public SnowboardCompetitionBuilder league(final League league) {
		this._league = league;
		return this;
	}

	public SnowboardCompetitionBuilder gender(final Gender gender) {
		this._gender = gender;
		return this;
	}

	public SnowboardCompetition build(final String kind) {
		return new SnowboardCompetition(
				_arena,
				_maxCompetitors,
				_discipline,
				_league,
				_gender);
	}
}
