package game.entities.sportsman;

import game.entities.MobileEntity;
import game.enums.Gender;
import utilities.ValidationUtils;

import java.awt.Point;
import java.util.*;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;

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
	protected Vector<Observer> observers;

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
	public Sportsman(String name, double age, Gender gender, double acceleration, double maxSpeed) {
		ValidationUtils.assertNotNullOrEmptyString(name);
		ValidationUtils.assertPositive(age);
		ValidationUtils.assertNotNull(gender);
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.observers = new Vector<>();
		this.entity = new MobileEntity(maxSpeed, acceleration);
	}

	public void run() {
		if (this.arena == null)
			throw new Error("Arena cannot be null while running");

		while (!this.arena.isFinished(this.entity)) {
			this.entity.move(this.arena.getFriction());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}

		this.entity.setLocation(new Point(
				this.entity.getLocation().getX(),
				(double) this.arena.getLength()));
		this.notifyObservers();
	}

	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void deleteObserver(Observer observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		Iterator<Observer> iter = this.observers.iterator();

		while (iter.hasNext()) {
			Observer current = iter.next();
			current.update(this, current);
		}
	}

	public void move(double friction) {
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
	public void setName(String name) {
		ValidationUtils.assertNotNullOrEmptyString(name);
		this.name = name;
	}

	/**
	 * Sets the age of the sportsman.
	 *
	 * @param age The new age of the sportsman.
	 * @throws IllegalArgumentException If the age is not positive.
	 */
	public void setAge(double age) {
		ValidationUtils.assertPositive(age);
		this.age = age;
	}

	/**
	 * Sets the gender of the sportsman.
	 *
	 * @param gender The new gender of the sportsman.
	 * @throws NullPointerException If the gender is null.
	 */
	public void setGender(Gender gender) {
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
		Sportsman sportsman = (Sportsman) obj;
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
