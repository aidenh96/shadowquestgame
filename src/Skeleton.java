/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Skeleton Class (Extends Aggressive)
 * Author: Aiden H
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents the "Skeleton" aggressive enemy and set its combat stats.
 */
public class Skeleton extends Aggressive {

	// Constructor
	/**
	 * Create Skeleton and set its initial position
	 * 
	 * @param startX
	 *            the initial x position of the Skeleton
	 * @param startY
	 *            the initial y position of the Skeleton
	 */
	public Skeleton(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Sprite
		Image sprite = new Image(RPG.UNIT_PATH + "skeleton.png");
		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));
		this.setUnitName("Skeleton");

		// Combat Stats
		this.setMaxHP(100);
		this.setCurHP(this.getMaxHP());
		this.setMaxDamage(16);
		this.setCooldown(500);

		// Choose initial movement direction
		chooseDir(false, 0, 9);
	}
}
