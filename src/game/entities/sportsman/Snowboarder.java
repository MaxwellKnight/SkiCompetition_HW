package game.entities.sportsman;

import game.Interfaces.IPrototype;
import game.enums.Discipline;
import game.enums.Gender;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */
public class Snowboarder extends WinterSportsman implements IPrototype {
	public Snowboarder(String name, double age, Gender gender,
			double acceleration, double maxSpeed, Discipline discipline) {
		super(name, age, gender, acceleration, maxSpeed, discipline);
	}

	public String toString() {
		return "Snowboarder " + this.name;
	}

	public Snowboarder clone() {
		return new Snowboarder(name, age, gender, this.getAcceleration(), this.getMaxSpeed(), discipline);
	}
}
