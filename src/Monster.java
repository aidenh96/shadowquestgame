/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Monster Class (Extends Unit)
 * Author: Aiden H
 */

// Imports
import java.util.Random;
import org.newdawn.slick.SlickException;

/**
 * Represents the base class for all Monster sub-types in the game world.
 */
public abstract class Monster extends Unit {
	// Attributes
	/** Combat radiuses */
	public static final int ATK_RADIUS = 50;
	public static final int FOLLOW_RADIUS = 150;

	/** Monster Speed */
	public static final double SPEED = 0.2; // allows Player to outrun Monsters

	/** Direction to move the Monster */
	private double curMoveX;
	private double curMoveY;

	// Getters & Setters
	public double getCurMoveX() {
		return curMoveX;
	}

	public double getCurMoveY() {
		return curMoveY;
	}

	// Constructor
	/**
	 * Create a Monster and set the initial map position
	 * 
	 * @param startX
	 *            the starting x position of the Monster
	 * @param startY
	 *            the starting y position of the Monster
	 */
	public Monster(int startX, int startY) throws SlickException {
		super(startX, startY);
	}

	// Methods
	/**
	 * Select a direction to move a Monster or generate a random movement direction
	 * 
	 * @param random
	 *            use a random direction
	 * @param avoid
	 *            specify a direction to not choose (when choosing a random direction)
	 * @param dir
	 *            specify the direction to move
	 */
	public void chooseDir(boolean random, int avoid, int dir) {
		int i = 0;

		// Choose random direction
		if (random) {
			Random rand = new Random();
			i = rand.nextInt(9) + 1;

			// Avoid choosing a direction or not moving
			if (random && avoid > 0) {
				if (i == avoid || i == 9)
					chooseDir(random, avoid, dir); // try again
			}

			// Don't choose the same direction twice in a row
			if (i == this.getMoveDir()) {
				chooseDir(random, avoid, dir); // try again
			}
		}

		// Use specified direction
		if (!random) {
			i = dir;
		}

		// Set the appropriate x and y values
		if (i == 1) { // Down-Right
			curMoveX = 1;
			curMoveY = 1;
		} else if (i == 2) { // Up-Right
			curMoveX = 1;
			curMoveY = -1;
		} else if (i == 3) { // Right
			curMoveX = 1;
			curMoveY = 0;
		} else if (i == 4) { // Down-Left
			curMoveX = -1;
			curMoveY = 1;
		} else if (i == 5) { // Up-Left
			curMoveX = -1;
			curMoveY = -1;
		} else if (i == 6) { // Left
			curMoveX = -1;
			curMoveY = 0;
		} else if (i == 7) { // Up
			curMoveX = 0;
			curMoveY = -1;
		} else if (i == 8) { // Down
			curMoveX = 0;
			curMoveY = 1;
		} else if (i == 9) { // None
			curMoveX = 0;
			curMoveY = 0;
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
	public abstract void update(World world, Player player, int delta);

}
