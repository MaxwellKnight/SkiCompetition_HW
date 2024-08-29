package game.competition;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.entities.sportsman.Snowboarder;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

public class SnowboardCompetition extends WinterCompetition {
	public SnowboardCompetition(
			final IArena arena, final int maxCompetitors,
			final Discipline discipline, final League league, final Gender gender) {
		super(arena, maxCompetitors, discipline, league, gender);
	}

	@Override
	public boolean isValidCompetitor(final ICompetitor competitor) {
		if (competitor instanceof Snowboarder)
			return super.isValidCompetitor(competitor);
		return false;
	}
}
