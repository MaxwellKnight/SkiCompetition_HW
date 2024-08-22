package game.entities.sportsman;

import game.Interfaces.IPrototype;
import game.enums.Discipline;
import game.enums.Gender;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */

public class Skier extends WinterSportsman implements IPrototype {

	public Skier(String name, double age, Gender gender,
			double acceleration, double maxSpeed, Discipline discipline) {
		super(name, age, gender, acceleration, maxSpeed, discipline);
	}

	public Skier clone() {
		return new Skier(name, age, gender, this.getAcceleration(), this.getMaxSpeed(), discipline);
	}

	public String toString() {
		return "Skier " + this.name;
	}
}
