package game.arena;

import game.Interfaces.IArena;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;

/**
 * ArenaFactory
 */
public class ArenaFactory {

	public IArena createArena(
			String arenaKind,
			double length,
			SnowSurface surface,
			WeatherCondition condition) throws Exception {
		if (arenaKind == "winter")
			return new WinterArena(length, surface, condition);
		else if (arenaKind == "summer")
			return new SummerArena(length, surface, condition);

		throw new Exception("Invalid winter kind");
	}
}
