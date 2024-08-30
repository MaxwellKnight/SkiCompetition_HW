package game.entities.sportsman.State;

import game.entities.sportsman.Sportsman;

public class SportsmanInjured extends SportsmanState {
	private final long injuryTime;

	public SportsmanInjured(Sportsman sportsman, final long injuryTime) {
		super(sportsman);
		this.injuryTime = injuryTime;
	}

	public void action() {
	}
}
