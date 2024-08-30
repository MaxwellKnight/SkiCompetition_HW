package game.entities.sportsman.Decorator;

import java.awt.Color;

import game.Interfaces.IWinterSportsman;

public class ColoredSportsman extends WSDecorator {
	private Color color;

	public ColoredSportsman(final IWinterSportsman sportsman) {
		super(sportsman);
		this.color = Color.BLACK; // Default color
	}

	public void colorSportsman(final Color color) {
		this.color = color;
		_wrapedSportsman.setColor(color);
	}

	public Color getColor() {
		return this.color;
	}
}
