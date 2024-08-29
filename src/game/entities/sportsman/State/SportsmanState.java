package game.entities.sportsman.State;

import game.entities.sportsman.Sportsman;

/**
 * 
 */
public abstract class SportsmanState {
	Sportsman sportsman;

	public SportsmanState(Sportsman sportsman) {
		this.sportsman = sportsman;
	}

	public abstract void action();
}
