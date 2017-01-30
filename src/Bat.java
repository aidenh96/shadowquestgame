/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine - Bat Class (Extends Passive)
 * Author: Aiden H
 */

// Imports
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents  the "Giant Bat" passive enemy and sets its combat stats.
 */
public class Bat extends Passive {

	// Constructor
	/**
	 * Create Giant Bat and set its initial position
	 * 
	 * @param startX
	 *            the initial x position of Giant Bat
	 * @param startY
	 *            the initial y position of Giant Bat
	 */
	public Bat(int startX, int startY) throws SlickException {
		super(startX, startY);

		// Sprite
		Image sprite = new Image(RPG.UNIT_PATH + "dreadbat.png");
		this.setUnit(sprite);
		this.setUnitFlip(sprite.getFlippedCopy(true, false));
		this.setUnitName("Giant Bat");

		// Combat Stats
		this.setMaxHP(40);
		this.setCurHP(this.getMaxHP());
		this.setMaxDamage(0);
		this.setCooldown(0);

		// Choose initial movement direction
		chooseDir(true, 0, 0);
	}
}
