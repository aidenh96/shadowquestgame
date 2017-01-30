/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Bandit Class (Extends Aggressive)
 * Author: Aiden H
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the "Bandit" aggressive enemy and set its combat stats.
 */
public class Bandit extends Aggressive {
	
	// Constructor
	/**
	 * Create Bandit and set its initial position
	 * 
	 * @param startX
	 *            the initial x position of the Bandit
	 * @param startY
	 *            the initial y position of the Bandit
	 */
	public Bandit(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Sprite
		Image sprite = new Image(RPG.UNIT_PATH + "bandit.png");
		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));
		this.setUnitName("Bandit");

		// Combat Stats
		this.setMaxHP(40);
		this.setCurHP(this.getMaxHP());
		this.setMaxDamage(8);
		this.setCooldown(200);

		// Choose initial movement direction
		chooseDir(false, 0, 9);
	}
}
