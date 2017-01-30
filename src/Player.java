/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Player Class (Extends Unit)
 * Author: Aiden H
 */

// Imports
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the Player object, which is users controlled using keyboard input.
 */
public class Player extends Unit {
	// Attributes
	/** Player Speed */
	private static final double SPEED = 0.25;

	// Constructor
	/**
	 * Create a Player and set it's initial map position, and initial combat stats
	 * 
	 * @param startX
	 *            the starting x position of the Player
	 * @param startY
	 *            the starting y position of the Player
	 */
	public Player(int startX, int startY) throws SlickException {
		super(startX, startY);

		Image sprite = new Image(RPG.UNIT_PATH + "player.png");

		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));

		// Set initial values for Player stats
		reset(false);
	}

	// Methods
	/**
	 * Determines if Player can attack, setting appropriate flags as required
	 * 
	 * @param input
	 *            array of keyboard inputs
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 */
	public void canAttack(int[] input, int delta) {
		// Load required input
		int attack = input[2];

		// Attack key pressed
		if (attack == 1) {
			if (this.getCurCooldown() == this.getCooldown()) { // cooldown timer full
				this.setAttacking(true);
				cooldownTimer(delta); // start cooldown
			} else if (this.getCurCooldown() < this.getCooldown()) {
				this.setAttacking(false); // prevent attacking
			}
		} else if (attack == 0) { // attack key not pressed
			if (this.getCurCooldown() < this.getCooldown()) {
				cooldownTimer(delta);
			}
		}
	}

	/**
	 * Reset the initial load values for Player
	 * 
	 * @param afterDeath
	 *            indicates if the Player has just died
	 */
	public void reset(boolean afterDeath) {
		// Combat Stats

		// Only reset these values if the Player has died before
		if (afterDeath) {
			// Preserve Item effects
			this.setMaxHP(this.getMaxHP());
			this.setCurHP(this.getMaxHP());
			this.setMaxDamage(this.getMaxDamage());
			this.setCooldown(this.getCooldown());
			this.setCurCooldown(this.getCooldown());

			this.setAlive(true);
			this.setX(RPG.START_X);
			this.setY(RPG.START_Y);
		} else {
			this.setMaxHP(100);
			this.setCurHP(this.getMaxHP());
			this.setMaxDamage(26);
			this.setCooldown(600);
			this.setCurCooldown(this.getCooldown());
		}
	}

	/**
	 * Update the game state for a frame
	 * 
	 * @param world
	 *            the world the Player is in
	 * @param input
	 *            array of keyboard inputs
	 * @param delta
	 *            time passed since last frame (milliseconds)
	 */
	public void update(World world, int[] input, int delta) {
		// Load required inputs
		int dirX = input[0];
		int dirY = input[1];

		// Check if can attack
		this.canAttack(input, delta);

		// Move the player
		this.move(world, dirX, dirY, delta, SPEED);

		// Check if unit is sill alive
		this.checkAlive();
	}

	/**
	 * Renders the Villagers dialogue
	 * 
	 * @param g
	 *            the Slick graphics object, used for drawing
	 */
	@Override
	public void render(Graphics g) throws SlickException {
		if (this.isAlive()) {
			super.render(g);
		} else {
			reset(true);
			super.render(g);
		}
	}
}
