/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Aggressive Class (Extends Unit)
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.SlickException;

/**
 * Represents the base class for all Aggressive monster sub-types in the game world.
 */
public class Aggressive extends Monster {

	// Constructor
	/**
	 * Create an Aggressive and set the initial map position
	 * 
	 * @param startX
	 *            the starting x position of the Monster
	 * @param startY
	 *            the starting y position of the Monster
	 */
	public Aggressive(int startX, int startY) throws SlickException {
		super(startX, startY);
	}

	// Methods
	/**
	 * Handles all attacks against the Monster
	 * 
	 * @param player
	 *            the Player that is attacking the Monster
	 */
	public void attackMonster(Player player) {

		// If the Player is attacking, and Monster is within range, Monster looses HP
		if (player.isAttacking() && checkRadius(player.getX(), player.getY(), ATK_RADIUS)) {
			this.setCurHP(this.getCurHP() - player.getMaxDamage());
		}
	}

	/**
	 * Handles all attacks against the Player
	 * 
	 * @param player
	 *            the Player that is getting attacked
	 */
	public void attackPlayer(Player player) {

		// If player is within range, attack them, Player looses HP
		if (this.isAttacking())
			player.setCurHP(player.getCurHP() - this.getMaxDamage());
	}

	/**
	 * Determines if this Monster can attack
	 * 
	 * @param player
	 *            the Player that is to be attacked
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 * @return true if the Monster can attack the Player
	 */
	public boolean canAttack(Player player, int delta) {

		// Player is within attack range
		if (checkRadius(player.getX(), player.getY(), ATK_RADIUS) && this.isAlive()) {

			// Cooldown timer is full, can attack
			if (this.getCurCooldown() == this.getCooldown()) {
				this.setAttacking(true);
				cooldownTimer(delta); // start cooldown
				return true;
			} else if (this.getCurCooldown() < this.getCooldown()) { // cooldown timer already started
				this.setAttacking(false); // cannot attack
				cooldownTimer(delta); // update cooldown timer
				return false;
			}
		}

		return false; // default return
	}

	/**
	 * Determines if this Monster can follow a Player
	 * 
	 * @param player
	 *            the Player to be followed
	 * @return true if the player can be followed
	 */
	public boolean canFollow(Player player) {

		// Player is within follow range
		if (checkRadius(player.getX(), player.getY(), FOLLOW_RADIUS) && this.isAlive()) {
			return true;
		} else if (!(checkRadius(player.getX(), player.getY(), FOLLOW_RADIUS)) && this.isAlive()) { // Player is not within follow range OR Player is standing still
			return false;
		}

		return false; // default return
	}

	/**
	 * Determines the direction need to move into order to follow a Player
	 * 
	 * @param pX
	 *            the Player to follow's x current x position
	 * @param pY
	 *            the Player to follow's x current y position
	 * @return an integer representing the direction to move
	 */
	public int followPlayer(double pX, double pY) {

		double x = this.getX();
		double y = this.getY();

		double lowerX = this.getX() - FOLLOW_RADIUS;
		double upperX = this.getX() + FOLLOW_RADIUS;
		double lowerY = this.getY() - FOLLOW_RADIUS;
		double upperY = this.getY() + FOLLOW_RADIUS;

		double dUpperX = this.getX() + (RPG.TILE_SIZE / 2);
		double dLowerX = this.getX() - (RPG.TILE_SIZE / 2);
		double dUpperY = this.getY() + (RPG.TILE_SIZE / 2);
		double dLowerY = this.getY() - (RPG.TILE_SIZE / 2);

		if ((pX >= dLowerX && pX <= dUpperX) && (pY >= lowerY && pY <= y)) {
			return 7; // Up
		}
		if ((pX >= dLowerX && pX <= dUpperX) && (pY >= y && pY <= upperY)) {
			return 8; // Down
		}
		if ((pX >= x && pX <= upperX) && (pY >= dLowerY && pY <= dUpperY)) {
			return 3; // Right
		}
		if ((pX >= lowerX && pX <= x) && (pY >= dLowerY && pY <= dUpperY)) {
			return 6; // Left
		}

		if ((pX >= x && pX <= upperX) && (pY >= y && pY <= upperY)) {
			return 1; // Down-right
		}
		if ((pX >= lowerX && pX <= x) && (pY >= y && pY <= upperY)) {
			return 4; // Down-left
		}
		if ((pX >= x && pX <= upperX) && (pY >= lowerY && pY <= y)) {
			return 2; // Up-right
		}
		if ((pX >= lowerX && pX <= x) && (pY >= lowerY && pY <= y)) {
			return 5; // Up-left
		}

		return 0;
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

		// Check Player attacks and Player following
		if (canAttack(player, delta)) {
			chooseDir(false, 0, 9); // stand still
			attackPlayer(player); // attack
		} else if (canFollow(player)) {
			chooseDir(false, 0, followPlayer(player.getX(), player.getY())); // follow the player
		} else if (!canFollow(player)) {
			chooseDir(false, 0, 9); // stand still
		}

		this.move(world, this.getCurMoveX(), this.getCurMoveY(), delta, Monster.SPEED);

		// Check attacks against Monster
		attackMonster(player);

		// Check if unit is sill alive
		this.checkAlive();
	}
}
