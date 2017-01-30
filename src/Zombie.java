/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Zombie Class (Extends Aggressive)
 * Author: Aiden Hussein <hussein> 
 * Student ID: 756647
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the "Zombie" aggressive enemy and set its combat stats.
 */
public class Zombie extends Aggressive {

	// Constructor
	/**
	 * Create Zombie and set its initial position
	 * 
	 * @param startX
	 *            the initial x position of the Zombie
	 * @param startY
	 *            the initial y position of the Zombie
	 */
	public Zombie(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Sprite
		Image sprite = new Image(RPG.UNIT_PATH + "zombie.png");
		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));
		this.setUnitName("Zombie");

		// Combat Stats
		this.setMaxHP(60);
		this.setCurHP(this.getMaxHP());
		this.setMaxDamage(10);
		this.setCooldown(800);

		// Choose initial movement direction
		chooseDir(false, 0, 9);
	}
}
