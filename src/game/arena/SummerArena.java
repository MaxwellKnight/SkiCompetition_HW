package game.arena;

import game.Interfaces.IArena;
import game.Interfaces.IMobileEntity;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;
import utilities.ValidationUtils;

public class SummerArena implements IArena {

	protected double length;
	protected SnowSurface surface;
	protected WeatherCondition condition;
	protected double friction;

	/**
	 * Constructs a SummerArena with the specified attributes.
	 *
	 * @param length    The length of the winter arena (must be positive).
	 * @param surface   The snow surface of the arena (must not be null).
	 * @param condition The weather condition of the arena (must not be null).
	 * @throws IllegalArgumentException If length is not positive.
	 * @throws NullPointerException     If surface or condition is null.
	 */
	public SummerArena(double length, SnowSurface surface, WeatherCondition condition) {
		ValidationUtils.assertNotNull(surface);
		ValidationUtils.assertNotNull(condition);
		ValidationUtils.assertPositive(length);
		this.length = length;
		this.surface = surface;
		this.condition = condition;
	}

	/**
	 * Sets the friction coefficient of the arena.
	 *
	 * @param friction The new friction coefficient to set.
	 */
	public void setFriction(double friction) {
		this.friction = friction;
	}

	/**
	 * Sets the weather condition of the arena.
	 *
	 * @param condition The new weather condition to set (must not be null).
	 * @throws NullPointerException If condition is null.
	 */
	public void setCondition(WeatherCondition condition) {
		ValidationUtils.assertNotNull(condition);
		this.condition = condition;
	}

	/**
	 * Sets the length of the winter arena.
	 *
	 * @param length The new length to set (must be positive).
	 * @throws IllegalArgumentException If length is not positive.
	 */
	public void setLength(double length) {
		ValidationUtils.assertPositive(length);
		this.length = length;
	}

	/**
	 * Sets the snow surface of the arena.
	 *
	 * @param surface The new snow surface to set (must not be null).
	 * @throws NullPointerException If surface is null.
	 */
	public void setSurface(SnowSurface surface) {
		ValidationUtils.assertNotNull(surface);
		this.surface = surface;
	}

	/**
	 * Retrieves the friction coefficient of the arena.
	 *
	 * @return The friction coefficient of the arena.
	 */
	public double getFriction() {
		return this.surface.getFriction();
	}

	/**
	 * Retrieves the length of the winter arena.
	 *
	 * @return The length of the winter arena.
	 */
	public double getLength() {
		return this.length;
	}

	/**
	 * Retrieves the snow surface of the arena.
	 *
	 * @return The snow surface of the arena.
	 */
	public SnowSurface getSurface() {
		return this.surface;
	}

	/**
	 * Retrieves the weather condition of the arena.
	 *
	 * @return The weather condition of the arena.
	 */
	public WeatherCondition getCondition() {
		return this.condition;
	}

	/**
	 * Checks if the given mobile entity has finished the race within the arena.
	 *
	 * @param entity The mobile entity to check.
	 * @return True if the entity has crossed the length of the arena, false
	 *         otherwise.
	 */
	public boolean isFinished(IMobileEntity entity) {
		return entity.getLocation().getX() > length;
	}

	/**
	 * Generates a string representation of the winter arena.
	 *
	 * @return A string representation of the winter arena.
	 */
	@Override
	public String toString() {
		return "Summer Arena";
	}
}
