package game.entities.sportsman;

import java.awt.Color;
import java.util.Date;
import java.util.Observable;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import game.entities.MobileEntity;
import game.entities.sportsman.State.SportsmanActive;
import game.entities.sportsman.State.SportsmanState;
import game.enums.Gender;
import utilities.Point;
import utilities.ValidationUtils;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */

public abstract class Sportsman extends Observable implements ICompetitor {
	protected String name;
	protected double age;
	protected Gender gender;
	protected IArena arena;
	protected MobileEntity entity;
	protected Color color;
	protected Date date;
	protected SportsmanState state;

	/**
	 * Constructs a Sportsman with the specified attributes.
	 *
	 * @param name         The name of the sportsman.
	 * @param age          The age of the sportsman.
	 * @param gender       The gender of the sportsman.
	 * @param acceleration The acceleration of the sportsman.
	 * @param maxSpeed     The maximum speed of the sportsman.
	 * @throws IllegalArgumentException If the name is null or empty, or if age is
	 *                                  not positive.
	 * @throws NullPointerException     If the gender is null.
	 */
	public Sportsman(final String name, final double age, final Gender gender, final double acceleration,
			final double maxSpeed) {
		ValidationUtils.assertNotNullOrEmptyString(name);
		ValidationUtils.assertPositive(age);
		ValidationUtils.assertNotNull(gender);
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.entity = new MobileEntity(maxSpeed, acceleration);
		this.state = new SportsmanActive(this);
	}

	public void run() {
		if (this.arena == null)
			throw new Error("Arena cannot be null while running");

		while (!this.arena.isFinished(this.entity)) {
			this.entity.move(this.arena.getFriction());
			this.setChanged();
			this.notifyObservers();
			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}

		this.entity.setLocation(new Point(
				(double) this.entity.getLocation().getX(),
				(double) this.arena.getLength()));
		this.notifyObservers();
	}

	public void recordTime() {
		date = new Date();
	}

	public Date getDate() {
		return this.date;
	}

	public void changeState(SportsmanState state) {
		this.state = state;
		this.state.action();
		this.setChanged();
		this.notifyObservers();
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void move(final double friction) {
		this.entity.move(friction);
	}

	/**
	 * Retrieves the name of the sportsman.
	 *
	 * @return The name of the sportsman.
	 */
	public String getName() {
		return this.name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public double getAcceleration() {
		return entity.getAcceleration();
	}

	/**
	 * @param acceleration
	 */
	public void setAcceleration(final double acceleration) {
		entity.setAcceleration(acceleration);
	}

	public double getMaxSpeed() {
		return this.entity.getMaxSpeed();
	}

	public double getSpeed() {
		return this.entity.getSpeed();
	}

	/**
	 * Retrieves the age of the sportsman.
	 *
	 * @return The age of the sportsman.
	 */
	public double getAge() {
		return this.age;
	}

	/**
	 * Retrieves the gender of the sportsman.
	 *
	 * @return The gender of the sportsman.
	 */
	public Gender getGender() {
		return this.gender;
	}

	/**
	 * Sets the name of the sportsman.
	 *
	 * @param name The new name of the sportsman.
	 * @throws IllegalArgumentException If the name is null or empty.
	 */
	public void setName(final String name) {
		ValidationUtils.assertNotNullOrEmptyString(name);
		this.name = name;
	}

	/**
	 * Sets the age of the sportsman.
	 *
	 * @param age The new age of the sportsman.
	 * @throws IllegalArgumentException If the age is not positive.
	 */
	public void setAge(final double age) {
		ValidationUtils.assertPositive(age);
		this.age = age;
	}

	/**
	 * Sets the gender of the sportsman.
	 *
	 * @param gender The new gender of the sportsman.
	 * @throws NullPointerException If the gender is null.
	 */
	public void setGender(final Gender gender) {
		ValidationUtils.assertNotNull(gender);
		this.gender = gender;
	}

	/**
	 * Checks equality between this sportsman and another object.
	 *
	 * @param obj The object to compare for equality.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		if (!super.equals(obj)) {
			return false;
		}
		final Sportsman sportsman = (Sportsman) obj;
		return name.equals(sportsman.name) &&
				Double.compare(sportsman.age, age) == 0 &&
				gender == sportsman.gender;
	}

	/**
	 * Generates a string representation of the sportsman.
	 *
	 * @return A string representation of the sportsman, including its name, age,
	 *         gender, and inherited attributes.
	 */
	@Override
	public String toString() {
		return "Sportsman{" +
				"name='" + name + '\'' +
				", age=" + age +
				", gender=" + gender +
				", " + super.toString().substring(12); // Remove the "MobileEntity{" part
	}
}
