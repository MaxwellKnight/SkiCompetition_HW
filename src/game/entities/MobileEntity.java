package game.entities;

import game.Interfaces.IMobileEntity;
import utilities.Point;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 */

public class MobileEntity extends Entity implements IMobileEntity {
	protected double maxSpeed;
	protected double acceleration;
	protected double currentSpeed;

	/**
	 * Constructs a mobile entity with specified maximum speed and acceleration.
	 *
	 * @param maxSpeed     The maximum speed of the mobile entity.
	 * @param acceleration The acceleration of the mobile entity.
	 */
	public MobileEntity(double maxSpeed, double acceleration) {
		super();
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.currentSpeed = 0;
	}

	/**
	 * Moves the mobile entity according to the given friction.
	 * Updates the current speed and adjusts the location based on the speed.
	 *
	 * @param friction The friction affecting the movement.
	 */
	public void move(double friction) {
		if (this.currentSpeed < this.maxSpeed) {
			this.currentSpeed += friction * this.acceleration;
			if (this.currentSpeed > this.maxSpeed)
				this.currentSpeed = this.maxSpeed;
		}
		this.location.setX(this.location.getX() + this.currentSpeed);
	}

	/**
	 * Retrieves the current location of the mobile entity.
	 *
	 * @return The current location of the mobile entity as a Point object.
	 */
	public Point getLocation() {
		return this.location;
	}

	/**
	 * Retrieves the maximum speed of the mobile entity.
	 *
	 * @return The maximum speed of the mobile entity.
	 */
	public double getMaxSpeed() {
		return this.maxSpeed;
	}

	/**
	 * Retrieves the acceleration of the mobile entity.
	 *
	 * @return The acceleration of the mobile entity.
	 */
	public double getAcceleration() {
		return this.acceleration;
	}

	public double getSpeed() {
		return this.currentSpeed;
	}

	/**
	 * Sets the location of the mobile entity.
	 *
	 * @param location The new location to set.
	 */
	public void setLocation(Point location) {
		this.location = new Point(location);
	}

	/**
	 * Sets the acceleration of the mobile entity.
	 *
	 * @param acceleration The new acceleration to set.
	 */
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * Sets the maximum speed of the mobile entity.
	 *
	 * @param maxSpeed The new maximum speed to set.
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Compares this mobile entity with another object for equality.
	 * Mobile entities are considered equal if they have the same class,
	 * maximum speed, acceleration, current speed, and location.
	 *
	 * @param obj The object to compare with.
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
		MobileEntity entity = (MobileEntity) obj;
		return Double.compare(entity.maxSpeed, maxSpeed) == 0 &&
				Double.compare(entity.acceleration, acceleration) == 0 &&
				Double.compare(entity.currentSpeed, currentSpeed) == 0 &&
				location.equals(entity.location);
	}

	/**
	 * Returns a string representation of the mobile entity.
	 *
	 * @return A string representation including maxSpeed, acceleration,
	 *         currentSpeed, and location.
	 */
	@Override
	public String toString() {
		return "MobileEntity{" +
				"maxSpeed=" + maxSpeed +
				", acceleration=" + acceleration +
				", currentSpeed=" + currentSpeed +
				", location=" + location +
				'}';
	}
}
