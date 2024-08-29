package game.entities.sportsman.Decorator;

import game.Interfaces.IArena;
import game.Interfaces.IWinterSportsman;
import game.enums.Discipline;
import utilities.Point;

public class WSDecorator implements IWinterSportsman {
	protected IWinterSportsman _wrapedSportsman;

	public WSDecorator(IWinterSportsman sportsman) {
		_wrapedSportsman = sportsman;
	}

	public int getId() {
		return _wrapedSportsman.getId();
	}

	public void setAcceleration(double acceleration) {
		_wrapedSportsman.setAcceleration(acceleration);
	}

	public void setDiscipline(Discipline discipline) {
		_wrapedSportsman.setDiscipline(discipline);
	}

	public Discipline getDiscipline() {
		return _wrapedSportsman.getDiscipline();
	}

	public void initRace(IArena arena) {
		_wrapedSportsman.initRace(arena);
	}

	public Point getLocation() {
		return _wrapedSportsman.getLocation();
	}

	public boolean equals(Object obj) {
		return _wrapedSportsman.equals(obj);
	}

	public String toString() {
		return _wrapedSportsman.toString();
	}

	public double getAcceleration() {
		return _wrapedSportsman.getAcceleration();
	}
}
