package game.entities.sportsman.Decorator;

import game.Interfaces.IWinterSportsman;

public class SpeedySportsman extends WSDecorator {
	public SpeedySportsman(IWinterSportsman sportsman) {
		super(sportsman);
	}

	public void increaseAcceleration(double acceleration) {
		_wrapedSportsman.setAcceleration(_wrapedSportsman.getAcceleration() + acceleration);
	}
}
