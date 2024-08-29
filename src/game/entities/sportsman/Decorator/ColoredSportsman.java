package game.entities.sportsman.Decorator;

import java.awt.Color;

import game.Interfaces.IWinterSportsman;

public class ColoredSportsman extends WSDecorator {
	public ColoredSportsman(final IWinterSportsman sportsman) {
		super(sportsman);
	}

	public void colorSportsman(final Color color) {
		_wrapedSportsman.setColor(color);
	}
}
