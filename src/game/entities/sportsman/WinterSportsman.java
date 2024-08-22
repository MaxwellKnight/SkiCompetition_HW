package game.entities.sportsman;

import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import utilities.Point;
import utilities.ValidationUtils;
import game.Interfaces.IArena;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */
public class WinterSportsman extends Sportsman {
	private Discipline discipline;

	/**
	 * Constructs a WinterSportsman with the specified attributes.
	 *
	 * @param name         The name of the winter sportsman.
	 * @param age          The age of the winter sportsman (must be positive).
	 * @param gender       The gender of the winter sportsman (must not be null).
	 * @param acceleration The acceleration capability of the winter sportsman.
	 * @param maxSpeed     The maximum speed capability of the winter sportsman.
	 * @param discipline   The discipline in which the winter sportsman competes
	 *                     (must not be null).
	 * @throws NullPointerException     If discipline, gender, or name is null.
	 * @throws IllegalArgumentException If age is not positive.
	 */
	public WinterSportsman(String name, double age, Gender gender,
			double acceleration, double maxSpeed, Discipline discipline) {
		super(name, age, gender, acceleration, maxSpeed);
		ValidationUtils.assertNotNull(discipline);
		this.discipline = discipline;
	}

	/**
	 * Sets the discipline of the winter sportsman.
	 *
	 * @param discipline The new discipline to set (must not be null).
	 * @throws NullPointerException If discipline is null.
	 */
	public void setDiscipline(Discipline discipline) {
		ValidationUtils.assertNotNull(discipline);
		this.discipline = discipline;
	}

	/**
	 * Retrieves the discipline of the winter sportsman.
	 *
	 * @return The discipline of the winter sportsman.
	 */
	public Discipline getDiscipline() {
		return this.discipline;
	}

	/**
	 * Initializes the race for the winter sportsman, adjusting acceleration based
	 * on league bonus,
	 * and resetting location and speed.
	 */
	public void initRace(IArena arena) {
		double acceleration = this.entity.getAcceleration() + League.calcAccelerationBonus(this.age);
		this.entity.setAcceleration(acceleration);
		this.entity.setLocation(new Point());
		this.arena = arena;
	}

	public Point getLocation() {
		return this.entity.getLocation();
	}

	/**
	 * Compares this winter sportsman to another object for equality.
	 *
	 * @param obj The object to compare to.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		if (!super.equals(obj)) {
			return false;
		}
		WinterSportsman wSportsman = (WinterSportsman) obj;
		return discipline == wSportsman.discipline;
	}

	/**
	 * Generates a string representation of the winter sportsman.
	 *
	 * @return A string representation of the winter sportsman.
	 */
	@Override
	public String toString() {
		return "WinterSportsman{" +
				"discipline=" + discipline +
				", " + super.toString().substring(10); // Remove the "Sportsman{" part
	}
}
