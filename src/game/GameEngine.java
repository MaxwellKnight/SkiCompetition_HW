package game;

import game.competition.Competition;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */

public class GameEngine {

	private static GameEngine instance = null;

	/**
	 * Private constructor to prevent instantiation from outside the class.
	 */
	private GameEngine() {
	}

	/**
	 * Retrieves the singleton instance of GameEngine.
	 *
	 * @return The singleton instance of GameEngine.
	 */
	public static GameEngine getInstance() {
		if (GameEngine.instance == null) {
			GameEngine.instance = new GameEngine();
		}
		return GameEngine.instance;
	}

	/**
	 * Starts the race for the given competition, iterating through turns until all
	 * competitors finish.
	 *
	 * @param competition The competition to start.
	 */
	public void startRace(Competition competition) {
		try {
			competition.launch_racers();
			Thread.sleep(30);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Prints the results of the finished competitors in the given competition.
	 *
	 * @param competition The competition whose results to print.
	 */
	public void printResult(Competition competition) {
		competition.printFinishedCompetitors();
	}
}
