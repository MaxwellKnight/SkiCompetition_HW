package game.Interfaces;

import game.competition.Competition;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

public interface ICompetitionBuilder {

	public ICompetitionBuilder arena(final IArena arena);

	public ICompetitionBuilder maxCompetitors(final int max_competitors);

	public ICompetitionBuilder discipline(final Discipline discipline);

	public ICompetitionBuilder league(final League league);

	public ICompetitionBuilder gender(final Gender gender);

	public Competition build();
}
