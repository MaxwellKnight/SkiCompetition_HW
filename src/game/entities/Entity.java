package game.entities;

import utilities.Point;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */

/**
 * Abstract class representing an entity with a location in a 2D space.
 */
abstract public class Entity {

	protected Point location;

	/**
	 * Default constructor that initializes the location to (0,0).
	 */
	public Entity() {
		this.location = new Point();
	}

	/**
	 * Constructor that initializes the location to the specified point.
	 *
	 * @param p The initial location of the entity.
	 */
	public Entity(Point p) {
		this.location = p;
	}

	/**
	 * Checks equality between this entity and another object.
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
		Entity entity = (Entity) obj;
		return location.equals(entity.location);
	}

	/**
	 * Generates a string representation of the entity.
	 *
	 * @return A string representation of the entity, including its location.
	 */
	@Override
	public String toString() {
		return "Entity{" +
				"location=" + location +
				'}';
	}
}
