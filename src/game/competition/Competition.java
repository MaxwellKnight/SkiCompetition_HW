package game.competition;

import game.Interfaces.IArena;
import game.Interfaces.ICompetitor;
import utilities.ValidationUtils;

import java.util.*;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */
public abstract class Competition implements Observer {
	protected final IArena arena;
	protected final int maxCompetitors;
	protected ArrayList<ICompetitor> activeCompetitors;
	protected ArrayList<ICompetitor> finishedCompetitors;

	protected boolean isDone = false;

	/**
	 * Constructs a competition with the specified arena and maximum number of
	 * competitors.
	 *
	 * @param arena          The arena where the competition takes place.
	 * @param maxCompetitors The maximum number of competitors allowed in the
	 *                       competition.
	 */
	public Competition(IArena arena, int maxCompetitors) {
		ValidationUtils.assertNotNull(arena);
		ValidationUtils.assertPositive(maxCompetitors);
		this.arena = arena;
		this.maxCompetitors = maxCompetitors;
		activeCompetitors = new ArrayList<>();
		finishedCompetitors = new ArrayList<>();
	}

	public ArrayList<ICompetitor> getCompetitors() {
		return this.activeCompetitors;
	}

	/**
	 * Abstract method to check if a competitor is valid for this competition.
	 *
	 * @param competitor The competitor to validate.
	 * @return True if the competitor is valid for this competition, false
	 *         otherwise.
	 */
	public abstract boolean isValidCompetitor(ICompetitor competitor);

	/**
	 * Plays a turn in the competition, advancing active competitors based on arena
	 * conditions.
	 */
	public synchronized void launch_racers() {
		Iterator<ICompetitor> iter = activeCompetitors.iterator();

		while (iter.hasNext()) {
			ICompetitor current = iter.next();
			Thread sportsman = new Thread(current);
			sportsman.start();
		}
	}

	/**
	 * Updates the state of the competition when an observable notifies the
	 * observer.
	 *
	 * @param observable The observable object that notifies the observer.
	 */
	@Override
	public synchronized void update(Observable observable, Object o) {
		ICompetitor competitor = (ICompetitor) observable;
		activeCompetitors.remove(competitor);
		finishedCompetitors.add(competitor);
		if (activeCompetitors.size() == 0)
			isDone = true;
	}

	public boolean getIsDone() {
		return this.isDone;
	}

	/**
	 * Adds a competitor to the competition if it's valid, initializing their race.
	 *
	 * @param competitor The competitor to add to the competition.
	 * @throws IllegalStateException    If the competition is full.
	 * @throws IllegalArgumentException If the competitor is invalid for this
	 *                                  competition.
	 */
	public void addCompetitor(ICompetitor competitor) {
		if (this.activeCompetitors.size() >= this.maxCompetitors) {
			throw new IllegalStateException(this.arena.toString() + "" + this.maxCompetitors);
		} else if (!this.isValidCompetitor(competitor)) {
			throw new IllegalArgumentException("Invalid competitor " + competitor.toString());
		}
		this.activeCompetitors.add(competitor);
		((Observable) competitor).addObserver(this);
		competitor.initRace(this.arena);
	}

	/**
	 * Checks if there are any active competitors left in the competition.
	 *
	 * @return True if there are active competitors, false otherwise.
	 */
	public boolean hasActiveCompetitors() {
		return !this.activeCompetitors.isEmpty();
	}

	/**
	 * Prints the list of finished competitors in the competition.
	 */
	public void printFinishedCompetitors() {
		int index = 1;
		System.out.println("Race results: ");
		for (ICompetitor finishedCompetitor : this.finishedCompetitors) {
			System.out.println(index++ + ". " + finishedCompetitor.toString());
		}
	}

	/**
	 * Checks if this competition is equal to another object.
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
		Competition that = (Competition) obj;
		return maxCompetitors == that.maxCompetitors &&
				arena.equals(that.arena) &&
				activeCompetitors.equals(that.activeCompetitors) &&
				finishedCompetitors.equals(that.finishedCompetitors);
	}

	/**
	 * Returns a string representation of the competition.
	 *
	 * @return A string representation of the competition object.
	 */
	@Override
	public String toString() {
		return "Competition{" +
				"arena=" + arena +
				", maxCompetitors=" + maxCompetitors +
				", activeCompetitors=" + activeCompetitors +
				", finishedCompetitors=" + finishedCompetitors +
				'}';
	}
}
