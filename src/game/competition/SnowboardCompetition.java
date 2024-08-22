package game.competition;

import game.Interfaces.ICompetitor;
import game.arena.WinterArena;
import game.entities.sportsman.Snowboarder;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

public class SnowboardCompetition extends WinterCompetition {
	public SnowboardCompetition(
			WinterArena arena, int maxCompetitors,
			Discipline discipline, League league, Gender gender) {
		super(arena, maxCompetitors, discipline, league, gender);
	}

	@Override
	public boolean isValidCompetitor(ICompetitor competitor) {
		if (competitor instanceof Snowboarder)
			return super.isValidCompetitor(competitor);
		return false;
	}
}
