package game.Interfaces;

import java.awt.Color;

import game.enums.Discipline;
import utilities.Point;

public interface IWinterSportsman {
	public int getId();

	public void setDiscipline(Discipline discipline);

	public void setAcceleration(double acceleration);

	public Discipline getDiscipline();

	public void initRace(IArena arena);

	public Point getLocation();

	public boolean equals(Object obj);

	public String toString();

	public double getAcceleration();

	public void setColor(Color color);

	public Color getColor();
}
