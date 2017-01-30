/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Passive Class (Extends Unit)
 * Author: Aiden H
 */

// Imports
import org.newdawn.slick.SlickException;

/**
 * Represents the base class for all Passive monster sub-types in the game world.
 */
public class Passive extends Monster {
	// Attributes
	/** Timer length constants */
	private static final int MOVE_TIMER = 3000;
	private static final int RUN_AWAY_TIMER = 5000;

	/** Timer control */
	private int pastTime = 0;
	private boolean timerStarted = false;
	private String timerID;

	/** Combat stats */
	private boolean attacked; // the unit is being attacked

	// Getters & setters
	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

	// Constructor
	/**
	 * Create a Passive and set the initial map position
	 * 
	 * @param startX
	 *            the starting x position of the Monster
	 * @param startY
	 *            the starting y position of the Monster
	 */
	public Passive(int startX, int startY) throws SlickException {
		super(startX, startY);
	}

	// Methods
	/**
	 * Handles player attacks
	 * 
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 * 
	 * @return 0 = a timer is started, 1 = move normally, 2 = run away
	 */
	public int willMove(int delta) {

		// No timer started started
		// Passive has not been attacked
		if (!timerStarted && !this.isAttacked()) {
			timer(delta, MOVE_TIMER); // start timer
			timerID = "move";
			return 1;

		} else if (!timerStarted && this.isAttacked()) { // Passive has been attacked
			this.setAttacked(false); // reset
			timer(delta, RUN_AWAY_TIMER); // start timer
			timerID = "run";
			return 2;
		}

		// A timer has been started
		if (timerStarted && timerID.equals("move")) {
			timer(delta, MOVE_TIMER); // update timer
			return 0;
		} else if (timerStarted && timerID.equals("run")) {
			timer(delta, RUN_AWAY_TIMER); // update timer
			return 0;
		}

		return 0;
	}

	/**
	 * Handles all attacks against the Monster
	 * 
	 * @param player
	 *            the Player that is attacking the Monster
	 */
	public void attackMonster(Player player) {

		// If the player is within attack radius and attacking, loose HP
		if (checkRadius(player.getX(), player.getY(), ATK_RADIUS) && player.isAttacking()) {
			this.setAttacked(true); // this Passive is attack
			timerStarted = false;
			this.setCurHP(this.getCurHP() - player.getMaxDamage());
		}
	}

	/**
	 * Keeps the time for movement direction changes
	 * 
	 * @param delta
	 *            Time passed since last frame (milliseconds)
	 * @param timeLimit
	 *            How long the timer runs for (milliseconds)
	 */
	public void timer(int delta, int timeLimit) {
		if (timerStarted) {
			if (pastTime < timeLimit)
				pastTime += delta;
			if (pastTime == timeLimit) {
				pastTime = 0;
				timerStarted = false;
			}
		} else {
			pastTime = 0;
			timerStarted = true;
			pastTime += delta;
		}
	}

	/**
	 * Update the game state for a frame
	 * 
	 * @param world
	 *            the world the Monster is in
	 * @param player
	 *            the Player that is to be attacked
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 */
	@Override
	public void update(World world, Player player, int delta) {

		// Determine direction to move passive
		int move = willMove(delta); // Updates timer

		if (move == 1) { // Move normally
			chooseDir(true, 0, 0);

		} else if (move == 2) { // Run away
			chooseDir(true, player.getMoveDir(), 0);
		}

		// Move Passive
		this.move(world, this.getCurMoveX(), this.getCurMoveY(), delta, Monster.SPEED);

		// Check attacks
		attackMonster(player);

		// Check if unit is sill alive
		this.checkAlive();
	}
}
